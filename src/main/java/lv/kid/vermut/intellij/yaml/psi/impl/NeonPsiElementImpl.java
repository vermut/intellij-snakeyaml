package lv.kid.vermut.intellij.yaml.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import org.jetbrains.annotations.NotNull;

/**
 * Base of all PsiElements (or unknown/not-recognized elements)
 */
public class NeonPsiElementImpl extends ASTWrapperPsiElement implements NavigatablePsiElement {
    public NeonPsiElementImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }

    @NotNull
    @Override
    public PsiReference[] getReferences()
    {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this);
    }
}
