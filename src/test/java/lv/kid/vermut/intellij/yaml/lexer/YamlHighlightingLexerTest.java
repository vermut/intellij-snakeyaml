package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.lexer.Lexer;
import org.junit.Test;

@SuppressWarnings("JUnit4AnnotatedMethodInJUnit3TestCase")
public class YamlHighlightingLexerTest extends LexerTest {

	@Test
    public void testKeys() {
        Lexer l = new YamlHighlightingLexer(createLexer());

		l.start("key: val");

        assertEquals(YamlTokenTypes.YAML_BlockMappingStart, l.getTokenType());
        l.advance();
        assertEquals(YamlTokenTypes.YAML_Key, l.getTokenType());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Key, l.getTokenType()); // this is important
        assertEquals(0, l.getTokenStart());
		assertEquals(3, l.getTokenEnd());
		assertEquals("key", l.getTokenText());
		l.advance();

        assertEquals(YamlTokenTypes.YAML_Value, l.getTokenType());
        assertEquals(3, l.getTokenStart());
		assertEquals(4, l.getTokenEnd());
		assertEquals(":", l.getTokenText());
		l.advance();

        assertEquals(YamlTokenTypes.YAML_Scalar, l.getTokenType());
        assertEquals(4, l.getTokenStart());
		assertEquals(8, l.getTokenEnd());
        assertEquals(" val", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_BlockEnd, l.getTokenType());
        l.advance();
        assertEquals(null, l.getTokenType());
	}

	@Test
	public void testKeywords() {
        Lexer l = new YamlHighlightingLexer(createLexer());

        l.start("[true,off,TruE,\"true\",12,12.3,null]");

        assertEquals(YamlTokenTypes.YAML_FlowSequenceStart, l.getTokenType());
        assertEquals(0, l.getTokenStart());
        assertEquals(1, l.getTokenEnd());
        assertEquals("[", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Tag_BOOL, l.getTokenType());
        assertEquals(1, l.getTokenStart());
		assertEquals(5, l.getTokenEnd());
		assertEquals("true", l.getTokenText());
		l.advance();

        assertEquals(YamlTokenTypes.YAML_FlowEntry, l.getTokenType());
        assertEquals(5, l.getTokenStart());
		assertEquals(6, l.getTokenEnd());
		assertEquals(",", l.getTokenText());
		l.advance();

        assertEquals(YamlTokenTypes.YAML_Tag_BOOL, l.getTokenType());
        assertEquals(6, l.getTokenStart());
		assertEquals(9, l.getTokenEnd());
		assertEquals("off", l.getTokenText());
		l.advance();

        assertEquals(YamlTokenTypes.YAML_FlowEntry, l.getTokenType());
        assertEquals(9, l.getTokenStart());
		assertEquals(10, l.getTokenEnd());
		assertEquals(",", l.getTokenText());
		l.advance();

        assertEquals(YamlTokenTypes.YAML_Scalar, l.getTokenType());
        assertEquals(10, l.getTokenStart());
		assertEquals(14, l.getTokenEnd());
		assertEquals("TruE", l.getTokenText());
		l.advance();

        assertEquals(YamlTokenTypes.YAML_FlowEntry, l.getTokenType());
        assertEquals(14, l.getTokenStart());
		assertEquals(15, l.getTokenEnd());
		assertEquals(",", l.getTokenText());
		l.advance();

        assertEquals(YamlTokenTypes.YAML_Scalar, l.getTokenType());
        assertEquals(15, l.getTokenStart());
		assertEquals(21, l.getTokenEnd());
		assertEquals("\"true\"", l.getTokenText());
		l.advance();

        assertEquals(YamlTokenTypes.YAML_FlowEntry, l.getTokenType());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Tag_INT, l.getTokenType());
        assertEquals("12", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_FlowEntry, l.getTokenType());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Tag_FLOAT, l.getTokenType());
        assertEquals("12.3", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_FlowEntry, l.getTokenType());
        l.advance();

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

        l.start("key: val\n" +
                "@bad!\n" +
                "key2: val2");

        assertEquals(YamlTokenTypes.YAML_BlockMappingStart, l.getTokenType());
        l.advance();
        assertEquals(YamlTokenTypes.YAML_Key, l.getTokenType());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Key, l.getTokenType()); // this is important
        assertEquals(0, l.getTokenStart());
        assertEquals(3, l.getTokenEnd());
        assertEquals("key", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Value, l.getTokenType());
        assertEquals(3, l.getTokenStart());
        assertEquals(4, l.getTokenEnd());
        assertEquals(":", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Scalar, l.getTokenType());
        assertEquals(4, l.getTokenStart());
        assertEquals(8, l.getTokenEnd());
        assertEquals(" val", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Error, l.getTokenType());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Key, l.getTokenType());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Key, l.getTokenType()); // this is important
        assertEquals(15 + 0, l.getTokenStart());
        assertEquals(15 + 4, l.getTokenEnd());
        assertEquals("key2", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Value, l.getTokenType());
        assertEquals(15 + 4, l.getTokenStart());
        assertEquals(15 + 5, l.getTokenEnd());
        assertEquals(":", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Scalar, l.getTokenType());
        assertEquals(15 + 5, l.getTokenStart());
        assertEquals(15 + 10, l.getTokenEnd());
        assertEquals(" val2", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_BlockEnd, l.getTokenType());
        l.advance();
        assertEquals(null, l.getTokenType());
    }

    @Test
    public void testErrorSkipping() {
        Lexer l = new YamlHighlightingLexer(createLexer());

        l.start("key: val\n" +
                "@bad!\n" +
                "key2: val2");

        assertEquals(YamlTokenTypes.YAML_BlockMappingStart, l.getTokenType());
        l.advance();
        assertEquals(YamlTokenTypes.YAML_Key, l.getTokenType());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Key, l.getTokenType()); // this is important
        assertEquals(0, l.getTokenStart());
        assertEquals(3, l.getTokenEnd());
        assertEquals("key", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Value, l.getTokenType());
        assertEquals(3, l.getTokenStart());
        assertEquals(4, l.getTokenEnd());
        assertEquals(":", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Scalar, l.getTokenType());
        assertEquals(4, l.getTokenStart());
        assertEquals(8, l.getTokenEnd());
        assertEquals(" val", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Error, l.getTokenType());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Key, l.getTokenType());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Key, l.getTokenType()); // this is important
        assertEquals(15 + 0, l.getTokenStart());
        assertEquals(15 + 4, l.getTokenEnd());
        assertEquals("key2", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Value, l.getTokenType());
        assertEquals(15 + 4, l.getTokenStart());
        assertEquals(15 + 5, l.getTokenEnd());
        assertEquals(":", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Scalar, l.getTokenType());
        assertEquals(15 + 5, l.getTokenStart());
        assertEquals(15 + 10, l.getTokenEnd());
        assertEquals(" val2", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_BlockEnd, l.getTokenType());
        l.advance();
        assertEquals(null, l.getTokenType());
    }

    @Test
    public void printer() {
        Lexer l1 = new YamlLexer(true);

        String buf = "key: val\n" +
                "@bad!\n" +
                "key2: val2" +
                "@bad!\n" +
                "key2: val2";
        l1.start(buf);
        // l2.start(buf);

        while (l1.getTokenType() != null) {
            System.out.println("l.getTokenType() = " + l1.getTokenType());
            // assertEquals(l1.getTokenType(), l2.getTokenType());

            l1.advance();
            // l2.advance();
        }
    }
}
