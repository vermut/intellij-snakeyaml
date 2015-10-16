package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import lv.kid.vermut.intellij.yaml.parser.NeonElementTypes;
import lv.kid.vermut.intellij.yaml.psi.NeonKey;
import lv.kid.vermut.intellij.yaml.psi.NeonSection;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class NeonSectionImpl extends NeonKeyValPairImpl implements NeonSection {
	public NeonSectionImpl(@NotNull ASTNode astNode) {
		super(astNode);
	}

	public String toString() {
		return "Yaml section";
	}

	@Override
	public NeonKey getParentSection() {
		if (getNode().getFirstChildNode().getElementType() == NeonElementTypes.COMPOUND_KEY) {
			return (NeonKey) getNode().getFirstChildNode().getPsi();
		} else {
			return null;
		}
	}

	@Override
	public String getParentSectionText() {
		PsiElement tmp = getParentSection();
		return tmp == null ? null : tmp.getText();
	}
}
