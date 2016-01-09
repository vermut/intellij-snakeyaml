package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.lang.PsiBuilder;
import com.intellij.util.text.CharSequenceReader;
import org.yaml.snakeyaml.tokens.Token;

/**
 * Created by Pavels.Veretennikovs on 2015.06.27..
 */
public class ScannerAndBuilderSynchronizer extends ErrorSkippingScanner implements ScannerEx {
    private final PsiBuilder builder;
    private boolean peekMode;
    private int builderTokensBehind = 0;

    public ScannerAndBuilderSynchronizer(PsiBuilder builder) {
        super(new CharSequenceReader(builder.getOriginalText()));
        this.builder = builder;
    }

    @Override
    public Token getToken() {
        builderTokensBehind++;

        if (!peekMode) {
            catchUpWithScanner();
        }
        return super.getToken();
    }

    @Override
    public void catchUpWithScanner() {
        while (builderTokensBehind > 0) {
            builder.advanceLexer();
            builderTokensBehind--;
        }
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
