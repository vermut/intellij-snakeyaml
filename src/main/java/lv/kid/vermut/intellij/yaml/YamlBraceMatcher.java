package lv.kid.vermut.intellij.yaml;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static lv.kid.vermut.intellij.yaml.lexer.YamlTokenTypes.*;

/**
 * Matches starting-closing braces in neon
 */
public class YamlBraceMatcher implements PairedBraceMatcher {
    private static final BracePair[] PAIRS = {
            new BracePair(YAML_FlowMappingStart, YAML_FlowMappingEnd, true), // {}
            new BracePair(YAML_FlowSequenceStart, YAML_FlowSequenceEnd, true),              // []
    };

    @Override
    public BracePair[] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType iElementType, @Nullable IElementType iElementType1) {
        return true;
    }

    @Override
    public int getCodeConstructStart(PsiFile psiFile, int openingBraceOffset) {
        return openingBraceOffset;
    }
}
