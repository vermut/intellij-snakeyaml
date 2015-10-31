package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.psi.tree.IElementType;
import lv.kid.vermut.intellij.yaml.YamlLanguage;
import org.jetbrains.annotations.NotNull;


@Deprecated
public class NeonElementType extends IElementType {
	public NeonElementType(@NotNull String debugName) {
		super(debugName, YamlLanguage.INSTANCE);
	}

	public String toString() {
		return "[Yaml] " + super.toString();
	}
}
