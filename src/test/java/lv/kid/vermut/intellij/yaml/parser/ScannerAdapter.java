package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.WhitespacesAndCommentsBinder;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.scanner.Scanner;
import org.yaml.snakeyaml.scanner.ScannerEx;
import org.yaml.snakeyaml.scanner.ScannerException;
import org.yaml.snakeyaml.scanner.ScannerImpl;
import org.yaml.snakeyaml.tokens.Token;

import java.io.Reader;

/**
 * Created by Pavels.Veretennikovs on 2015.06.27..
 */
public class ScannerAdapter implements ScannerEx {
    protected final static PsiBuilder.Marker EMPTY_MARKER = new PsiBuilder.Marker() {
        @NotNull
        @Override
        public PsiBuilder.Marker precede() {
            return null;
        }

        @Override
        public void drop() {

        }

        @Override
        public void rollbackTo() {

        }

        @Override
        public void done(@NotNull IElementType type) {

        }

        @Override
        public void collapse(@NotNull IElementType type) {

        }

        @Override
        public void doneBefore(@NotNull IElementType type, @NotNull PsiBuilder.Marker before) {

        }

        @Override
        public void doneBefore(@NotNull IElementType type, @NotNull PsiBuilder.Marker before, String errorMessage) {

        }

        @Override
        public void error(String message) {

        }

        @Override
        public void errorBefore(String message, @NotNull PsiBuilder.Marker before) {

        }

        @Override
        public void setCustomEdgeTokenBinders(@Nullable WhitespacesAndCommentsBinder left, @Nullable WhitespacesAndCommentsBinder right) {

        }
    };
    private static final Token.ID ERROR_TOKEN_ID = null;
    private final Scanner scanner;
    private final StreamReader streamReader;
    private Token myToken;

    public ScannerAdapter(Reader reader) {
        streamReader = new StreamReader(reader);
        scanner = new ScannerImpl(streamReader);
    }

    @Override
    public boolean checkToken(Token.ID... choices) {
        return scanner.checkToken(choices);
    }

    @Override
    public Token peekToken() {
        try {
            return scanner.peekToken();
        } catch (ScannerException e) {
            Mark start = streamReader.getMark();

            do {
                streamReader.forward();
            } while (!readerOnWhitespace());
            streamReader.forward();

            myToken = new Token(start, streamReader.getMark()) {
                @Override
                public ID getTokenId() {
                    return ERROR_TOKEN_ID;
                }
            };
        }
    }

    @Override
    public Token getToken() {
        return scanner.getToken();
    }

    @Override
    public void catchUpWithScanner() {
    }

    @Override
    public void setPeekMode(boolean peekMode) {
    }

    @Override
    public PsiBuilder.Marker getMarker() {
        return EMPTY_MARKER;
    }

    @Override
    public void markError(String error) {
    }

    protected boolean currentTokenIsError() {
        return myToken != null && myToken.getTokenId() == ERROR_TOKEN_ID;
    }

    private boolean readerOnWhitespace() {
        return streamReader.peek() == ' '
                || streamReader.peek() == '\n'
                || streamReader.peek() == '\t';
    }
}
