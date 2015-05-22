package cz.juzna.intellij.neon.reference;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.psi.PsiElement;
import cz.juzna.intellij.neon.psi.NeonKey;
import cz.juzna.intellij.neon.psi.NeonKeyValPair;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static cz.juzna.intellij.neon.reference.AnsibleReferenceContributor.jinjaRefPattern;

/**
 * Created by Pavels.Veretennikovs on 2015.05.22..
 */
public class AnsibleVariableValuesDocumentationProvider extends AbstractDocumentationProvider {
    @Nullable
    public String getQuickNavigateInfo(PsiElement element, PsiElement originalElement) {
        if (jinjaRefPattern().accepts(originalElement)) {
            // Find all values of jinja var
            List<NeonKey> properties = AnsibleUtil.findAllProperties(originalElement.getProject(), originalElement.getText());
            StringBuilder result = new StringBuilder();

            for (NeonKey neonKey : properties) {
                if (neonKey.getParent() instanceof NeonKeyValPair) {
                    NeonKeyValPair keyValPair = (NeonKeyValPair) neonKey.getParent();
                    result.append(keyValPair.getValueText() + "<br>");
                }
            }

            return result.toString();
        }
        return null;
    }

    @Override
    public String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
        return getQuickNavigateInfo(element, element);
    }
}
