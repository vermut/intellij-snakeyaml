package cz.juzna.intellij.neon.reference;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.FileBasedIndex;
import cz.juzna.intellij.neon.file.NeonFileType;
import cz.juzna.intellij.neon.psi.NeonFile;
import cz.juzna.intellij.neon.psi.NeonKey;
import cz.juzna.intellij.neon.psi.NeonKeyValPair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Vermut on 16/05/2015.
 */
public class AnsibleUtil {
    public static List<NeonKeyValPair> findProperties(Project project, String key) {
        List<NeonKeyValPair> result = new ArrayList<NeonKeyValPair>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, NeonFileType.INSTANCE,
                GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            NeonFile yamlFile = (NeonFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (yamlFile != null) {
                Collection<NeonKeyValPair> properties = PsiTreeUtil.findChildrenOfType(yamlFile, NeonKeyValPair.class);
                if (!properties.isEmpty()) {
                    if (key == null) {
                        result.addAll(properties);
                    } else {
                        for (NeonKeyValPair property : properties) {
                            if (key.equals(property.getKeyText()))
                                result.add(property);
                        }
                    }
                }
            }
        }
        return result;
    }

    public static List<NeonKeyValPair> findProperties(Project project) {
        return findProperties(project, null);
    }

    public static List<NeonKeyValPair> findNames(Project project) {
        return findProperties(project, "name");
    }
}
