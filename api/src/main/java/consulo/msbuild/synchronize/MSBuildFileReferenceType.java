package consulo.msbuild.synchronize;

import java.util.List;

import javax.annotation.Nonnull;

import consulo.msbuild.dom.ItemGroup;
import consulo.msbuild.dom.SimpleItem;

/**
 * @author VISTALL
 * @since 2018-08-15
 */
public enum MSBuildFileReferenceType
{
	COMPILE
			{
				@Nonnull
				@Override
				public List<? extends SimpleItem> selectItems(ItemGroup itemGroup)
				{
					return itemGroup.getCompiles();
				}

				@Override
				public SimpleItem addItem(ItemGroup itemGroup)
				{
					return itemGroup.addCompile();
				}
			},

	NONE
			{
				@Nonnull
				@Override
				public List<? extends SimpleItem> selectItems(ItemGroup itemGroup)
				{
					return itemGroup.getNones();
				}

				@Override
				public SimpleItem addItem(ItemGroup itemGroup)
				{
					return itemGroup.addNone();
				}
			},

	CONTENT
			{
				@Nonnull
				@Override
				public List<? extends SimpleItem> selectItems(ItemGroup itemGroup)
				{
					return itemGroup.getContents();
				}

				@Override
				public SimpleItem addItem(ItemGroup itemGroup)
				{
					return itemGroup.addContent();
				}
			};

	@Nonnull
	public abstract List<? extends SimpleItem> selectItems(ItemGroup itemGroup);

	public abstract SimpleItem addItem(ItemGroup itemGroup);
}
