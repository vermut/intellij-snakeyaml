package cz.juzna.intellij.neon.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import cz.juzna.intellij.neon.editor.NeonStructureViewElement;
import cz.juzna.intellij.neon.psi.NeonScalar;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class NeonScalarImpl extends NeonPsiElementImpl implements NeonScalar {
	public NeonScalarImpl(@NotNull ASTNode astNode) {
		super(astNode);
	}

	public String toString() {
		return "Neon scalar";
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
