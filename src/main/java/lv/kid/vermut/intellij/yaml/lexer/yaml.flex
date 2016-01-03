package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import java.util.Stack;

import static lv.kid.vermut.intellij.yaml.lexer.YamlTokenTypes.*;
%%
%class YamlFlexLexer
%implements FlexLexer
%public
// %debug
%unicode
%column
%function advance
%type IElementType
%{
    private int yycolumn = 0;
    private int a = 0;

	private void retryInState(int newState) {
        yybegin(newState);
        yypushback(yylength());
	}

	private void retryInInitial() {
        retryInState(YYINITIAL);
	}

    // The current indentation level.
    private int indent = -1;

    // The number of unclosed '{' and '['. `flow_level == 0` means block
    // context.
    private int flowLevel = 0;

    // Past indentation levels.
    private Stack<Integer> indents = new Stack<Integer>();

    private boolean addIndent(int column) {
        if (this.indent < column) {
            this.indents.push(this.indent);
            this.indent = column;
            return true;
        }
        return false;
    }

    private void unwindIndent(int col) {
        // In the flow context, indentation is ignored. We make the scanner less
        // restrictive then specification requires.
        if (this.flowLevel != 0) {
            return;
        }

        while (this.indent > col) {
            this.indent = this.indents.pop();
        }
    }
%}

