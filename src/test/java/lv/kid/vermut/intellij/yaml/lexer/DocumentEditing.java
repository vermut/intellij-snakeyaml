package lv.kid.vermut.intellij.yaml.lexer;

/**
 * Created by VermutMac on 11/22/2015.
 */

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.testFramework.FileBasedTestCaseHelper;
import com.intellij.testFramework.LightPlatformCodeInsightTestCase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DocumentEditing extends LightPlatformCodeInsightTestCase implements FileBasedTestCaseHelper {

    @NotNull
    @Override
    protected String getTestDataPath() {
        return "src/test/data/docEdit/";
    }

    public void testJustTyping() {
        new WriteCommandAction<Void>(null) {
            @Override
            protected void run(@NotNull Result<Void> result) throws Throwable {
                configureByFile("01.yml");
                type("a");
                type("a");
                type("a");
                checkResultByText(null, "aaa", true);
            }
        }.execute();
    }


    public void testTypingWithColon() {
        new WriteCommandAction<Void>(null) {
            @Override
            protected void run(@NotNull Result<Void> result) throws Throwable {
                configureByFile("01.yml");
                type("a");
                type(":");
                checkResultByText(null, "a:", true);
            }
        }.execute();
    }

    public void test73() {
        new WriteCommandAction<Void>(null) {
            @Override
            protected void run(@NotNull Result<Void> result) throws Throwable {
                configureByFile("01.yml");
                type(" c c");
            }
        }.execute();
    }

    @Nullable
    @Override
    public String getFileSuffix(String fileName) {
        return null;
    }


}
