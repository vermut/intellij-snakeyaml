package cz.juzna.intellij.neon;

import com.intellij.lang.Language;
import org.jetbrains.annotations.NotNull;

public class NeonLanguage extends Language {
	// singleton
	public static final NeonLanguage INSTANCE = new NeonLanguage();
	public static final String MIME_TYPE = "application/x-yaml";
	public static final String MIME_TYPE2 = "application/yaml";

	public NeonLanguage() {
		super("yaml", MIME_TYPE, MIME_TYPE2);
	}

	@NotNull
	@Override
	public String getDisplayName() {
		return "YAML/Ansible";
	}
}
