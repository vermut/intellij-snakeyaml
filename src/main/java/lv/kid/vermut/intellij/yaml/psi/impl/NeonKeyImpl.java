package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import lv.kid.vermut.intellij.yaml.editor.NeonStructureViewElement;
import lv.kid.vermut.intellij.yaml.psi.NeonKey;
import lv.kid.vermut.intellij.yaml.psi.NeonKeyValPair;
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
        return "Yaml key";
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
