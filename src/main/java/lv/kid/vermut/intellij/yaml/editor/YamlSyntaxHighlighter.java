package lv.kid.vermut.intellij.yaml.editor;


import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import lv.kid.vermut.intellij.yaml.lexer.YamlFlexLexer;
import lv.kid.vermut.intellij.yaml.lexer.YamlHighlightingLexer;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import static lv.kid.vermut.intellij.yaml.lexer.YamlTokenTypes.*;

public class YamlSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey UNKNOWN = TextAttributesKey.createTextAttributesKey("Yaml Bad Character", HighlighterColors.BAD_CHARACTER);
    public static final TextAttributesKey DOC_SEPARATORS = TextAttributesKey.createTextAttributesKey("Yaml DocumentStart/DocumentEnd", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey COMMENTS = TextAttributesKey.createTextAttributesKey("Yaml Comments", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey IDENTIFIER = TextAttributesKey.createTextAttributesKey("Yaml Identifier", DefaultLanguageHighlighterColors.INSTANCE_FIELD);
    public static final TextAttributesKey INTERPUNCTION = TextAttributesKey.createTextAttributesKey("Yaml Interpunction", DefaultLanguageHighlighterColors.DOT);
    public static final TextAttributesKey NUMBER = TextAttributesKey.createTextAttributesKey("Yaml Number", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey("Yaml Keyword", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey STRING = TextAttributesKey.createTextAttributesKey("Yaml Scalars", HighlighterColors.TEXT);
    public static final TextAttributesKey SPECIALS = TextAttributesKey.createTextAttributesKey("Yaml Specials", DefaultLanguageHighlighterColors.MARKUP_ATTRIBUTE);

    // Static container
    private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new HashMap<IElementType, TextAttributesKey>();

	// Fill in the map
	static {
        fillMap(ATTRIBUTES, IDENTIFIER, YAML_Key);
        fillMap(ATTRIBUTES, INTERPUNCTION, YAML_FlowSequenceStart, YAML_FlowSequenceEnd, YAML_BlockSequenceStart, YAML_FlowMappingStart, YAML_FlowMappingEnd, YAML_Value);
        fillMap(ATTRIBUTES, NUMBER, YAML_Tag_INT, YAML_Tag_FLOAT, YAML_Tag_TIMESTAMP);
        fillMap(ATTRIBUTES, KEYWORD, YAML_Tag_NULL, YAML_Tag_BOOL);
        fillMap(ATTRIBUTES, STRING, YAML_Scalar);
        fillMap(ATTRIBUTES, DOC_SEPARATORS, YAML_DocumentStart, YAML_DocumentEnd);
        fillMap(ATTRIBUTES, COMMENTS, YAML_Comment);
        fillMap(ATTRIBUTES, SPECIALS, YAML_Anchor, YAML_Directive, YAML_Alias, YAML_StreamEnd, YAML_Tag);
        fillMap(ATTRIBUTES, UNKNOWN, YAML_Error);
    }


	@NotNull
	@Override
	public Lexer getHighlightingLexer() {
        return new YamlHighlightingLexer(new FlexAdapter(new YamlFlexLexer((Reader) null)));
    }

	@NotNull
	@Override
	public TextAttributesKey[] getTokenHighlights(IElementType type) {
		return pack(ATTRIBUTES.get(type));
	}
}
