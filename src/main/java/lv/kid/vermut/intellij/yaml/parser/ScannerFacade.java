package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.lexer.Lexer;
import com.intellij.lexer.LexerPosition;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.text.CharSequenceReader;
import lv.kid.vermut.intellij.yaml.lexer.YamlTokenTypes;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.scanner.Scanner;
import org.yaml.snakeyaml.scanner.ScannerImpl;
import org.yaml.snakeyaml.tokens.Token;

/**
 * Created by Pavels.Veretennikovs on 2015.06.27..
 */
public class ScannerFacade extends Lexer {
    private Scanner myScanner;
    private Token myToken = null;
    private CharSequence myText;

    private int myEnd;
    private int myState;

    @Override
    public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
        myText = buffer;
        myEnd = endOffset;
        myScanner = new ScannerImpl(new StreamReader(new CharSequenceReader(buffer)));
        myToken = myScanner.peekToken();
        myState = initialState;
    }

    @Override
    public int getState() {
        return myState;
    }

    @Override
    public IElementType getTokenType() {
        if (myToken == null)
            return null;
        return YamlTokenTypes.getIElementType(myToken.getTokenId());
    }

    @Override
    public int getTokenStart() {
        return myToken.getStartMark().getIndex();
    }

    @Override
    public int getTokenEnd() {
        return myToken.getEndMark().getIndex();
    }

    @Override
    public void advance() {
        if (myScanner.getToken().getTokenId().equals(Token.ID.StreamEnd))
            myToken = null;
        else
            myToken = myScanner.peekToken();
    }

    @NotNull
    @Override
    public LexerPosition getCurrentPosition() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void restore(@NotNull LexerPosition position) {
        throw new RuntimeException("Not supported");
    }

    @NotNull
    @Override
    public CharSequence getBufferSequence() {
        return myText;
    }

    @Override
    public int getBufferEnd() {
        return myEnd;
    }

    protected void locffateToken() {

        try {
            myToken = myScanner.peekToken();
            //myState = .yystate();
            myToken = myScanner.getToken();
            System.out.println("myToken = " + myToken);
        } catch (Error e) {
            // add lexer class name to the error
            final Error error = new Error(myScanner.getClass().getName() + ": " + e.getMessage());
            error.setStackTrace(e.getStackTrace());
            throw error;
        }
    }
}
