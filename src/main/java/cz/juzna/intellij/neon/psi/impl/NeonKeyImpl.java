package cz.juzna.intellij.neon.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import cz.juzna.intellij.neon.editor.NeonStructureViewElement;
import cz.juzna.intellij.neon.psi.NeonKey;
import cz.juzna.intellij.neon.psi.NeonKeyValPair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        return new NeonStructureViewElement(this) {
            @Nullable
            @Override
            public String getPresentableText() {
                if (NeonKeyImpl.this.getParent() instanceof NeonKeyValPair) {
                    NeonKeyValPair keyValPair = (NeonKeyValPair) NeonKeyImpl.this.getParent();
                    return keyValPair.getKeyText() + ": " + keyValPair.getValueText();
                }
                return super.getPresentableText();
            }
        };
    }
}
