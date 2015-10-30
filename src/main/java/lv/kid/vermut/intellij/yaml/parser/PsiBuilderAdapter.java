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
public class PsiBuilderAdapter implements Scanner {
    private final PsiBuilder builder;
    private final Scanner scanner;
    private boolean peekMode = false;
    private int builderStepsBehind = 0;

    public PsiBuilderAdapter(PsiBuilder builder) {
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
        builderStepsBehind++;

        if (!peekMode)
            while (builderStepsBehind > 0) {
                builder.advanceLexer();
                builderStepsBehind--;
            }

        return scanner.getToken();
    }

    public void setPeekMode(boolean newPeekMode) {
        if (!this.peekMode && newPeekMode) {
            // Create an copy of scanner to play with
            //  peekScanner = new ScannerImpl(new StreamReader((new CharSequenceReader(builder.getOriginalText().subSequence(builder.getCurrentOffset(), builder.getOriginalText().length())))));
        }

        this.peekMode = newPeekMode;
    }
}
