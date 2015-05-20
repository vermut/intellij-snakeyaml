package cz.juzna.intellij.neon.reference;

import com.intellij.psi.PsiElement;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import cz.juzna.intellij.neon.NeonIcons;
import cz.juzna.intellij.neon.psi.NeonKey;
import cz.juzna.intellij.neon.psi.NeonKeyValPair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Pavels.Veretennikovs on 2015.05.19..
 */
public class AnsibleVariableReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String key;

    public AnsibleVariableReference(PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
        key = element.getText(); // .substring(rangeInElement.getStartOffset(), rangeInElement.getEndOffset());
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        final List<NeonKey> properties = AnsibleUtil.findAllProperties(project, key);
        List<ResolveResult> results = new ArrayList<ResolveResult>();
        for (NeonKey property : properties) {
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
        final List<NeonKeyValPair> properties = AnsibleUtil.findAllProperties(project);
        List<LookupElement> variants = new ArrayList<LookupElement>();
        for (final NeonKeyValPair property : properties) {
            if (property.getKey() != null && property.getKeyText().length() > 0) {
                variants.add(LookupElementBuilder.create(property).
                                withIcon(NeonIcons.FILETYPE_ICON).
                                withTypeText(property.getContainingFile().getName())
                );
            }
        }
        return variants.toArray();
    }
}
