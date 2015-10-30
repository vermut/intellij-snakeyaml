package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.psi.tree.IElementType;
import lv.kid.vermut.intellij.yaml.YamlLanguage;
import org.jetbrains.annotations.NotNull;


public class YamlTokenType extends IElementType {
	public YamlTokenType(@NotNull String debugName) {
		super(debugName, YamlLanguage.INSTANCE);
	}

	public String toString() {
		return "[YAML Token] " + super.toString();
	}
}
