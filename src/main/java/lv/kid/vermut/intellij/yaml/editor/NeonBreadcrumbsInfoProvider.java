package lv.kid.vermut.intellij.yaml.editor;

import com.intellij.lang.Language;
import com.intellij.psi.PsiElement;
import com.intellij.xml.breadcrumbs.BreadcrumbsInfoProvider;
import lv.kid.vermut.intellij.yaml.YamlLanguage;
import lv.kid.vermut.intellij.yaml.psi.NeonEntity;
import lv.kid.vermut.intellij.yaml.psi.NeonKeyValPair;
import org.jetbrains.annotations.NotNull;

/**
 * Breadcrumbs info about which section are we editing now (just above the editor, below tabs)
 */
public class NeonBreadcrumbsInfoProvider extends BreadcrumbsInfoProvider {
	private final Language[] ourLanguages = {YamlLanguage.INSTANCE};

	@Override
	public Language[] getLanguages() {
		return ourLanguages;
	}

	@Override
	public boolean acceptElement(@NotNull PsiElement e) {
		return (e instanceof NeonKeyValPair) || (e instanceof NeonEntity);
	}

	@NotNull
	@Override
	public String getElementInfo(@NotNull PsiElement e) {
		if (e instanceof NeonKeyValPair) {
			return ((NeonKeyValPair) e).getKeyText();

		} else if (e instanceof NeonEntity) {
			String name = ((NeonEntity) e).getName();
			return name != null ? name : "??";

		} else {
			return "??";
		}
	}

	@Override
	public String getElementTooltip(@NotNull PsiElement e) {
		return e.toString();
	}

}
