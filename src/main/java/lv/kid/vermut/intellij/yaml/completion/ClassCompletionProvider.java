package lv.kid.vermut.intellij.yaml.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

/*
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.completion.ClassUsageContext;
import com.jetbrains.php.completion.PhpLookupElement;
import com.jetbrains.php.completion.PhpVariantsUtil;
import com.jetbrains.php.lang.psi.elements.ClassReference;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
*/

/**
 * Complete class names
 */
public class ClassCompletionProvider extends CompletionProvider<CompletionParameters> {

	public ClassCompletionProvider() {
		super();
	}

	@Override
	protected void addCompletions(@NotNull CompletionParameters params,
	                              ProcessingContext ctx,
	                              @NotNull CompletionResultSet results) {
	/*	Collection<PhpNamedElement> variants = new THashSet<PhpNamedElement>();

		PsiElement curr = params.getPosition().getOriginalElement();
		if (!(curr.getParent() instanceof NeonEntity) && !(curr.getParent() instanceof NeonScalar)) return;

		PhpIndex phpIndex = PhpIndex.getInstance(curr.getProject());
		ClassUsageContext context = (curr instanceof ClassReference) ? ((ClassReference)curr).getUsageContext() : new ClassUsageContext(false);

		// Prepare list of possible variants
		for (String name : phpIndex.getAllClassNames(results.getPrefixMatcher())) {
			variants.addAll(phpIndex.getClassesByName(name));
			variants.addAll(phpIndex.getInterfacesByName(name));
		}

		// Add variants
		for (PhpNamedElement item : variants) {
			PhpLookupElement lookupItem = PhpVariantsUtil.getLookupItem(item, null);
			lookupItem.handler = PhpReferenceInsertHandler.getInstance();

			results.addElement(lookupItem);
		}*/
	}

}
