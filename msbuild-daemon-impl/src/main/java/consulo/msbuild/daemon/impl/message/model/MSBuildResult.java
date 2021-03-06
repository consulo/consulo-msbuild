package consulo.msbuild.daemon.impl.message.model;

import consulo.msbuild.daemon.impl.message.TypedMap;

import java.util.Arrays;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public class MSBuildResult implements DataObject
{
	public MSBuildTargetResult[] errors;

	public TypedMap<String, String> properties = new TypedMap<>(String.class, String.class);

	public TypedMap<String, MSBuildEvaluatedItem[]> items = new TypedMap<>(String.class, MSBuildEvaluatedItem[].class);

	@Override
	public String toString()
	{
		return "MSBuildResult{" +
				"errors=" + (errors == null ? null : Arrays.asList(errors)) +
				", properties=" + properties +
				", items=" + items +
				'}';
	}
}
