package cz.juzna.intellij.neon.reference;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import cz.juzna.intellij.neon.psi.NeonKey;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vermut on 16/05/2015.
 */
public class AnsibleChooseByNameContributor implements ChooseByNameContributor {
    @NotNull
    @Override
    public String[] getNames(Project project, boolean b) {
        List<NeonKey> properties = AnsibleUtil.findProperties(project);
        List<String> names = new ArrayList<String>(properties.size());
        for (NeonKey property : properties) {
            if (property.getKeyText() != null && property.getKeyText().length() > 0) {
                names.add(property.getKeyText());
            }
        }
        return names.toArray(new String[names.size()]);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        // todo include non project items
        List<NeonKey> properties = AnsibleUtil.findProperties(project, name);
        return properties.toArray(new NavigationItem[properties.size()]);
    }
}
