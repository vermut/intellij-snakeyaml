package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import lv.kid.vermut.intellij.yaml.YamlLanguage;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.tokens.Token;

import java.util.HashMap;

/**
 * Created by Pavels.Veretennikovs on 2015.06.27..
 */
public class YamlTokenTypes {
    static final HashMap<Token.ID, IElementType> tokens = new HashMap<Token.ID, IElementType>(Token.ID.values().length);

    public final static IElementType YAML_Anchor = addToken(Token.ID.Anchor);
    public final static IElementType YAML_Alias = addToken(Token.ID.Alias);
    public final static IElementType YAML_BlockEnd = addToken(Token.ID.BlockEnd);
    public final static IElementType YAML_BlockEntry = addToken(Token.ID.BlockEntry);
    public final static IElementType YAML_BlockMappingStart = addToken(Token.ID.BlockMappingStart);
    public final static IElementType YAML_BlockSequenceStart = addToken(Token.ID.BlockSequenceStart);
    public final static IElementType YAML_Directive = addToken(Token.ID.Directive);
    public final static IElementType YAML_DocumentEnd = addToken(Token.ID.DocumentEnd);
    public final static IElementType YAML_DocumentStart = addToken(Token.ID.DocumentStart);
    public final static IElementType YAML_FlowEntry = addToken(Token.ID.FlowEntry);
    public final static IElementType YAML_FlowMappingEnd = addToken(Token.ID.FlowMappingEnd);
    public final static IElementType YAML_FlowMappingStart = addToken(Token.ID.FlowMappingStart);
    public final static IElementType YAML_FlowSequenceEnd = addToken(Token.ID.FlowSequenceEnd);
    public final static IElementType YAML_FlowSequenceStart = addToken(Token.ID.FlowSequenceStart);
    public final static IElementType YAML_Key = addToken(Token.ID.Key);
    public final static IElementType YAML_Scalar = addToken(Token.ID.Scalar);
    public final static IElementType YAML_StreamEnd = addToken(Token.ID.StreamEnd);
    public final static IElementType YAML_StreamStart = addToken(Token.ID.StreamStart);

    public final static IElementType YAML_Tag = addToken(Token.ID.Tag);
    public final static IElementType YAML_Tag_BOOL = addTag(Tag.BOOL);
    public final static IElementType YAML_Tag_INT = addTag(Tag.INT);
    public final static IElementType YAML_Tag_FLOAT = addTag(Tag.FLOAT);
    public final static IElementType YAML_Tag_NULL = addTag(Tag.NULL);
    public final static IElementType YAML_Tag_TIMESTAMP = addTag(Tag.TIMESTAMP);

    public final static IElementType YAML_Value = addToken(Token.ID.Value);
    public final static IElementType YAML_Comment = addToken(Token.ID.Comment);

    public final static IElementType YAML_Error = TokenType.BAD_CHARACTER; // addToken(Token.ID.Error);
    public final static IElementType YAML_Whitespace = TokenType.WHITE_SPACE; // addToken(Token.ID.Whitespace);

    static {
        tokens.put(Token.ID.Error, YAML_Error);
        // tokens.put(Token.ID.Error, YAML_Comment);
        tokens.put(Token.ID.Whitespace, YAML_Whitespace);
    }

    private static YamlTokenFacade addToken(Token.ID token) {
        YamlTokenFacade tokenFacade = new YamlTokenFacade(token);
        tokens.put(token, tokenFacade);
        return tokenFacade;
    }

    private static IElementType addTag(Tag tag) {
        return new IElementType(tag.getValue(), YamlLanguage.INSTANCE);
    }

    public static IElementType getIElementType(Token.ID token) {
        return tokens.get(token);
    }
}
