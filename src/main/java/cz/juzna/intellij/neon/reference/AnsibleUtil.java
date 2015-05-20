package cz.juzna.intellij.neon.reference;

import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.FileBasedIndex;
import cz.juzna.intellij.neon.file.NeonFileType;
import cz.juzna.intellij.neon.psi.NeonFile;
import cz.juzna.intellij.neon.psi.NeonKey;
import cz.juzna.intellij.neon.psi.NeonKeyValPair;
import cz.juzna.intellij.neon.psi.NeonValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Vermut on 16/05/2015.
 */
public class AnsibleUtil {
    public static List<String> findRoleNames(Project project, String key) {
        List<String> result = new ArrayList<String>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, NeonFileType.INSTANCE,
                GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            if (virtualFile.getCanonicalPath().endsWith("tasks/main.yml"))
                result.add(virtualFile.getParent().getParent().getName());
        }
        return result;
    }

    public static List<PsiFile> findFiles(Project project, String key) {
        List<PsiFile> result = new ArrayList<PsiFile>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, NeonFileType.INSTANCE,
                GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            if (virtualFile.getCanonicalPath().endsWith(key))
                result.add(PsiManager.getInstance(project).findFile(virtualFile));
        }

        // Include .j2 - plaintexts
        virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, FileTypes.PLAIN_TEXT,
                GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            if (virtualFile.getCanonicalPath().endsWith(key))
                result.add(PsiManager.getInstance(project).findFile(virtualFile));
        }
        return result;
    }

    private static List<NeonKeyValPair> searchKeyPairs(Project project, String key, String value) {
        List<NeonKeyValPair> result = new ArrayList<NeonKeyValPair>();
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, NeonFileType.INSTANCE,
                GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            NeonFile yamlFile = (NeonFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (yamlFile != null) {
                Collection<NeonKeyValPair> properties = PsiTreeUtil.findChildrenOfType(yamlFile, NeonKeyValPair.class);
                if (!properties.isEmpty()) {
                    if (key == null) {
                        for (NeonKeyValPair property : properties)
                            result.add(property);
                    } else {
                        for (NeonKeyValPair property : properties) {
                            if (key.equals(property.getKeyText())) {
                                if (value == null)
                                    result.add(property);
                                else if (value.equals(property.getValueText()))
                                    result.add(property);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public static List<NeonKeyValPair> findAllProperties(Project project) {
        return searchKeyPairs(project, null, null);

    }

    public static List<NeonKey> findAllProperties(Project project, String key) {
        List<NeonKeyValPair> names = searchKeyPairs(project, key, null);
        List<NeonKey> result = new ArrayList<NeonKey>();
        for (NeonKeyValPair name : names) {
            result.add(name.getKey());
        }
        return result;
    }

    public static List<NeonKeyValPair> findNames(Project project) {
        return searchKeyPairs(project, "name", null);
    }

    public static List<NeonValue> findNames(Project project, String key) {
        List<NeonKeyValPair> names = searchKeyPairs(project, "name", key);
        List<NeonValue> result = new ArrayList<NeonValue>();
        for (NeonKeyValPair name : names) {
            result.add(name.getValue());
        }
        return result;
    }
}
