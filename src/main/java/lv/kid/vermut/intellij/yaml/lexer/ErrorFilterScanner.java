package lv.kid.vermut.intellij.yaml.lexer;

import org.yaml.snakeyaml.tokens.Token;

import java.io.Reader;

/**
 * Created by VermutMac on 11/15/2015.
 */
public class ErrorFilterScanner extends ErrorReportingScanner {
    public ErrorFilterScanner(Reader reader) {
        super(reader);
    }

    @Override
    public Token peekToken() {
        while (super.peekToken().getTokenId() == Token.ID.Error)
            getToken();

        return super.peekToken();
    }
}
