package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.testFramework.ParsingTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.composer.Composer;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.parser.ParserImplEx;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class ParserTest extends ParsingTestCase {

    private final Yaml yaml = new Yaml() {
        @Override
        public Node compose(Reader yaml) {
            Composer composer = new Composer(new ParserImplEx(new NoBuilderScannerAdapter(yaml)), this.resolver);
            this.constructor.setComposer(composer);
            return composer.getSingleNode();
        }
    };

    public ParserTest() {
        super("", "yml", new YamlParserDefinition());
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/data/parser";
    }

    protected void doTest(boolean checkResult, boolean suppressErrors) {
        try {
            yaml.compose(new StringReader(loadFile(getTestName(true) + "." + myFileExt)));
        } catch (IOException ignored) {

        }

        doTest(true);
        if (!suppressErrors) {
            Assert.assertFalse(
                    "PsiFile contains error elements",
                    toParseTreeText(myFile, true, includeRanges()).contains("PsiErrorElement")
            );
        }
    }

    @Test
    public void test01() {
        doTest(true, false);
    }

    @Test
    public void test02() {
        doTest(true, false);
    }

    @Test
    public void test03() {
        doTest(true, false);
    }

    @Test
    public void test04() {
        doTest(true, false);
    }

    @Test
    public void test05() {
        doTest(true, false);
    }

    @Test
    public void test06() {
        doTest(true, false);
    }

    @Test
    public void test07() {
        doTest(true, false);
    }

    @Test
    public void test08() {
        doTest(true, false);
    }

    @Test
    public void test09() {
        doTest(true, false);
    }

    @Test
    public void test10() {
        doTest(true, false);
    }

    @Test
    public void test11() {
        doTest(true, false);
    }

    @Test
    public void test12() {
        doTest(true, true);
    }

    @Test
    public void test13() {
        doTest(true, true);
    }

    @Test
    public void test14() {
        doTest(true, true);
    }

    @Test
    public void test15() {
        doTest(true, true);
    }

    @Test
    public void test16() {
        doTest(true, false);
    }

    @Test
    public void test17() {
        doTest(true, false);
    }

    public void test18() {
        doTest(true, false);
    }

    public void test19() {
        doTest(true, false);
    }

}
