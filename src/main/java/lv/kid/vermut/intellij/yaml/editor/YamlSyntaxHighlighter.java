package lv.kid.vermut.intellij.yaml.editor;


import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import lv.kid.vermut.intellij.yaml.lexer.YamlHighlightingLexer;
import lv.kid.vermut.intellij.yaml.lexer.YamlLexer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static lv.kid.vermut.intellij.yaml.lexer.YamlTokenTypes.*;

public class YamlSyntaxHighlighter extends SyntaxHighlighterBase {

    // public static final TextAttributesKey UNKNOWN = TextAttributesKey.createTextAttributesKey("Bad character", HighlighterColors.BAD_CHARACTER);
    public static final TextAttributesKey DOC_SEPARATORS = TextAttributesKey.createTextAttributesKey("DOC_SEPARATORS", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey IDENTIFIER = TextAttributesKey.createTextAttributesKey("Identifier", DefaultLanguageHighlighterColors.INSTANCE_FIELD);
    public static final TextAttributesKey INTERPUNCTION = TextAttributesKey.createTextAttributesKey("Interpunction", DefaultLanguageHighlighterColors.DOT);
    public static final TextAttributesKey NUMBER = TextAttributesKey.createTextAttributesKey("Number", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey("Keyword", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey STRING = TextAttributesKey.createTextAttributesKey("Value", HighlighterColors.TEXT);
    public static final TextAttributesKey SPECIALS = TextAttributesKey.createTextAttributesKey("Specials", DefaultLanguageHighlighterColors.MARKUP_ATTRIBUTE);


    // Static container
    private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new HashMap<IElementType, TextAttributesKey>();

	// Fill in the map
	static {
        fillMap(ATTRIBUTES, IDENTIFIER, YAML_Key);
        fillMap(ATTRIBUTES, INTERPUNCTION, YAML_FlowSequenceStart, YAML_FlowSequenceEnd, YAML_BlockSequenceStart, YAML_Value);
        fillMap(ATTRIBUTES, NUMBER, YAML_Tag_INT, YAML_Tag_FLOAT, YAML_Tag_TIMESTAMP);
        fillMap(ATTRIBUTES, KEYWORD, YAML_Tag_NULL, YAML_Tag_BOOL);
        fillMap(ATTRIBUTES, STRING, YAML_Scalar);
        fillMap(ATTRIBUTES, DOC_SEPARATORS, YAML_DocumentStart, YAML_DocumentEnd);
        fillMap(ATTRIBUTES, SPECIALS, YAML_Anchor, YAML_Directive, YAML_Alias, YAML_StreamEnd, YAML_Tag);
    }


	@NotNull
	@Override
	public Lexer getHighlightingLexer() {
        return new YamlHighlightingLexer(new YamlLexer(true));
    }

	@NotNull
	@Override
	public TextAttributesKey[] getTokenHighlights(IElementType type) {
		return pack(ATTRIBUTES.get(type));
	}
}
