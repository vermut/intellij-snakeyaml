package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.testFramework.ParsingTestCase;

public class ParserTest extends ParsingTestCase {

    public ParserTest() {
        super("parser", "yml", new YamlParserDefinition());
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/data";
    }

    public void test01() {
        doTest(true);
    }

    public void test02() {
        doTest(true);
    }

    public void test03() {
        doTest(true);
    }

    public void test04() {
        doTest(true);
    }

    public void test05() {
        doTest(true);
    }

    public void test06() {
        doTest(true);
    }

    public void test07() {
        doTest(true);
    }

    public void test08() {
        doTest(true);
    }

    public void test09() {
        doTest(true);
    }

    public void test10() {
        doTest(true);
    }

    public void test11() {
        doTest(true);
    }

    public void test12() {
        doTest(true);
    }

    public void test13() {
        doTest(true);
    }

    public void test14() {
        doTest(true);
    }

    public void test15() {
        doTest(true);
    }

    public void test16() {
        doTest(true);
    }

    public void test17() {
        doTest(true);
    }

    public void test18() {
        doTest(true);
    }

    public void test19() {
        doTest(true);
    }

    public void test70() throws Exception {
        doCodeTest(
                "a: \n" +
                        "  - c\n" +
                        "  - d\n" +
                        "a2: b2\n" +
                "@bad\n" +
                "d: r1");
    }
}
