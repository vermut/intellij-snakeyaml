package cz.juzna.intellij.neon.reference;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import cz.juzna.intellij.neon.NeonIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavels.Veretennikovs on 2015.05.19..
 */
public class AnsibleFileReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String key;

    public AnsibleFileReference(PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
        key = element.getText(); // .substring(rangeInElement.getStartOffset(), rangeInElement.getEndOffset());
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        final List<PsiFile> properties = AnsibleUtil.findFiles(project, "/" + key);
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
        final List<PsiFile> properties = AnsibleUtil.findFiles(project, "/" + key);
        List<LookupElementBuilder> variants = new ArrayList<LookupElementBuilder>();
        for (PsiFile property : properties) {
            variants.add(LookupElementBuilder.create(property).
                            withIcon(NeonIcons.FILETYPE_ICON).
                            withTypeText(property.getContainingFile().getName())
            );
        }
        return variants.toArray();
    }
}
