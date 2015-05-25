package cz.juzna.intellij.neon.reference;

import com.intellij.codeInsight.completion.CompletionUtilCore;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.util.codeInsight.CommentUtilCore;
import cz.juzna.intellij.neon.NeonIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavels.Veretennikovs on 2015.05.19..
 */
public class AnsibleRoleReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String key;

    public AnsibleRoleReference(PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
        key = element.getText(); // .substring(rangeInElement.getStartOffset(), rangeInElement.getEndOffset());

        // It should work another way, but I can't figure out how and why
        if (key.endsWith(CompletionUtilCore.DUMMY_IDENTIFIER_TRIMMED))
            key = key.substring(0, key.length() - CompletionUtilCore.DUMMY_IDENTIFIER_TRIMMED.length());
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        final List<PsiFile> properties = AnsibleUtil.findRoles(project, key);
        List<ResolveResult> results = new ArrayList<ResolveResult>();
        for (PsiFile property : properties) {
            results.add(new PsiElementResolveResult(property));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Project project = myElement.getProject();
        return AnsibleUtil.findRoleNames(project, key + ".*").toArray();
    }
}
