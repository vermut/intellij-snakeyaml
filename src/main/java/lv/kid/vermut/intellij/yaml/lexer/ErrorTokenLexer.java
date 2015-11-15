package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.psi.tree.IElementType;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.scanner.ScannerException;
import org.yaml.snakeyaml.tokens.Token;

/**
 * Created by VermutMac on 11/15/2015.
 */
public class ErrorTokenLexer extends YamlLexer {

    public static final Token.ID ERROR_TOKEN_ID = null;

    @Override
    public IElementType getTokenType() {
        if (currentTokenIsError())
            return YamlTokenTypes.YAML_Error;

        return super.getTokenType();
    }

    protected boolean currentTokenIsError() {
        return myToken != null && myToken.getTokenId() == ERROR_TOKEN_ID;
    }

    @Override
    public void advance() {
        if (currentTokenIsError())
            myToken = myScanner.peekToken();
        else
            try {
                super.advance();
            } catch (ScannerException e) {
                Mark start = streamReader.getMark();

                do {
                    streamReader.forward();
                } while (!readerOnWhitespace());
                streamReader.forward();

                myToken = new Token(start, streamReader.getMark()) {
                    @Override
                    public ID getTokenId() {
                        return ERROR_TOKEN_ID;
                    }
                };
            }
    }

    private boolean readerOnWhitespace() {
        return streamReader.peek() == ' '
                || streamReader.peek() == '\n'
                || streamReader.peek() == '\t';
    }
}
