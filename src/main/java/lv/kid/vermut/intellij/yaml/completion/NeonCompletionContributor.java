package lv.kid.vermut.intellij.yaml.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import lv.kid.vermut.intellij.yaml.reference.AnsibleReferenceContributor;
import lv.kid.vermut.intellij.yaml.reference.AnsibleUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Provides code completion
 */
public class NeonCompletionContributor extends CompletionContributor {
    public NeonCompletionContributor() {
        // extend(CompletionType.BASIC, StandardPatterns.instanceOf(PsiElement.class), new KeywordCompletionProvider());
        extend(CompletionType.BASIC, StandardPatterns.instanceOf(PsiElement.class), new ServiceCompletionProvider());
        // extend(CompletionType.BASIC, StandardPatterns.instanceOf(PsiElement.class), new ClassCompletionProvider());

        extend(CompletionType.BASIC,
                AnsibleReferenceContributor.roleRefPattern(),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
                        List<String> names = AnsibleUtil.findRoleNames(parameters.getPosition().getProject(), AnsibleUtil.ALL);
                        for (String name : names) {
                            result.addElement(LookupElementBuilder.create(name));
                        }
                    }
                });

    }
}
