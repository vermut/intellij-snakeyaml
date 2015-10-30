package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.psi.tree.IElementType;
import lv.kid.vermut.intellij.yaml.YamlLanguage;
import org.jetbrains.annotations.NotNull;


public class NeonTokenType extends IElementType {
	public NeonTokenType(@NotNull String debugName) {
		super(debugName, YamlLanguage.INSTANCE);
	}

	public String toString() {
		return "[Yaml] " + super.toString();
	}
}
