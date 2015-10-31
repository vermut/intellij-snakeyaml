package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.psi.tree.IElementType;
import org.yaml.snakeyaml.tokens.Token;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pavels.Veretennikovs on 2015.06.27..
 */
public class YamlTokenTypes {
    static final HashMap<Token.ID, IElementType> tokens = new HashMap<Token.ID, IElementType>(Token.ID.values().length);
    static final Map<IElementType, Token.ID> ids = new HashMap<IElementType, Token.ID>(Token.ID.values().length);

    static {
        for (Token.ID id : Token.ID.values()) {
            YamlTokenFacade tokenType = new YamlTokenFacade(id);
            tokens.put(id, tokenType);
            ids.put(tokenType, id);
        }
    }

    IElementType YAML_Alias = getIElementType(Token.ID.Alias);

    /* static Token.ID getTokenId(IElementType type) {
         return ids.get(type);
     }*/
    IElementType YAML_Anchor = getIElementType(Token.ID.Anchor);
    IElementType YAML_BlockEnd = getIElementType(Token.ID.BlockEnd);
    IElementType YAML_BlockEntry = getIElementType(Token.ID.BlockEntry);
    IElementType YAML_BlockMappingStart = getIElementType(Token.ID.BlockMappingStart);
    IElementType YAML_BlockSequenceStart = getIElementType(Token.ID.BlockSequenceStart);
    IElementType YAML_Directive = getIElementType(Token.ID.Directive);
    IElementType YAML_DocumentEnd = getIElementType(Token.ID.DocumentEnd);
    IElementType YAML_DocumentStart = getIElementType(Token.ID.DocumentStart);
    IElementType YAML_FlowEntry = getIElementType(Token.ID.FlowEntry);
    IElementType YAML_FlowMappingEnd = getIElementType(Token.ID.FlowMappingEnd);
    IElementType YAML_FlowMappingStart = getIElementType(Token.ID.FlowMappingStart);
    IElementType YAML_FlowSequenceEnd = getIElementType(Token.ID.FlowSequenceEnd);
    IElementType YAML_FlowSequenceStart = getIElementType(Token.ID.FlowSequenceStart);
    IElementType YAML_Key = getIElementType(Token.ID.Key);
    IElementType YAML_Scalar = getIElementType(Token.ID.Scalar);
    IElementType YAML_StreamEnd = getIElementType(Token.ID.StreamEnd);
    IElementType YAML_StreamStart = getIElementType(Token.ID.StreamStart);
    IElementType YAML_Tag = getIElementType(Token.ID.Tag);
    IElementType YAML_Value = getIElementType(Token.ID.Value);

    public static IElementType getIElementType(Token.ID token) {
        return tokens.get(token);
    }
}
