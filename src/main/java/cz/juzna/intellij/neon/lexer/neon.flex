package cz.juzna.intellij.neon.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static cz.juzna.intellij.neon.lexer.NeonTokenTypes.*;
%%
%class _NeonLexer
%implements FlexLexer
%public
%unicode
%function advance
%type IElementType
%{
    private int yycolumn = 0;
    private int a = 0;
	private void retryInState(int newState) {
        yybegin(newState);
        yypushback(yylength());
	}
%}

STRING = \'[^\'\n]*\'|\"(\\.|[^\"\\\n])*\"
COMMENT = \#.*
YAML_HEADER = ---.*
YAML_TAG = %.*
INDENT = \n[\t ]*
LITERAL_START = [^#\"\',=\[\]{}()\x00-\x20!`]
WHITESPACE = [\t ]+

%state IN_LITERAL
%state IN_ASSIGNMENT
%state IN_ASSIGNMENT_LITERAL
%%

<YYINITIAL> {

    {LITERAL_START}+ "=" {
          a=2;retryInState(IN_ASSIGNMENT);
    }

    {STRING} {
        return NEON_STRING;
    }

    ^{YAML_TAG} {
        return NEON_TAG;
    }

    {YAML_HEADER} {
        return NEON_HEADER;
    }

    "-" / [ \t\n] { return NEON_ARRAY_BULLET; }
    "-" $ { return NEON_ARRAY_BULLET; }
    ":" / [ \t\n,\]})] { return NEON_COLON; }
    ":" $ { return NEON_COLON; }
    "," { return NEON_ITEM_DELIMITER; }

    "(" { return NEON_LPAREN; }
    ")" { return NEON_RPAREN; }
    "{" { return NEON_LBRACE_CURLY; }
    "}" { return NEON_RBRACE_CURLY; }
    "{{" { return NEON_LBRACE_JINJA; }
    "}}" { return NEON_RBRACE_JINJA; }
    "[" { return NEON_LBRACE_SQUARE; }
    "]" { return NEON_RBRACE_SQUARE; }

    {COMMENT} {
        return NEON_COMMENT;
    }

    {INDENT} {
        return NEON_INDENT;
    }

    {LITERAL_START} {
        yybegin(IN_LITERAL);
        return NEON_LITERAL;
    }

    {WHITESPACE} {
        return NEON_WHITESPACE;
    }

    . {
        return NEON_UNKNOWN;
    }
}

<IN_LITERAL> {
    [^,:\]})(\x00-\x20]+ {}
    [ \t]+[^#,:\]})(\x00-\x20] {}
    ":"[ \t\n,\]})] { retryInState(YYINITIAL); }
    ":"$ { retryInState(YYINITIAL); }
    ":" {}
    .|\n { retryInState(YYINITIAL); }
}

<IN_ASSIGNMENT_LITERAL> {
    {WHITESPACE}    { a=6;retryInState(IN_ASSIGNMENT); }
    "="             { a=1;retryInState(IN_ASSIGNMENT); }
    [^,\]= )(\x00-\x20]+ { a=4; }
    [ \t]+[^#,:\]= )(\x00-\x20] { a=5; retryInState(IN_ASSIGNMENT);}
    .|\n            { a=4;retryInState(IN_ASSIGNMENT); }
}

<IN_ASSIGNMENT> {
    {STRING} {   return NEON_STRING; }
    "=" { return NEON_ASSIGNMENT; }

    {LITERAL_START} {
        a=3;retryInState(IN_ASSIGNMENT_LITERAL);
        return NEON_LITERAL;
    }

    .|\n { retryInState(YYINITIAL); }
}
