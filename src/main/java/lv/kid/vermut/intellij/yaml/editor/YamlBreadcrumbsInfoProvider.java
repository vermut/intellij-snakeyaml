package lv.kid.vermut.intellij.yaml.editor;

import com.intellij.lang.Language;
import com.intellij.psi.PsiElement;
import com.intellij.xml.breadcrumbs.BreadcrumbsInfoProvider;
import lv.kid.vermut.intellij.yaml.YamlLanguage;
import lv.kid.vermut.intellij.yaml.psi.YamlTuple;
import org.jetbrains.annotations.NotNull;

/**
 * Breadcrumbs info about which section are we editing now (just above the editor, below tabs)
 */
public class YamlBreadcrumbsInfoProvider extends BreadcrumbsInfoProvider {
    private final Language[] ourLanguages = {YamlLanguage.INSTANCE};

    @Override
    public Language[] getLanguages() {
        return ourLanguages;
    }

    @Override
    public boolean acceptElement(@NotNull PsiElement e) {
        return (e instanceof YamlTuple);
    }

    @NotNull
    @Override
    public String getElementInfo(@NotNull PsiElement e) {
        return (((YamlTuple) e).getKey()).getText();
    }

    @Override
    public String getElementTooltip(@NotNull PsiElement e) {
        return e.toString();
    }

}
