package lv.kid.vermut.intellij.yaml.lexer;


import com.intellij.lexer.Lexer;
import com.intellij.lexer.LexerPosition;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.text.CharSequenceReader;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.scanner.ScannerException;
import org.yaml.snakeyaml.scanner.ScannerImpl;
import org.yaml.snakeyaml.tokens.Token;

public class YamlLexer extends Lexer {
    private ScannerImpl myScanner;
    private Token myToken = null;
    private CharSequence myText;

    private int myEnd;
    private int myState;
    private StreamReader streamReader;

    @Override
    public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
        myText = buffer;
        myEnd = endOffset;
        streamReader = new StreamReader(new CharSequenceReader(buffer.subSequence(0, endOffset)));
        myScanner = new ScannerImpl(streamReader);
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
        if (currentTokenIsError())
            return YamlTokenTypes.YAML_Error;

        if (myToken == null || myToken.getTokenId().equals(Token.ID.StreamEnd))
            return null;
        return YamlTokenTypes.getIElementType(myToken.getTokenId());
    }

    private boolean currentTokenIsError() {
        return myToken != null && myToken.getTokenId() == null;
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
        try {
            if (currentTokenIsError())
                myToken = myScanner.peekToken();
            else {
                Token token = myScanner.getToken();
                if (token == null || token.getTokenId().equals(Token.ID.StreamEnd))
                    myToken = null;
                else
                    myToken = myScanner.peekToken();
            }
        } catch (ScannerException e) {
            Mark start = streamReader.getMark();

            do {
                streamReader.forward();
            } while (!((
                    streamReader.peek() == ' '
                            || streamReader.peek() == '\n'
                            || streamReader.peek() == '\t'
            )));
            streamReader.forward();

            myToken = new Token(start, streamReader.getMark()) {
                @Override
                public ID getTokenId() {
                    return null;
                }
            };

            // throw e;
            // Skip while problems
           /* streamReader.forward();
            advance(); */
            // myToken = null;
        }
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
