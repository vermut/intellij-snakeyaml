package cz.juzna.intellij.neon.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import cz.juzna.intellij.neon.NeonLanguage;
import cz.juzna.intellij.neon.psi.NeonKey;
import cz.juzna.intellij.neon.psi.NeonScalar;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Pavels.Veretennikovs on 2015.05.18..
 */
public class AnsibleRoleCompletionContributor extends CompletionContributor {
    public AnsibleRoleCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(NeonKey.class).withLanguage(NeonLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
                        result.addElement(LookupElementBuilder.create("Hello"));
                    }
                });
    }
}
