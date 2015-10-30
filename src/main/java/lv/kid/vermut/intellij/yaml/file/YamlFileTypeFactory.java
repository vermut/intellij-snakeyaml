package lv.kid.vermut.intellij.yaml.file;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class YamlFileTypeFactory extends FileTypeFactory {
	public void createFileTypes(@NotNull FileTypeConsumer consumer) {
		consumer.consume(YamlFileType.INSTANCE, YamlFileType.EXTENSIONS);
	}
}
