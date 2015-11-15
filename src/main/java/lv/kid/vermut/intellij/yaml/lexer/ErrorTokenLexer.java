package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.psi.tree.IElementType;
import lv.kid.vermut.intellij.yaml.parser.ScannerAdapter;

/**
 * Created by VermutMac on 11/15/2015.
 */
public class ErrorTokenLexer extends YamlLexer {
    @Override
    public IElementType getTokenType() {
        if (ScannerAdapter.currentTokenIsError(myToken))
            return YamlTokenTypes.YAML_Error;

        return super.getTokenType();
    }

    @Override
    public void advance() {
        if (ScannerAdapter.currentTokenIsError(myToken))
            myToken = myScanner.peekToken();
        else
            super.advance();
    }
}
