package lv.kid.vermut.intellij.yaml.lexer;

import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.scanner.Scanner;
import org.yaml.snakeyaml.scanner.ScannerException;
import org.yaml.snakeyaml.scanner.ScannerImpl;
import org.yaml.snakeyaml.tokens.StreamEndToken;
import org.yaml.snakeyaml.tokens.Token;

import java.io.Reader;

/**
 * Created by Pavels.Veretennikovs on 2015.06.27..
 */
public class ErrorSkippingScanner implements Scanner {
    private final Scanner scanner;
    private final StreamReader streamReader;

    public ErrorSkippingScanner(Reader reader) {
        streamReader = new StreamReader(reader);
        scanner = new ScannerImpl(streamReader);
    }

    @Override
    public boolean checkToken(Token.ID... choices) {
        return scanner.checkToken(choices);
    }

    @SuppressWarnings({"StatementWithEmptyBody", "EmptyCatchBlock"})
    private Token peekIgnoringErrors() {
        try {
            return scanner.peekToken();
        } catch (YAMLException e) {
            try {
                do {
                    streamReader.forward();
                } while (!readerOnWhitespace());
                streamReader.forward();
            } catch (StringIndexOutOfBoundsException ignored) {
                // No more data, make sure we have nothing in scanner cache
                try {
                    while (scanner.getToken() != null) {
                    }
                } catch (ScannerException also_ignored) {
                }
                return new StreamEndToken(streamReader.getMark(), streamReader.getMark());
            }
            // Try again
            return peekIgnoringErrors();
        } catch (StringIndexOutOfBoundsException ignored) {
            return new StreamEndToken(streamReader.getMark(), streamReader.getMark());
        }
    }

    @Override
    public Token getToken() {
        return scanner.getToken();
    }

    private boolean readerOnWhitespace() {
        return streamReader.peek() == ' '
                || streamReader.peek() == '\n'
                || streamReader.peek() == '\t';
    }

    @Override
    public Token peekToken() {
        return peekIgnoringErrors();
    }
}
