package cz.juzna.intellij.neon.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import cz.juzna.intellij.neon.psi.NeonKeyValPair;
import cz.juzna.intellij.neon.reference.AnsibleUtil;
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
                StandardPatterns.instanceOf(PsiElement.class),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
                        PsiElement curr = parameters.getPosition().getOriginalElement();
                        PsiElement parent = curr.getParent().getParent();
                        if (parent instanceof NeonKeyValPair) {
                            if (((NeonKeyValPair) parent).getKeyText().equals("role")) {
                                List<String> names = AnsibleUtil.findRoleNames(curr.getProject(), null);
                                for (String name : names) {
                                    result.addElement(LookupElementBuilder.create(name));
                                }
                            }
                        }
                    }
                });
    }
}
