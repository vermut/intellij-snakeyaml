package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.util.text.CharSequenceReader;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.scanner.Scanner;
import org.yaml.snakeyaml.scanner.ScannerImpl;
import org.yaml.snakeyaml.tokens.Token;

/**
 * Created by Pavels.Veretennikovs on 2015.06.27..
 */
public class PsiBuilderToScannerAdapter implements Scanner {
    private final PsiBuilder builder;
    private final Scanner scanner;
    private boolean peekMode;
    private int builderTokensBehind = 0;

    public PsiBuilderToScannerAdapter(PsiBuilder builder) {
        this.builder = builder;
        scanner = new ScannerImpl(new StreamReader(new CharSequenceReader(builder.getOriginalText())));
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
        builderTokensBehind++;

        if (!isPeekMode()) {
            catchUpWithScanner();
        }
        return scanner.getToken();
    }

    public void catchUpWithScanner() {
        while (builderTokensBehind > 0) {
            builder.advanceLexer();
            builderTokensBehind--;
        }
    }

    public boolean isPeekMode() {
        return peekMode;
    }

    public void setPeekMode(boolean peekMode) {
        this.peekMode = peekMode;
    }

    public PsiBuilder.Marker getMarker() {
        PsiBuilder.Marker marker = builder.mark();
        // catchUpWithScanner();
        return marker;
    }
}
