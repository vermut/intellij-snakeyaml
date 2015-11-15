package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.lexer.LexerPosition;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.text.CharSequenceReader;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.tokens.Token;

// TODO sobrat' v odno Parallizator i ErrorScanner
// ostavit' Lexer+PustojParallelizator i ParserEx
public class YamlLexer extends Lexer {
    private final boolean hideErrors;
    private ScannerEx myScanner;
    private Token myToken = null;
    private CharSequence myText;
    private int myEnd;
    private int myState;

    public YamlLexer(boolean hideErrors) {
        this.hideErrors = hideErrors;
    }

    @Override
    public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
        myText = buffer;
        myEnd = endOffset;

        if (hideErrors)
            myScanner = new ErrorFilterScanner(new CharSequenceReader(buffer.subSequence(0, endOffset)));
        else
            myScanner = new ErrorReportingScanner(new CharSequenceReader(buffer.subSequence(0, endOffset)));

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
        Token token = myScanner.getToken();
        if (token == null || token.getTokenId().equals(Token.ID.StreamEnd))
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
}
