package cz.juzna.intellij.neon.reference;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import cz.juzna.intellij.neon.NeonLanguage;
import cz.juzna.intellij.neon.psi.NeonJinja;
import cz.juzna.intellij.neon.psi.NeonKey;
import cz.juzna.intellij.neon.psi.NeonReference;
import cz.juzna.intellij.neon.psi.NeonScalar;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Pavels.Veretennikovs on 2015.05.19..
 */
public class AnsibleReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(jinjaRefPattern(),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        String text = element.getText();
                        if (text != null) {
                            return new PsiReference[]{new AnsibleVariableReference(element, new TextRange(0, text.length()))};
                        }
                        return AnsibleVariableReference.EMPTY_ARRAY;
                    }
                });

        registrar.registerReferenceProvider(roleRefPattern(),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        String text = element.getText();
                        if (text != null) {
                            return new PsiReference[]{new AnsibleRoleReference(element, new TextRange(0, text.length()))};
                        }
                        return AnsibleVariableReference.EMPTY_ARRAY;
                    }
                });

        registrar.registerReferenceProvider(srcRefPattern(),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        String text = element.getText();
                        if (text != null) {
                            return new PsiReference[]{new AnsibleFileReference(element, new TextRange(0, text.length()))};
                        }
                        return AnsibleVariableReference.EMPTY_ARRAY;
                    }
                });
    }

    public static PsiElementPattern.Capture<NeonReference> jinjaRefPattern() {
        return PlatformPatterns.psiElement(NeonReference.class)
                .inside(NeonJinja.class)
                .withLanguage(NeonLanguage.INSTANCE);
    }

    public static PsiElementPattern.Capture<NeonScalar> roleRefPattern() {
        return PlatformPatterns.psiElement(NeonScalar.class)
                .afterSibling(PlatformPatterns.psiElement(NeonKey.class).withText("role"))
                .withLanguage(NeonLanguage.INSTANCE);
    }

    public static PsiElementPattern.Capture<NeonScalar> srcRefPattern() {
        return PlatformPatterns.psiElement(NeonScalar.class)
                .afterSibling(PlatformPatterns.psiElement(NeonKey.class).withText("src"))
                .withLanguage(NeonLanguage.INSTANCE);
    }
}