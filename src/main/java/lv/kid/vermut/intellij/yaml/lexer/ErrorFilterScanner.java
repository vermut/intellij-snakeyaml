package lv.kid.vermut.intellij.yaml.lexer;

import org.yaml.snakeyaml.tokens.Token;

import java.io.Reader;
import java.util.EnumSet;

/**
 * Created by VermutMac on 11/15/2015.
 */
public class ErrorFilterScanner extends ErrorReportingScanner {

    private EnumSet<Token.ID> filtered = EnumSet.of(Token.ID.Error, Token.ID.Whitespace, Token.ID.Comment);

    public ErrorFilterScanner(Reader reader) {
        super(reader);
    }

    @Override
    public Token peekToken() {
        while (filtered.contains(super.peekToken().getTokenId()))
            getToken();

        return super.peekToken();
    }
}
