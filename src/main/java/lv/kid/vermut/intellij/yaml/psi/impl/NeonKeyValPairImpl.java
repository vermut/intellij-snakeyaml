package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.IncorrectOperationException;
import lv.kid.vermut.intellij.yaml.parser.NeonElementTypes;
import lv.kid.vermut.intellij.yaml.psi.NeonKey;
import lv.kid.vermut.intellij.yaml.psi.NeonKeyValPair;
import lv.kid.vermut.intellij.yaml.psi.NeonScalar;
import lv.kid.vermut.intellij.yaml.psi.NeonValue;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class NeonKeyValPairImpl extends NeonPsiElementImpl implements NeonKeyValPair {
    public NeonKeyValPairImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }

    public String toString() {
        return "Yaml key-val pair";
    }


    @Override
    public NeonKey getKey() {
        ASTNode keys[];

        ASTNode[] compoundKeys = getNode().getChildren(TokenSet.create(NeonElementTypes.COMPOUND_KEY));
        if (compoundKeys.length > 0) {
            keys = compoundKeys[0].getChildren(TokenSet.create(NeonElementTypes.KEY));
        } else {
            keys = getNode().getChildren(TokenSet.create(NeonElementTypes.KEY));
        }

        return keys.length > 0 ? (NeonKey) keys[0].getPsi() : null;
    }

    @Override
    public String getKeyText() {
        return this.getKey() != null ? this.getKey().getText() : null;
    }

    @Override
    public NeonValue getValue() {
        if (getNode().getLastChildNode().getPsi() instanceof NeonValue)
            return (NeonValue) getNode().getLastChildNode().getPsi();

        return null;
    }

    @Override
    public String getValueText() {
        if (getValue() instanceof NeonScalar)
            return getValue().getText();
        else
            return "?? complex value ??";
    }

    @Override
    public PsiElement setName(@NonNls @NotNull String s) throws IncorrectOperationException {
        // TODO: needed for refactoring
        return null;
    }

    @Override
    public String getName() {
        return getKeyText();
    }
}
