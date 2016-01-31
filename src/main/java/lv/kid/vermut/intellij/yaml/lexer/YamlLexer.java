package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.lexer.LexerPosition;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.text.CharSequenceReader;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.scanner.Scanner;
import org.yaml.snakeyaml.tokens.CommentToken;
import org.yaml.snakeyaml.tokens.Token;
import org.yaml.snakeyaml.tokens.WhitespaceToken;

public class YamlLexer extends Lexer {
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
            Token nextToken = myScanner.peekToken();
            if (!nextToken.getTokenId().equals(Token.ID.StreamEnd) && token.getEndMark().getIndex() < nextToken.getStartMark().getIndex())
                myToken = guessTokenType(token, nextToken);
            else
                myToken = nextToken;
        }
    }

    @NotNull
    private Token guessTokenType(Token token, Token nextToken) {
        // Maybe it's comment?
        if (myText.subSequence(token.getEndMark().getIndex(), nextToken.getStartMark().getIndex()).toString().contains("#"))
            return new CommentToken(token.getEndMark(), nextToken.getStartMark());

        return new WhitespaceToken(token.getEndMark(), nextToken.getStartMark());
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
