package lv.kid.vermut.intellij.yaml.lexer;

import com.google.common.collect.ImmutableMap;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import lv.kid.vermut.intellij.yaml.parser.YamlTokenType;

import java.util.Map;

/**
 * Types of tokens returned form lexer
 *
 * @author Jan Dolecek - juzna.cz@gmail.com
 */
@Deprecated
public interface YamlTokenTypesOld
{
	IElementType NEON_STRING = new YamlTokenType("string");
	IElementType NEON_SYMBOL = new YamlTokenType("symbol"); // use a symbol or brace instead (see below)
	IElementType NEON_COMMENT = new YamlTokenType("comment");
	IElementType NEON_INDENT = new YamlTokenType("indent");
	IElementType NEON_LITERAL = new YamlTokenType("literal");
	IElementType NEON_KEYWORD = new YamlTokenType("keyword");
	IElementType NEON_WHITESPACE = TokenType.WHITE_SPACE;
	IElementType NEON_UNKNOWN = TokenType.BAD_CHARACTER;

	IElementType NEON_TAG = new YamlTokenType("tag");
	IElementType NEON_HEADER = new YamlTokenType("header");

	// symbols
	IElementType NEON_COLON = new YamlTokenType(":");
	IElementType NEON_ASSIGNMENT = new YamlTokenType("=");
	IElementType NEON_ARRAY_BULLET = new YamlTokenType("-");
	IElementType NEON_ITEM_DELIMITER = new YamlTokenType(",");
	IElementType NEON_LINE_CONTINUATION = new YamlTokenType(">");

	// braces
	IElementType NEON_LPAREN = new YamlTokenType("(");
	IElementType NEON_RPAREN = new YamlTokenType(")");
	IElementType NEON_LBRACE_CURLY = new YamlTokenType("{");
	IElementType NEON_RBRACE_CURLY = new YamlTokenType("}");
	IElementType NEON_LBRACE_JINJA = new YamlTokenType("{{");
	IElementType NEON_RBRACE_JINJA = new YamlTokenType("}}");
	IElementType NEON_LBRACE_JINJA_CODE = new YamlTokenType("{%");
	IElementType NEON_RBRACE_JINJA_CODE = new YamlTokenType("%}");
	IElementType NEON_LBRACE_SQUARE = new YamlTokenType("[");
	IElementType NEON_RBRACE_SQUARE = new YamlTokenType("]");

	// the rest are deprecated and will be removed
	IElementType NEON_IDENTIFIER = new YamlTokenType("identifier");
	IElementType NEON_EOL = new YamlTokenType("eol");
	IElementType NEON_VARIABLE = new YamlTokenType("variable");
	IElementType NEON_NUMBER = new YamlTokenType("number");
	IElementType NEON_REFERENCE = new YamlTokenType("reference");
	IElementType NEON_BLOCK_INHERITENCE = new YamlTokenType("<");
	IElementType NEON_QUOTE = new YamlTokenType("\"");
	IElementType NEON_DOUBLE_COLON = new YamlTokenType("::");
	IElementType NEON_DOLLAR = new YamlTokenType("$");
	IElementType NEON_AT = new YamlTokenType("@");


	// special tokens (identifier in block header or as array key)
	IElementType NEON_KEY = new YamlTokenType("key");


	// sets
	TokenSet WHITESPACES = TokenSet.create(NEON_WHITESPACE);
	TokenSet COMMENTS = TokenSet.create(NEON_COMMENT);
	TokenSet STRING_LITERALS = TokenSet.create(NEON_LITERAL, NEON_STRING);
	TokenSet ASSIGNMENTS = TokenSet.create(NEON_ASSIGNMENT, NEON_COLON);
	TokenSet OPEN_BRACKET = TokenSet.create(NEON_LPAREN, NEON_LBRACE_CURLY, NEON_LBRACE_SQUARE);
	TokenSet CLOSING_BRACKET = TokenSet.create(NEON_RPAREN, NEON_RBRACE_CURLY, NEON_RBRACE_SQUARE);
	TokenSet SYMBOLS = TokenSet.create(
		NEON_COLON, NEON_ASSIGNMENT, NEON_ARRAY_BULLET, NEON_ITEM_DELIMITER,
		NEON_LPAREN, NEON_RPAREN, 
		NEON_LBRACE_CURLY, NEON_RBRACE_CURLY, 
		NEON_LBRACE_SQUARE, NEON_RBRACE_SQUARE
	);

	TokenSet OPEN_STRING_ALLOWED = TokenSet.create(
            NEON_COLON, NEON_ASSIGNMENT, NEON_ARRAY_BULLET,
            NEON_LPAREN, NEON_RPAREN,

            NEON_WHITESPACE, NEON_LITERAL, NEON_STRING, NEON_QUOTE,

			// Match brackets, as they would be inside the literal
			NEON_LPAREN, NEON_LBRACE_CURLY, NEON_LBRACE_SQUARE
    );

	// brackets
	public static final Map<IElementType, IElementType> closingBrackets = ImmutableMap.of(
		NEON_LPAREN, NEON_RPAREN,
		NEON_LBRACE_CURLY, NEON_RBRACE_CURLY,
		NEON_LBRACE_SQUARE, NEON_RBRACE_SQUARE
	);

}
