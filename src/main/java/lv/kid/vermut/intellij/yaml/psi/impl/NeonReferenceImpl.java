package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import lv.kid.vermut.intellij.yaml.psi.NeonReference;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Pavels.Veretennikovs on 2015.05.20..
 */
public class NeonReferenceImpl extends NeonPsiElementImpl implements NeonReference {
    public NeonReferenceImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }

    @Override
    public PsiElement setName(String name) throws IncorrectOperationException {
        return null;
    }
}
