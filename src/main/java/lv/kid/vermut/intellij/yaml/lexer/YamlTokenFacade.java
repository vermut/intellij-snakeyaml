package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.psi.tree.IElementType;
import lv.kid.vermut.intellij.yaml.YamlLanguage;
import org.yaml.snakeyaml.tokens.Token;

/**
 * Created by VermutMac on 10/31/2015.
 */
public class YamlTokenFacade extends IElementType {
    // private final Token.ID token;

    public YamlTokenFacade(Token.ID token) {
        super("[YAML Token] " + token.name(), YamlLanguage.INSTANCE);
        // this.token = token;
    }

}
