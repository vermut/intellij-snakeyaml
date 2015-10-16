package lv.kid.vermut.intellij.yaml.file;

import com.intellij.openapi.fileTypes.LanguageFileType;
import lv.kid.vermut.intellij.yaml.Yaml;
import lv.kid.vermut.intellij.yaml.YamlIcons;
import lv.kid.vermut.intellij.yaml.YamlLanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class YamlFileType extends LanguageFileType {
	public static final YamlFileType INSTANCE = new YamlFileType();
	public static final String DEFAULT_EXTENSION = "yml";
	public static final String EXTENSIONS = "yml;yaml";

	protected YamlFileType() {
		super(YamlLanguage.INSTANCE);
	}

	@NotNull
	public String getName() {
		return Yaml.LANGUAGE_NAME;
	}

	@NotNull
	public String getDescription() {
		return Yaml.LANGUAGE_DESCRIPTION;
	}

	@NotNull
	public String getDefaultExtension() {
		return DEFAULT_EXTENSION;
	}

	@NotNull
	public Icon getIcon() {
		return YamlIcons.FILETYPE_ICON;
	}
}

