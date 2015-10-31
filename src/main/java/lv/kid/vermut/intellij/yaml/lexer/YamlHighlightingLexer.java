package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.lexer.LookAheadLexer;
import com.intellij.psi.tree.IElementType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Lexer used for syntax highlighting
 * <p/>
 * It reuses the simple lexer, changing types of some tokens
 */
public class YamlHighlightingLexer extends LookAheadLexer {

    private static final Set<String> KEYWORDS = new HashSet<String>(Arrays.asList(
            new String[]{
                    "true", "True", "TRUE",
                    "false", "False", "FALSE",
                    "null", "Null", "NULL", "~",
                    ".inf", ".Inf", ".INF",
                    "+.inf", "+.Inf", "+.INF",
                    "-.inf", "-.Inf", "-.INF",
                    ".NAN", ".nan", ".NaN"
            }
    ));

    public YamlHighlightingLexer(Lexer baseLexer) {
        super(baseLexer, 1);
    }

    @Override
    protected void lookAhead(Lexer baseLexer) {
        IElementType currentToken = baseLexer.getTokenType();

        if (currentToken == YamlTokenTypes.YAML_Scalar && KEYWORDS.contains(baseLexer.getTokenText())) {
            advanceLexer(baseLexer);
            replaceCachedType(0, YamlTokenTypes.YAML_Highlight_Keyword);

        } else if (currentToken == YamlTokenTypes.YAML_Scalar) {
            advanceLexer(baseLexer);

            if (baseLexer.getTokenType() == YamlTokenTypes.YAML_Value) {
                advanceLexer(baseLexer);
                // replaceCachedType(0, YamlTokenTypesOld.NEON_KEY);
            }

        } else {
            super.lookAhead(baseLexer);
        }
    }
}
