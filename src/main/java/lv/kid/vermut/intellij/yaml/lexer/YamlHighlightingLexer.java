package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.lexer.LookAheadLexer;
import com.intellij.psi.tree.IElementType;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.resolver.Resolver;

/**
 * Lexer used for syntax highlighting
 * <p/>
 * It reuses the simple lexer, changing types of some tokens
 */
public class YamlHighlightingLexer extends LookAheadLexer {
    Resolver resolver = new Resolver();

    public YamlHighlightingLexer(Lexer baseLexer) {
        super(baseLexer, 1);
    }

    @Override
    protected void lookAhead(Lexer baseLexer) {
        IElementType currentToken = baseLexer.getTokenType();

        if (currentToken == YamlTokenTypes.YAML_Key) {
            advanceLexer(baseLexer);

            if (baseLexer.getTokenType() == YamlTokenTypes.YAML_Scalar) {
                advanceLexer(baseLexer);
                replaceCachedType(1, YamlTokenTypes.YAML_Key);
            }

        } else if (currentToken == YamlTokenTypes.YAML_Scalar) {
            Tag tag = resolver.resolve(NodeId.scalar, baseLexer.getTokenText(), true);

            advanceLexer(baseLexer);
            if (tag.equals(Tag.BOOL)) replaceCachedType(0, YamlTokenTypes.YAML_Tag_BOOL);
            else if (tag.equals(Tag.INT)) replaceCachedType(0, YamlTokenTypes.YAML_Tag_INT);
            else if (tag.equals(Tag.FLOAT)) replaceCachedType(0, YamlTokenTypes.YAML_Tag_FLOAT);
            else if (tag.equals(Tag.NULL)) replaceCachedType(0, YamlTokenTypes.YAML_Tag_NULL);
            else if (tag.equals(Tag.TIMESTAMP)) replaceCachedType(0, YamlTokenTypes.YAML_Tag_TIMESTAMP);
        } else {
            super.lookAhead(baseLexer);
        }
    }
}
