package lv.kid.vermut.intellij.yaml.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static lv.kid.vermut.intellij.yaml.lexer.YamlTokenTypes.*;
%%
%class YamlFlexLexer
%implements FlexLexer
%public
%debug
%unicode
%function advance
%type IElementType
%{
    private int yycolumn = 0;
    private int a = 0;
    private Stack<Integer> stack = new Stack<Integer>();

	private void retryInState(int newState) {
        yybegin(newState);
        yypushback(yylength());
	}

    public void yypushState(int newState) {
      stack.push(yystate());
      yybegin(newState);
    }

    public void yypopState() {
      yybegin(stack.pop());
    }
%}

COMMENT = \#.* {NEWLINE}
YAML_DOCUMENT = --- | \.\.\.
DIRECTIVE = %.*{NEWLINE}
INDENT = \n? [\t ]*
LITERAL_START = [^-?:,\[\]{}#&*!|>\'\"%@`]
WHITESPACE = [\t ]+
NEWLINE = \r\n|[\r\n\u2028\u2029\u000B\u000C\u0085]

%xstate IN_PLAIN
%xstate IN_PLAIN_FLOW
%%

<YYINITIAL> {
     // Eat whitespaces and comments until we reach the next token.
    {WHITESPACE}    { a=100; return YAML_Whitespace; }
    {COMMENT}       { return YAML_Comment;    }

     // unwindIndent
    {INDENT}        { a=101; return YAML_Whitespace; }

    // Is it a directive?
    ^{DIRECTIVE}    { return YAML_Directive;  }

    // Is it the document start?
    {YAML_DOCUMENT} {         return YAML_DocumentStart;    }

    // Is it the block entry indicator?
    "-" {WHITESPACE}*            { return YAML_BlockEntry;               }

    // Is it the flow sequence start indicator?
    "[" {WHITESPACE}*            { return YAML_FlowSequenceStart; }
    // Is it the flow sequence end indicator?
    "]" {WHITESPACE}*             { return YAML_FlowSequenceEnd; }

    // Is it the flow mapping start indicator?
    "{" {WHITESPACE}*             { return YAML_FlowMappingStart; }
    "}" {WHITESPACE}*            { return YAML_FlowMappingEnd; }

    // Is it the flow entry indicator?
    "," {WHITESPACE}*             { return YAML_FlowEntry; }

    // Is it the key indicator?
    "?" {WHITESPACE}*            { return YAML_Key; }

    // Is it the value indicator?
    ":" {WHITESPACE}*            { return YAML_Value; }

    // Is it a literal scalar?
    "|" {WHITESPACE}* {NEWLINE} { return YAML_Scalar; }

    // Is it a folded scalar?
    ">" {WHITESPACE}* {NEWLINE} { return YAML_Scalar; }

    // Is it a single quoted scalar?
    "'"                            { return YAML_Scalar; }

    // Is it a double quoted scalar?
    "\""                           { return YAML_Scalar; }

    {LITERAL_START} {
        yybegin(IN_PLAIN_FLOW);
    }


    . {         return YAML_Error;     }
}

<IN_PLAIN> {
    {NEWLINE}                  { a=307; retryInState(YYINITIAL); return YAML_Scalar; }
    <<EOF>>                    { a=307; yybegin(YYINITIAL); return YAML_Scalar; }
    .                         {}
}
<IN_PLAIN_FLOW> {
    ":"                        { a=308; retryInState(YYINITIAL); return YAML_Scalar; }
    {NEWLINE}                  { a=307; retryInState(YYINITIAL); return YAML_Scalar; }
    <<EOF>>                    { a=307; yybegin(YYINITIAL); return YAML_Scalar; }
    .                         {}
}