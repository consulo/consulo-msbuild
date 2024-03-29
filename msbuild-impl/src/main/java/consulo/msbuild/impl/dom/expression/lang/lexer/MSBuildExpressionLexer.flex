package consulo.msbuild.impl.dom.expression.lang.lexer;

import consulo.language.lexer.LexerBase;
import consulo.language.ast.IElementType;
import consulo.msbuild.impl.dom.expression.lang.psi.MSBuildExpressionTokens;

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
   "$("         { yybegin(MACRO); return MSBuildExpressionTokens.MACRO_OPEN; }

   "'"          { return MSBuildExpressionTokens.SINGLE_QUOTE; }

   "=="         { return MSBuildExpressionTokens.EQEQ; }

   "("          { return MSBuildExpressionTokens.LPAR; }

   ")"          { return MSBuildExpressionTokens.RPAR; }

   "\\"         { return MSBuildExpressionTokens.PATH_SEPARATOR; }

   [^]          { return MSBuildExpressionTokens.TEXT; }
}

<MACRO>
{
   ")"          { yybegin(YYINITIAL); return MSBuildExpressionTokens.MACRO_STOP; }

   {IDENTIFIER} { return MSBuildExpressionTokens.MACRO_NAME; }

   [^]          { return MSBuildExpressionTokens.BAD_CHARACTER; }
}