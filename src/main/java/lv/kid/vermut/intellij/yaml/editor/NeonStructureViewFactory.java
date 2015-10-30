package lv.kid.vermut.intellij.yaml.editor;

import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder;
import com.intellij.lang.PsiStructureViewFactory;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import lv.kid.vermut.intellij.yaml.psi.NeonFile;
import org.jetbrains.annotations.NotNull;

/**
 * Structure view
 */
public class NeonStructureViewFactory implements PsiStructureViewFactory {
	@Override
	public StructureViewBuilder getStructureViewBuilder(final PsiFile file) {
		if ( ! (file instanceof NeonFile)) return null;

		return new TreeBasedStructureViewBuilder() {
			@NotNull
			@Override
			public StructureViewModel createStructureViewModel(Editor editor) {
				return new StructureViewModelBase(file, new NeonStructureViewElement(file));
			}

			@NotNull
			public StructureViewModel createStructureViewModel() {
				return new StructureViewModelBase(file, new NeonStructureViewElement(file));
//				return new NeonStructureViewModel(file);
			}
		};
	}
}
