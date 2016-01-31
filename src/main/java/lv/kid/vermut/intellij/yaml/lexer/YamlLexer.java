package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.lexer.LexerPosition;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.text.CharSequenceReader;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.scanner.Scanner;
import org.yaml.snakeyaml.tokens.Token;
import org.yaml.snakeyaml.tokens.WhitespaceToken;

public class YamlLexer extends Lexer {
    long lastTokenEndIndex = 0;
    int i = 0;
    int lastTokenEnd = 0;
    private Scanner myScanner;
    private Token myToken = null;
    private CharSequence myText;
    private int myEnd;
    private int myState;

    @Override
    public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
        myText = buffer;
        myEnd = endOffset;

        myScanner = new ErrorSkippingScanner(new CharSequenceReader(buffer.subSequence(0, endOffset)));

        myToken = myScanner.peekToken();
        myState = initialState;

        if (startOffset > 0) {
            // Here comes ugly. Emulate incremental lexer by re-lexing from start
            while (myScanner.peekToken().getStartMark().getIndex() < startOffset) {
                advance();
            }
        }
    }

    @Override
    public int getState() {
        return myState;
    }

    @Override
    public IElementType getTokenType() {
        if (myToken == null || myToken.getTokenId().equals(Token.ID.StreamEnd))
            return null;
        return YamlTokenTypes.getIElementType(myToken.getTokenId());
    }


    @Override
    public int getTokenStart() {
        return myToken.getStartMark().getIndex();
    }

    @Override
    public int getTokenEnd() {
        if (myToken == null)
            return myEnd;

        return myToken.getEndMark().getIndex();
    }

    @Override
    public void advance() {
        Token token;

        // Forward only on non-whitespace (whitespace is virtual)
        if (myToken.getTokenId() != Token.ID.Whitespace)
            token = myScanner.getToken();
        else
            token = myScanner.peekToken();


        if (token == null || token.getTokenId().equals(Token.ID.StreamEnd))
            myToken = null;
        else {
            if (token.getEndMark().getIndex()  < myScanner.peekToken().getStartMark().getIndex())
                myToken = new WhitespaceToken(shiftMark(token.getEndMark(), 0), shiftMark(myScanner.peekToken().getStartMark(), 0));
            else
                myToken = myScanner.peekToken();
        }

    }

    private Mark shiftMark(Mark mark, int deltaIndex) {
        return new Mark(mark.getName(), mark.getIndex() + deltaIndex, mark.getLine(), mark.getColumn() + deltaIndex, null, 0);
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
}
