package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.testFramework.UsefulTestCase;
import com.intellij.testFramework.exceptionCases.AbstractExceptionCase;
import org.junit.Test;

/**
 *
 */
public class NeonLexer2Test extends UsefulTestCase {

    @Test
    public void test01() {
        Lexer l = LexerTest.createLexer();
        l.start("key: 'val'");

        assertEquals(YamlTokenTypes.YAML_BlockMappingStart, l.getTokenType());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Key, l.getTokenType());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_Scalar, l.getTokenType());
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
        assertEquals(5, l.getTokenStart());
        assertEquals(10, l.getTokenEnd());
        assertEquals("'val'", l.getTokenText());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_BlockEnd, l.getTokenType());
        l.advance();

        assertEquals(YamlTokenTypes.YAML_StreamEnd, l.getTokenType());
        l.advance();

        assertEquals(null, l.getTokenType());
    }

    @Test
    public void test02() {
        final Lexer l = LexerTest.createLexer();
        try {
            assertException(new AbstractExceptionCase() {
                @Override
                public Class getExpectedExceptionClass() {
                    return RuntimeException.class;
                }

                @Override
                public void tryClosure() throws Throwable {
                    l.start("key: 'val'", 4, 5);
                }
            });
        } catch (Throwable ignored) {
        }

        /*
        assertEquals(YamlTokenTypesOld.NEON_WHITESPACE, l.getTokenType());
        assertEquals(4, l.getTokenStart());
        assertEquals(5, l.getTokenEnd());
        assertEquals(" ", l.getTokenText());
        l.advance();

        assertEquals(null, l.getTokenType());*/
    }

}
