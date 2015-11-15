package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.lang.PsiBuilder;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.scanner.Scanner;
import org.yaml.snakeyaml.scanner.ScannerEx;
import org.yaml.snakeyaml.scanner.ScannerImpl;
import org.yaml.snakeyaml.tokens.Token;

import java.io.Reader;

/**
 * Created by Pavels.Veretennikovs on 2015.06.27..
 */
public class NoBuilderScannerAdapter implements ScannerEx {
    private final Scanner scanner;

    public NoBuilderScannerAdapter(Reader reader) {
        scanner = new ScannerImpl(new StreamReader(reader));
    }

    @Override
    public boolean checkToken(Token.ID... choices) {
        return scanner.checkToken(choices);
    }

    @Override
    public Token peekToken() {
        return scanner.peekToken();
    }

    @Override
    public Token getToken() {
        return scanner.getToken();
    }

    @Override
    public void catchUpWithScanner() {
    }

    @Override
    public void setPeekMode(boolean peekMode) {
    }

    @Override
    public PsiBuilder.Marker getMarker() {
        return null;
    }

    @Override
    public void markError(String error) {
    }
}
