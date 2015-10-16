package lv.kid.vermut.intellij.yaml;

import com.intellij.lang.Commenter;
import org.jetbrains.annotations.NotNull;

/**
 * Comment line/block
 */
public class YamlCommenter implements Commenter {
	@NotNull
	@Override
	public String getLineCommentPrefix() {
		return "#";
	}

	@Override
	public String getBlockCommentPrefix() {
		return null;
	}

	@Override
	public String getBlockCommentSuffix() {
		return null;
	}

	@Override
	public String getCommentedBlockCommentPrefix() {
		return null;
	}

	@Override
	public String getCommentedBlockCommentSuffix() {
		return null;
	}
}
