package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.LexerTestCase;
import org.junit.Test;

@SuppressWarnings("JUnit4AnnotatedMethodInJUnit3TestCase")
public class YamlHighlightingLexerTest extends LexerTestCase {

    @Override
    protected Lexer createLexer() {
        return new YamlLexer(false);
    }

    @Override
    protected String getDirPath() {
        return LexerTest.SRC_TEST_DATA_LEXER;
    }

    private void startSkippingHeaders(Lexer l, String buf) {
        l.start(buf);

        assertAndAdvance(l, YamlTokenTypes.YAML_StreamStart);
        assertAndAdvance(l, YamlTokenTypes.YAML_BlockMappingStart);
    }

    @Test
    public void testKeys() {
        Lexer l = new YamlHighlightingLexer(createLexer());

        startSkippingHeaders(l, "key: val");
        assertAndAdvance(l, YamlTokenTypes.YAML_Key);
        assertAndAdvance(l, YamlTokenTypes.YAML_Key, 0, 3, "key");
        assertAndAdvance(l, YamlTokenTypes.YAML_Value, 3, 4, ":");
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar, 4, 8, " val");

        assertAndAdvance(l, YamlTokenTypes.YAML_BlockEnd);
        assertEquals(null, l.getTokenType());
    }


    @Test
    public void testKeywords() {
        Lexer l = new YamlHighlightingLexer(createLexer());

        l.start("[true,off,TruE,\"true\",12,12.3,null]");

        l.advance();
        assertAndAdvance(l, YamlTokenTypes.YAML_FlowSequenceStart, 0, 1, "[");
        assertAndAdvance(l, YamlTokenTypes.YAML_Tag_BOOL, 1, 5, "true");

        assertAndAdvance(l, YamlTokenTypes.YAML_FlowEntry, 5, 6, ",");
        assertAndAdvance(l, YamlTokenTypes.YAML_Tag_BOOL, 6, 9, "off");

        assertAndAdvance(l, YamlTokenTypes.YAML_FlowEntry, 9, 10, ",");
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar, 10, 14, "TruE");

        assertAndAdvance(l, YamlTokenTypes.YAML_FlowEntry, 14, 15, ",");
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar, 15, 21, "\"true\"");

        assertAndAdvance(l, YamlTokenTypes.YAML_FlowEntry);
        assertEquals(YamlTokenTypes.YAML_Tag_INT, l.getTokenType());
        assertEquals("12", l.getTokenText());
        l.advance();

        assertAndAdvance(l, YamlTokenTypes.YAML_FlowEntry);
        assertEquals(YamlTokenTypes.YAML_Tag_FLOAT, l.getTokenType());
        assertEquals("12.3", l.getTokenText());
        l.advance();

        assertAndAdvance(l, YamlTokenTypes.YAML_FlowEntry);
        assertEquals(YamlTokenTypes.YAML_Tag_NULL, l.getTokenType());
        assertEquals("null", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_FlowSequenceEnd, l.getTokenType());
        assertEquals("]", l.getTokenText());
        l.advance();

        //assertEquals(null, l.getTokenType());
    }

    @Test
    public void testError1() {
        Lexer l = new YamlHighlightingLexer(createLexer());

        startSkippingHeaders(l, "key: val\n" +
                "@bad!\n" +
                "key2: val2");

        assertAndAdvance(l, YamlTokenTypes.YAML_Key);
        assertAndAdvance(l, YamlTokenTypes.YAML_Key, 0, 3, "key");
        assertAndAdvance(l, YamlTokenTypes.YAML_Value, 3, 4, ":");
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar, 4, 8, " val");

        assertAndAdvance(l, YamlTokenTypes.YAML_Error);

        assertAndAdvance(l, YamlTokenTypes.YAML_Key);
        assertAndAdvance(l, YamlTokenTypes.YAML_Key, 15 + 0, 15 + 4, "key2");
        assertAndAdvance(l, YamlTokenTypes.YAML_Value, 15 + 4, 15 + 5, ":");
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar, 15 + 5, 15 + 10, " val2");

        assertAndAdvance(l, YamlTokenTypes.YAML_BlockEnd);
        assertEquals(null, l.getTokenType());
    }


    @Test
    public void testError2() {
        Lexer l = new YamlHighlightingLexer(createLexer());

        startSkippingHeaders(l, "a: x\n" +
                "b: y\n" +
                "ab");

        assertAndAdvance(l, YamlTokenTypes.YAML_Key);
        assertAndAdvance(l, YamlTokenTypes.YAML_Key);
        assertAndAdvance(l, YamlTokenTypes.YAML_Value);
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar, 2, 4, " x");

        assertAndAdvance(l, YamlTokenTypes.YAML_Key);
        assertAndAdvance(l, YamlTokenTypes.YAML_Key);
        assertAndAdvance(l, YamlTokenTypes.YAML_Value);
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar, 7, 9, " y");

        assertAndAdvance(l, YamlTokenTypes.YAML_Error);

        assertEquals(null, l.getTokenType());
    }

    @Test
    public void testError2Direct() {
        Lexer l = createLexer();

        startSkippingHeaders(l, "a: x\n" +
                "b: y\n" +
                "ab");

        assertAndAdvance(l, YamlTokenTypes.YAML_Key);
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar);
        assertAndAdvance(l, YamlTokenTypes.YAML_Value);
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar, 3, 4, "x");

        assertAndAdvance(l, YamlTokenTypes.YAML_Key);
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar);
        assertAndAdvance(l, YamlTokenTypes.YAML_Value);
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar, 8, 9, "y");

        assertAndAdvance(l, YamlTokenTypes.YAML_Error);

        assertEquals(null, l.getTokenType());
    }


    @Test
    public void testError3Direct() {
        Lexer l = createLexer();

        startSkippingHeaders(l, "a: x\n" +
                "b: y\n" +
                "@bad!");

        assertAndAdvance(l, YamlTokenTypes.YAML_Key);
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar);
        assertAndAdvance(l, YamlTokenTypes.YAML_Value);
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar, 3, 4, "x");

        assertAndAdvance(l, YamlTokenTypes.YAML_Key);
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar);
        assertAndAdvance(l, YamlTokenTypes.YAML_Value);
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar, 8, 9, "y");

        assertAndAdvance(l, YamlTokenTypes.YAML_Error);

        assertEquals(null, l.getTokenType());
    }

    @Test
    public void testErrorSkipping() {
        Lexer l = new YamlHighlightingLexer(createLexer());

        startSkippingHeaders(l, "key: val\n" +
                "@bad!\n" +
                "key2: val2");

        assertAndAdvance(l, YamlTokenTypes.YAML_Key);
        assertAndAdvance(l, YamlTokenTypes.YAML_Key, 0, 3, "key");
        assertAndAdvance(l, YamlTokenTypes.YAML_Value, 3, 4, ":");
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar, 4, 8, " val");

        assertAndAdvance(l, YamlTokenTypes.YAML_Error);

        assertAndAdvance(l, YamlTokenTypes.YAML_Key);
        assertAndAdvance(l, YamlTokenTypes.YAML_Key, 15, 19, "key2");
        assertAndAdvance(l, YamlTokenTypes.YAML_Value, 19, 20, ":");
        assertAndAdvance(l, YamlTokenTypes.YAML_Scalar, 20, 25, " val2");

        assertAndAdvance(l, YamlTokenTypes.YAML_BlockEnd);
        assertEquals(null, l.getTokenType());
    }


    @Test
    public void testPrinter() {
        String text = "key: val\n" +
                "@bad!\n" +
                "key2: val2" +
                "@bad!\n" +
                "key2: val2";
        System.out.println(printTokens(text, 0));
        System.out.println(printTokens(text, 8));

        checkCorrectRestart(text);
    }

    private void assertAndAdvance(Lexer l, IElementType expectedType) {
        assertEquals(expectedType, l.getTokenType());
        l.advance();
    }


    private void assertAndAdvance(Lexer l, IElementType expectedType, int start, int end, String text) {
        assertEquals(expectedType, l.getTokenType());
        assertEquals(start, l.getTokenStart());
        assertEquals(end, l.getTokenEnd());
        assertEquals(text, l.getTokenText());
        l.advance();
    }

}
