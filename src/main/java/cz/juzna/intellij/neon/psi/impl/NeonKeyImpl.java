package cz.juzna.intellij.neon.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import cz.juzna.intellij.neon.editor.NeonStructureViewElement;
import cz.juzna.intellij.neon.psi.NeonKey;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class NeonKeyImpl extends NeonPsiElementImpl implements NeonKey {
	public NeonKeyImpl(@NotNull ASTNode astNode) {
		super(astNode);
	}

	public String toString() {
		return "Neon key";
	}


	@Override
	public String getKeyText() {
		return getNode().getText();
	}

	@Override
	public String getName() {
		return getKeyText();
	}

	@Override
	public ItemPresentation getPresentation() {
		return new NeonStructureViewElement(this);
	}


}