COMMENT = \#.* {NEWLINE}
YAML_DOCUMENT = --- | \.\.\.
DIRECTIVE = %.*{NEWLINE}
INDENT = \n? [\t ]*
PLAIN_FIRST = [^-?:,\[\]{}#&*!|>\'\"%@`]
WHITESPACE = [\t ]+
NON_WHITESPACE = [^\t\r\n ]+
NEWLINE = \r\n|[\r\n\u2028\u2029\u000B\u000C\u0085]

BLOCK_INDENTATION_INDICATOR = [:digit:]?[+-]?

LINEBR_S = [\n\u0085\u2028\u2029]
FULL_LINEBR_S = \r | {LINEBR_S}
NULL_OR_LINEBR_S = \0 | {FULL_LINEBR_S}
NULL_BL_LINEBR_S = [ ] | {NULL_OR_LINEBR_S}
NULL_BL_T_LINEBR_S = \t | {NULL_BL_LINEBR_S}
NULL_BL_T_S = [\0 \t]

%xstate IN_SINGLE_QUOTE_SCALAR
%xstate IN_DOUBLE_QUOTE_SCALAR
%xstate IN_PLAIN_SCALAR
%xstate IN_BLOCK_SCALAR
%%

<YYINITIAL> {
     // Eat whitespaces and comments until we reach the next token.
    {WHITESPACE}    { a=100; return YAML_Whitespace; }
    {COMMENT}       { return YAML_Comment;    }

     // unwindIndent
    {INDENT}        { a=101; unwindIndent(yycolumn); return YAML_Whitespace; }

    // Is it a directive?
    ^{DIRECTIVE}    { unwindIndent(-1); return YAML_Directive;  }

    // Is it the document start?
    {YAML_DOCUMENT} { unwindIndent(-1); return YAML_DocumentStart;    }

    // Is it the block entry indicator?
    "-" {WHITESPACE}+             { addIndent(yycolumn); return YAML_BlockEntry;  }

    // Is it the flow sequence start indicator?
    "[" {WHITESPACE}*             { this.flowLevel++; return YAML_FlowSequenceStart; }
    // Is it the flow sequence end indicator?
    "]" {WHITESPACE}*             { this.flowLevel--; return YAML_FlowSequenceEnd; }

    // Is it the flow mapping start indicator?
    "{" {WHITESPACE}*             { this.flowLevel++; return YAML_FlowMappingStart; }
    "}" {WHITESPACE}*             { this.flowLevel--; return YAML_FlowMappingEnd; }

    // Is it the flow entry indicator?
    "," {WHITESPACE}*             { return YAML_FlowEntry; }

    // Is it the key indicator?
    "?" {NULL_BL_T_LINEBR_S}*     {
                                    // KEY(flow context): '?'
                                    if (this.flowLevel != 0) {
                                        return YAML_Key;
                                    } else {
                                        // KEY(block context): '?' (' '|'\n')
                                        if (yylength()>1) {
                                            yypushback(yylength() - 1); // Keep whitespace
                                            return YAML_Key;
                                        }
                                    }
                                  }

    // Is it the value indicator?
    ":" {NULL_BL_T_LINEBR_S}*     {
                                    // VALUE(flow context): ':'
                                    if (this.flowLevel != 0) {
                                        return YAML_Value;
                                    } else {
                                        // VALUE(block context): '?' (' '|'\n')
                                        if (yylength()>1) {
                                            yypushback(yylength() - 1); // Keep whitespace
                                            return YAML_Value;
                                        }
                                    }
                                  }
    // Is it an alias?
    "*" {NON_WHITESPACE}+           { return YAML_Alias; }

    // Is it an anchor?
    "&" {NON_WHITESPACE}+           { return YAML_Anchor; }

    // Is it an tag?
    "!!" {NON_WHITESPACE}+          { return YAML_Tag; }

    // TODO maybe it possible to parse out comment
    // Is it a literal scalar?
    "|" {BLOCK_INDENTATION_INDICATOR} {WHITESPACE}* {COMMENT}? {NEWLINE}
        { if (this.flowLevel == 0)
          {
            // Hacky hold the current indent to compare inside IN_BLOCK
            this.indents.push(yycolumn - yylength());
            yybegin(IN_BLOCK_SCALAR);
            return YAML_Scalar;
          }
        }

    // Is it a folded scalar?
    ">" {BLOCK_INDENTATION_INDICATOR} {WHITESPACE}* {COMMENT}? {NEWLINE}
        { if (this.flowLevel == 0)
          {
            // Hacky hold the current indent to compare inside IN_BLOCK
            this.indents.push(yycolumn - yylength());
            yybegin(IN_BLOCK_SCALAR);
            return YAML_Scalar;
          }
        }

    // Is it a single quoted scalar?
    "'"                            { yybegin(IN_SINGLE_QUOTE_SCALAR); }

    // Is it a double quoted scalar?
    "\""                           { yybegin(IN_DOUBLE_QUOTE_SCALAR); }

    {PLAIN_FIRST}                  { a=101; yybegin(IN_PLAIN_SCALAR);    }

    // However, the “:”, “?” and “-” indicators may be used as the first character if followed by a non-space “safe” character, as this causes no ambiguity.
    [?:-] [^ ]                     { a=102; yybegin(IN_PLAIN_SCALAR);    }

    .                              {         return YAML_Error;     }
}

// http://www.yaml.org/spec/1.2/spec.html#id2788859
<IN_PLAIN_SCALAR> {
    ":" {NULL_BL_T_LINEBR_S}           { a=306; retryInInitial(); return YAML_Scalar; }
    {NULL_BL_T_LINEBR_S} "#"           { a=308; retryInInitial(); return YAML_Scalar; }

    [\[\]{},]
        {
            if (this.flowLevel > 0) {
              retryInInitial();
              return YAML_Scalar;
            }
        }

    ^{WHITESPACE}* {NEWLINE}   { a=301; }

    ^{WHITESPACE}*
        { a=302;
            if (yylength() < this.indent || yylength() == 0)
            { // End of multiline scalar
                retryInInitial(); return YAML_Scalar;
            }
        }

    <<EOF>>                    { a=307; retryInInitial(); return YAML_Scalar; }
    . | {NEWLINE}
        { a=303;
            if (yycolumn == 0)
            { // End of multiline scalar
                retryInInitial(); return YAML_Scalar;
            }
        }
}

<IN_SINGLE_QUOTE_SCALAR> {
    "''"                         {}
    "'"                          { yybegin(YYINITIAL); return YAML_Scalar; }
    . | {NEWLINE}                {}
}
<IN_DOUBLE_QUOTE_SCALAR> {
    \\\"                         {}
    \"                           { yybegin(YYINITIAL); return YAML_Scalar; }
    . | {NEWLINE}                {}
}

<IN_BLOCK_SCALAR> {
    ^{WHITESPACE}* {NEWLINE}   { a=401; }
    ^{WHITESPACE}*
        { a=402;
            if (yylength() < this.indents.peek() || yylength() == 0)
            { // End of block scalar
                a=403; retryInInitial(); this.indents.pop(); return YAML_Scalar;
            }
        }
    <<EOF>>                    { a=407; retryInInitial(); this.indents.pop(); return YAML_Scalar; }
    . | {NEWLINE}              { }
}