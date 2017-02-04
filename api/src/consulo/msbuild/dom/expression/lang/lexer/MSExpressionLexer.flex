package consulo.msbuild.dom.expression.lang.lexer;

import java.util.*;
import com.intellij.lexer.LexerBase;
import com.intellij.psi.tree.IElementType;
import consulo.msbuild.dom.expression.lang.MSBuildExpressionTokens;

%%

%public
%class MSExpressionLexer
%extends LexerBase
%unicode
%function advanceImpl
%type IElementType
%eof{  return;
%eof}

%state MACRO

%%

<MACRO>
{
    ")"                         { yybegin(YYINITIAL); return MSBuildExpressionTokens.RPAR; }
    [^]                         { return MSBuildExpressionTokens.MACRO_CHAR; }
}

<YYINITIAL>
{
	"$("
	{
	   yybegin(MACRO);
	   return MSBuildExpressionTokens.MACRO_START;
	}

	"("                         { return MSBuildExpressionTokens.LPAR; }
	")"                         { return MSBuildExpressionTokens.RPAR; }
	[^]                         { return MSBuildExpressionTokens.CHAR; }
}
