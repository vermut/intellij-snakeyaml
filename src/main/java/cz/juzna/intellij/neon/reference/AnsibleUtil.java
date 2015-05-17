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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vermut on 16/05/2015.
 */
public class AnsibleUtil {
    public static List<NeonKey> findProperties(Project project, String key) {
        List<NeonKey> result = null;
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, NeonFileType.INSTANCE,
                GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            NeonFile simpleFile = (NeonFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (simpleFile != null) {
                NeonKey[] properties = PsiTreeUtil.getChildrenOfType(simpleFile, NeonKey.class);
                if (properties != null) {
                    for (NeonKey property : properties) {
                        if (key.equals(property.getKeyText())) {
                            if (result == null) {
                                result = new ArrayList<NeonKey>();
                            }
                            result.add(property);
                        }
                    }
                }
            }
        }
        return result != null ? result : Collections.<NeonKey>emptyList();
    }

    public static List<NeonKey> findProperties(Project project) {
        List<NeonKey> result = new ArrayList<NeonKey>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, NeonFileType.INSTANCE,
                GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            NeonFile simpleFile = (NeonFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (simpleFile != null) {
                NeonKey[] properties = PsiTreeUtil.getChildrenOfType(simpleFile, NeonKey.class);
                if (properties != null) {
                    Collections.addAll(result, properties);
                }
            }
        }
        return result;
    }
}
