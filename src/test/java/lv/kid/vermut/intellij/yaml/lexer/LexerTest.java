package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.testFramework.LexerTestCase;
import org.junit.Test;

/**
 *
 */
@SuppressWarnings({"unchecked", "JUnit4AnnotatedMethodInJUnit3TestCase"})
public class LexerTest extends LexerTestCase {

    public static final String SRC_TEST_DATA_LEXER = "src/test/data/lexer";

    @Override
    protected Lexer createLexer() {
        return new YamlLexer(true);
    }

    @Override
    protected String getDirPath() {
        return SRC_TEST_DATA_LEXER;
    }

    @Test
    public void test01() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test02() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test03() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test04() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test05() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test06() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test07() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test08() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test09() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test10() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test11() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test12() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test13() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test14() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test15() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test16() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test17() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test18() throws Exception {
        doFileTest("yml");
    }

    @Test
    public void test19() throws Exception {
        doFileTest("yml");
    }

}
