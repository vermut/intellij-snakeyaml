package lv.kid.vermut.intellij.yaml.lexer;

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

    public static IElementType YAML_Anchor = addToken(Token.ID.Anchor);
    public static IElementType YAML_Alias = addToken(Token.ID.Alias);
    public static IElementType YAML_BlockEnd = addToken(Token.ID.BlockEnd);
    public static IElementType YAML_BlockEntry = addToken(Token.ID.BlockEntry);
    public static IElementType YAML_BlockMappingStart = addToken(Token.ID.BlockMappingStart);
    public static IElementType YAML_BlockSequenceStart = addToken(Token.ID.BlockSequenceStart);
    public static IElementType YAML_Directive = addToken(Token.ID.Directive);
    public static IElementType YAML_DocumentEnd = addToken(Token.ID.DocumentEnd);
    public static IElementType YAML_DocumentStart = addToken(Token.ID.DocumentStart);
    public static IElementType YAML_FlowEntry = addToken(Token.ID.FlowEntry);
    public static IElementType YAML_FlowMappingEnd = addToken(Token.ID.FlowMappingEnd);
    public static IElementType YAML_FlowMappingStart = addToken(Token.ID.FlowMappingStart);
    public static IElementType YAML_FlowSequenceEnd = addToken(Token.ID.FlowSequenceEnd);
    public static IElementType YAML_FlowSequenceStart = addToken(Token.ID.FlowSequenceStart);
    public static IElementType YAML_Key = addToken(Token.ID.Key);
    public static IElementType YAML_Scalar = addToken(Token.ID.Scalar);
    public static IElementType YAML_StreamEnd = addToken(Token.ID.StreamEnd);
    public static IElementType YAML_StreamStart = addToken(Token.ID.StreamStart);
    public static IElementType YAML_Tag = addToken(Token.ID.Tag);
    public static IElementType YAML_Value = addToken(Token.ID.Value);

    public static IElementType YAML_Tag_BOOL = addTag(Tag.BOOL);
    public static IElementType YAML_Tag_INT = addTag(Tag.INT);
    public static IElementType YAML_Tag_FLOAT = addTag(Tag.FLOAT);
    public static IElementType YAML_Tag_NULL = addTag(Tag.NULL);
    public static IElementType YAML_Tag_TIMESTAMP = addTag(Tag.TIMESTAMP);

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
