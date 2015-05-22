package cz.juzna.intellij.neon.reference;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.util.ArrayUtil;
import cz.juzna.intellij.neon.psi.NeonKeyValPair;
import cz.juzna.intellij.neon.psi.NeonValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavels.Veretennikovs on 2015.05.18..
 */
public class AnsibleNamesContributor implements ChooseByNameContributor {

    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        List<NeonKeyValPair> names = AnsibleUtil.findNames(project);
        List<String> result = new ArrayList<String>(names.size());

        result.addAll(AnsibleUtil.findRoleNames(project, AnsibleUtil.ALL));

        for (NeonKeyValPair property : names) {
            if (property.getValueText() != null && property.getValueText().length() > 0) {
                result.add(property.getValueText());
            }
        }
        return ArrayUtil.toStringArray(result);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String key, String pattern, Project project, boolean includeNonProjectItems) {
        List<NeonValue> names = AnsibleUtil.findNames(project, key);
        List<PsiFile> roles = AnsibleUtil.findRoles(project, key);

        List<NavigationItem> result = new ArrayList<NavigationItem>(names.size() + roles.size());
        result.addAll(roles);
        result.addAll(names);
        return result.toArray(new NavigationItem[result.size()]);
    }
}
