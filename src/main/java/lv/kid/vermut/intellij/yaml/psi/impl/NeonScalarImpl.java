package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import lv.kid.vermut.intellij.yaml.editor.NeonStructureViewElement;
import lv.kid.vermut.intellij.yaml.psi.NeonScalar;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class NeonScalarImpl extends NeonPsiElementImpl implements NeonScalar {
	public NeonScalarImpl(@NotNull ASTNode astNode) {
		super(astNode);
	}

	public String toString() {
		return "Yaml scalar";
	}

	@Override
	public String getValueText() {
		return getNode().getText();
	}

	@Override
	public String getName() {
		return getValueText();
	}

	@Override
	public ItemPresentation getPresentation() {
		return new NeonStructureViewElement(this);
	}
}
