package lv.kid.vermut.intellij.ansible.parser;

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
        builder.advanceLexer();
        return scanner.getToken();
    }
}
