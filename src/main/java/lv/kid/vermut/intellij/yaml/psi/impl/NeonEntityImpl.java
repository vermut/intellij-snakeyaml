package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.IncorrectOperationException;
import lv.kid.vermut.intellij.yaml.lexer.NeonTokenTypes;
import lv.kid.vermut.intellij.yaml.psi.NeonArray;
import lv.kid.vermut.intellij.yaml.psi.NeonEntity;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class NeonEntityImpl extends NeonPsiElementImpl implements NeonEntity {
    public NeonEntityImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }

    public String toString() {
        return "Yaml entity";
    }

    @Override
    public String getName() {
        try {
            return getNode().findChildByType(NeonTokenTypes.NEON_LITERAL).getText();
        } catch (NullPointerException e) {
            return "?? Jinja ??";
        }
    }

    @Override
    public NeonArray getArgs() {
        ASTNode children[] = getNode().getChildren(TokenSet.create(NeonTokenTypes.NEON_LITERAL));
        if (children.length > 0) return (NeonArray) children[0].getPsi(); // should be hash I guess
        else return null;
    }

    @Override
    public PsiElement setName(@NonNls @NotNull String s) throws IncorrectOperationException {
        // TODO: for refactoring
        return null;
    }
}
