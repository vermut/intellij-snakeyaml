package lv.kid.vermut.intellij.ansible.parser;

import com.intellij.psi.tree.IElementType;
import org.yaml.snakeyaml.tokens.Token;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pavels.Veretennikovs on 2015.06.27..
 */
public class TokenConverter {
    static final Map<Token.ID, YamlTokenType> tokens = new HashMap<Token.ID, YamlTokenType>(Token.ID.values().length);
    static final Map<IElementType, Token.ID> ids = new HashMap<IElementType, Token.ID>(Token.ID.values().length);

    static {
        for (Token.ID id : Token.ID.values()) {
            YamlTokenType tokenType = new YamlTokenType(id.name());
            tokens.put(id, tokenType);
            ids.put(tokenType, id);
        }
    }

    static IElementType getIElementType(Token.ID token) {
        return tokens.get(token);
    }

    static Token.ID getTokenId(IElementType type) {
        return ids.get(type);
    }
}
