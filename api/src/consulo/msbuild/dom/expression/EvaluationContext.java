package consulo.msbuild.dom.expression;

import gnu.trove.THashMap;

import java.util.Map;

/**
 * @author VISTALL
 * @since 02-Feb-17
 */
public class EvaluationContext
{
	private Map<String, String> myMacroes = new THashMap<>();
}
