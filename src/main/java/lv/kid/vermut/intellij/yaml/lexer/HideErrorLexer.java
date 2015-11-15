package lv.kid.vermut.intellij.yaml.lexer;

/**
 * Created by VermutMac on 11/15/2015.
 */
public class HideErrorLexer extends ErrorTokenLexer {
    @Override
    public void advance() {
        super.advance();
        while (currentTokenIsError())
            super.advance();
    }
}
