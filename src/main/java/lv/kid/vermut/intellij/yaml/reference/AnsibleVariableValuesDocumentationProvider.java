package lv.kid.vermut.intellij.yaml.reference;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.util.io.HttpRequests;
import com.intellij.util.net.HttpConfigurable;
import lv.kid.vermut.intellij.yaml.psi.NeonKey;
import lv.kid.vermut.intellij.yaml.psi.NeonKeyValPair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static lv.kid.vermut.intellij.yaml.reference.AnsibleReferenceContributor.jinjaRefPattern;

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

        // Try documentation lookup for key
        if (psiElement(NeonKeyValPair.class).accepts(originalElement)){
            String url = MessageFormat.format("http://docs.ansible.com/{0}_module.html", ((NeonKeyValPair) originalElement).getKeyText());
            try {
                HttpConfigurable.getInstance().prepareURL(url);
                String pageData = HttpRequests.request(url).connect(new HttpRequests.RequestProcessor<String>() {
                    @Override
                    public String process(@NotNull HttpRequests.Request request) throws IOException {
                        return new String(request.readBytes(null), "UTF-8");
                    }
                });
                return pageData.substring(pageData.indexOf("<div id=\"page-content\">"));
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
        return getQuickNavigateInfo(element, element);
    }
}
