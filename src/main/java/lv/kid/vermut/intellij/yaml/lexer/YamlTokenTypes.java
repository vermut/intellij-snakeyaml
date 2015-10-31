package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.psi.tree.IElementType;
import lv.kid.vermut.intellij.yaml.YamlLanguage;
import org.yaml.snakeyaml.tokens.Token;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pavels.Veretennikovs on 2015.06.27..
 */
public class YamlTokenTypes {
    static final HashMap<Token.ID, IElementType> tokens = new HashMap<Token.ID, IElementType>(Token.ID.values().length);
    static final Map<IElementType, Token.ID> ids = new HashMap<IElementType, Token.ID>(Token.ID.values().length);
    /* static Token.ID getTokenId(IElementType type) {
         return ids.get(type);
     }*/
    public static IElementType YAML_Anchor = getIElementType(Token.ID.Anchor);
    public static IElementType YAML_Alias = getIElementType(Token.ID.Alias);
    public static IElementType YAML_BlockEnd = getIElementType(Token.ID.BlockEnd);
    public static IElementType YAML_BlockEntry = getIElementType(Token.ID.BlockEntry);
    public static IElementType YAML_BlockMappingStart = getIElementType(Token.ID.BlockMappingStart);
    public static IElementType YAML_BlockSequenceStart = getIElementType(Token.ID.BlockSequenceStart);
    public static IElementType YAML_Directive = getIElementType(Token.ID.Directive);
    public static IElementType YAML_DocumentEnd = getIElementType(Token.ID.DocumentEnd);
    public static IElementType YAML_DocumentStart = getIElementType(Token.ID.DocumentStart);
    public static IElementType YAML_FlowEntry = getIElementType(Token.ID.FlowEntry);
    public static IElementType YAML_FlowMappingEnd = getIElementType(Token.ID.FlowMappingEnd);
    public static IElementType YAML_FlowMappingStart = getIElementType(Token.ID.FlowMappingStart);
    public static IElementType YAML_FlowSequenceEnd = getIElementType(Token.ID.FlowSequenceEnd);
    public static IElementType YAML_FlowSequenceStart = getIElementType(Token.ID.FlowSequenceStart);
    public static IElementType YAML_Key = getIElementType(Token.ID.Key);
    public static IElementType YAML_Scalar = getIElementType(Token.ID.Scalar);
    public static IElementType YAML_StreamEnd = getIElementType(Token.ID.StreamEnd);
    public static IElementType YAML_StreamStart = getIElementType(Token.ID.StreamStart);
    public static IElementType YAML_Tag = getIElementType(Token.ID.Tag);
    public static IElementType YAML_Value = getIElementType(Token.ID.Value);
    public static IElementType YAML_Highlight_Keyword = new IElementType("[YAML HL] Keyword", YamlLanguage.INSTANCE);

    static {
        for (Token.ID id : Token.ID.values()) {
            YamlTokenFacade tokenType = new YamlTokenFacade(id);
            tokens.put(id, tokenType);
            ids.put(tokenType, id);
        }
    }

    public static IElementType getIElementType(Token.ID token) {
        return tokens.get(token);
    }
}
