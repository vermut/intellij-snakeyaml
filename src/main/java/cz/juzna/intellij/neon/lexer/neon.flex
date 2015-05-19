package cz.juzna.intellij.neon.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static cz.juzna.intellij.neon.lexer.NeonTokenTypes.*;
%%
%class _NeonLexer
%implements FlexLexer
%public
%debug
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
JINJA_START = "{{"
WHITESPACE = [\t ]+
Identifier = [:jletter:] [:jletterdigit:]*

%state IN_LITERAL
%state IN_ASSIGNMENT
%state IN_ASSIGNMENT_LITERAL
%%

<YYINITIAL> {

    {Identifier} "=" {
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
//    "{{" { return NEON_LBRACE_JINJA; }
//    "}}" { return NEON_RBRACE_JINJA; }
    "[" { return NEON_LBRACE_SQUARE; }
    "]" { return NEON_RBRACE_SQUARE; }

    {COMMENT} {
        return NEON_COMMENT;
    }

    {INDENT} {
        return NEON_INDENT;
    }

    {LITERAL_START} | {JINJA_START} {
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
    {WHITESPACE}+ {Identifier} {WHITESPACE}* "="    { a=501; retryInState(IN_ASSIGNMENT); }
    "="             { a=502; retryInState(IN_ASSIGNMENT); }
    \R              { a=503;retryInState(IN_ASSIGNMENT); }
    // Just to make next one faster
    [:jletter:]* [:jletterdigit:]*               { a=504; }
    .               { a=505; }
}

<IN_ASSIGNMENT> {
    {STRING}         {  a=401;return NEON_STRING; }
    "="              {  a=402;return NEON_ASSIGNMENT; }
    {WHITESPACE}     {  a=403;return NEON_WHITESPACE;    }
    // {Identifier}     {  a=404;return NEON_LITERAL;    }

    {LITERAL_START} | {JINJA_START} {
        a=405;retryInState(IN_ASSIGNMENT_LITERAL);
        return NEON_LITERAL;
    }

    .|\n { a=406;retryInState(YYINITIAL); }
}
