package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.lang.PsiBuilder;
import com.intellij.util.text.CharSequenceReader;
import org.yaml.snakeyaml.scanner.Scanner;
import org.yaml.snakeyaml.tokens.Token;

/**
 * Created by Pavels.Veretennikovs on 2015.06.27..
 */
public class PsiBuilderScannerParallelizator implements ScannerEx {
    private final PsiBuilder builder;
    private final Scanner scanner;
    private boolean peekMode;
    private int builderTokensBehind = 0;

    public PsiBuilderScannerParallelizator(PsiBuilder builder) {
        this.builder = builder;
        scanner = new ErrorFilterScanner(new CharSequenceReader(builder.getOriginalText()));
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

    @Override
    public void catchUpWithScanner() {
        while (builderTokensBehind > 0) {
            builder.advanceLexer();
            builderTokensBehind--;
        }
    }

    public boolean isPeekMode() {
        return peekMode;
    }

    @Override
    public void setPeekMode(boolean peekMode) {
        this.peekMode = peekMode;
    }

    @Override
    public PsiBuilder.Marker getMarker() {
        return builder.mark();
    }

    @Override
    public void markError(String error) {
        builder.error(error);
    }
}
