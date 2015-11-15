package lv.kid.vermut.intellij.yaml.lexer;

import lv.kid.vermut.intellij.yaml.parser.ScannerAdapter;

/**
 * Created by VermutMac on 11/15/2015.
 */
public class HideErrorLexer extends ErrorTokenLexer {
    @Override
    public void advance() {
        super.advance();
        while (ScannerAdapter.currentTokenIsError(myToken))
            super.advance();
    }
}
