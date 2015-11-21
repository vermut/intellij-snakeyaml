package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.testFramework.LexerTestCase;

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

    public void test70() throws Exception {
        doTest("a: b\n" +
                "@bad\n" +
                "d: r1");
    }

    public void test71() throws Exception {
        doTest("a: b\n" +
                "a");
    }

    public void test01() throws Exception {
        doFileTest("yml");
    }

    public void test02() throws Exception {
        doFileTest("yml");
    }

    public void test03() throws Exception {
        doFileTest("yml");
    }

    public void test04() throws Exception {
        doFileTest("yml");
    }

    public void test05() throws Exception {
        doFileTest("yml");
    }

    public void test06() throws Exception {
        doFileTest("yml");
    }

    public void test07() throws Exception {
        doFileTest("yml");
    }

    public void test08() throws Exception {
        doFileTest("yml");
    }

    public void test09() throws Exception {
        doFileTest("yml");
    }

    public void test10() throws Exception {
        doFileTest("yml");
    }

    public void test11() throws Exception {
        doFileTest("yml");
    }

    public void test12() throws Exception {
        doFileTest("yml");
    }

    public void test13() throws Exception {
        doFileTest("yml");
    }

    public void test14() throws Exception {
        doFileTest("yml");
    }

    public void test15() throws Exception {
        doFileTest("yml");
    }

    public void test16() throws Exception {
        doFileTest("yml");
    }

    public void test17() throws Exception {
        doFileTest("yml");
    }

    public void test18() throws Exception {
        doFileTest("yml");
    }

    public void test19() throws Exception {
        doFileTest("yml");
    }

}
