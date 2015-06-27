package cz.juzna.intellij.neon.lexer;


import com.intellij.lexer.MergingLexerAdapter;
import com.intellij.psi.tree.TokenSet;
import lv.kid.vermut.intellij.ansible.parser.ScannerFacade;


public class NeonLexer extends MergingLexerAdapter {
	// To be merged
	private static final TokenSet TOKENS_TO_MERGE = TokenSet.create(
			NeonTokenTypes.NEON_COMMENT,
			NeonTokenTypes.NEON_WHITESPACE
	);

	public NeonLexer() {
		super(new ScannerFacade(), TOKENS_TO_MERGE);
	}
}
