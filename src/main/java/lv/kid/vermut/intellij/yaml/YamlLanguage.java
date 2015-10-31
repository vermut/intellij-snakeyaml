package lv.kid.vermut.intellij.yaml;

import com.intellij.lang.Language;
import org.jetbrains.annotations.NotNull;

public class YamlLanguage extends Language {
	// singleton
	public static final YamlLanguage INSTANCE = new YamlLanguage();
	public static final String MIME_TYPE = "application/x-yaml";
	public static final String MIME_TYPE2 = "application/yaml";

	public YamlLanguage() {
		super("snakeyaml", MIME_TYPE, MIME_TYPE2);
	}

	@NotNull
	@Override
	public String getDisplayName() {
		return "SnakeYAML";
	}
}
