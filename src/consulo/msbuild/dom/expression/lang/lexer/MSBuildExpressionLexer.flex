package consulo.msbuild.dom.expression.lang.lexer;

import com.intellij.lexer.LexerBase;
import com.intellij.psi.tree.IElementType;
import consulo.msbuild.dom.expression.lang.psi.MSBuildExpressionTokens;

%%

%public
%class MSBuildExpressionLexer
%extends LexerBase
%unicode
%function advanceImpl
%type IElementType
%eof{  return;
%eof}

IDENTIFIER=[:jletter:] [:jletterdigit:]*

%state MACRO
%%

<YYINITIAL>
{
   "$("         { yybegin(MACRO); return MSBuildExpressionTokens.MACRO_START; }

   "'"          { return MSBuildExpressionTokens.SINGLE_QUOTE; }

   "\\"         { return MSBuildExpressionTokens.PATH_SEPARATOR; }
   
   [^]          { return MSBuildExpressionTokens.TEXT; }
}

<MACRO>
{
   ")"          { yybegin(YYINITIAL); return MSBuildExpressionTokens.MACRO_STOP; }

   {IDENTIFIER} { return MSBuildExpressionTokens.MACRO_NAME; }

   [^]          { return MSBuildExpressionTokens.BAD_CHARACTER; }
}