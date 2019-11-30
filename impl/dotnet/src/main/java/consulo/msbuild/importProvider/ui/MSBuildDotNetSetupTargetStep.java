/*
 * Copyright 2013-2017 consulo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package consulo.msbuild.importProvider.ui;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.TitledSeparator;
import com.intellij.ui.table.TableView;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.ListTableModel;
import com.intellij.util.ui.table.ComboBoxTableCellEditor;
import consulo.desktop.util.awt.component.VerticalLayoutPanel;
import consulo.msbuild.importProvider.MSBuildModuleImportContext;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportProject;
import consulo.msbuild.importProvider.item.MSBuildDotNetImportTarget;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.wizard.WizardStep;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author VISTALL
 * @since 30-Jan-17
 */
public class MSBuildDotNetSetupTargetStep implements WizardStep<MSBuildModuleImportContext>
{
	private VerticalLayoutPanel myPanel;

	public MSBuildDotNetSetupTargetStep(MSBuildModuleImportContext context)
	{
		ComboBox<MSBuildDotNetImportTarget> targetComboBox = new ComboBox<>(MSBuildDotNetImportTarget.EP_NAME.getExtensions());
		targetComboBox.setRenderer(new ColoredListCellRenderer<MSBuildDotNetImportTarget>()
		{
			@Override
			protected void customizeCellRenderer(@Nonnull JList<? extends MSBuildDotNetImportTarget> jList, MSBuildDotNetImportTarget target, int i, boolean b, boolean b1)
			{
				append(target.getPresentableName());
			}
		});

		ColumnInfo<MSBuildDotNetImportProject, String> nameColumn = new ColumnInfo<MSBuildDotNetImportProject, String>("Name")
		{
			@Nullable
			@Override
			public Comparator<MSBuildDotNetImportProject> getComparator()
			{
				return (o1, o2) -> o1.getProjectInfo().Name.compareToIgnoreCase(o2.getProjectInfo().Name);
			}

			@Nullable
			@Override
			public String valueOf(MSBuildDotNetImportProject tableItem)
			{
				return tableItem.getProjectInfo().Name;
			}
		};

		ColumnInfo<MSBuildDotNetImportProject, MSBuildDotNetImportTarget> targetColumn = new ColumnInfo<MSBuildDotNetImportProject, MSBuildDotNetImportTarget>("Framework")
		{
			@Nullable
			@Override
			public MSBuildDotNetImportTarget valueOf(MSBuildDotNetImportProject tableItem)
			{
				return tableItem.getTarget();
			}

			@Override
			public boolean isCellEditable(MSBuildDotNetImportProject tableItem)
			{
				return true;
			}

			@Override
			public void setValue(MSBuildDotNetImportProject tableItem, MSBuildDotNetImportTarget value)
			{
				tableItem.setTarget(value);
			}

			@Override
			public int getWidth(JTable table)
			{
				return 100;
			}

			@Nullable
			@Override
			public TableCellEditor getEditor(MSBuildDotNetImportProject o)
			{
				return ComboBoxTableCellEditor.INSTANCE;
			}

			@Override
			public Class getColumnClass()
			{
				return MSBuildDotNetImportTarget.class;
			}
		};

		List<MSBuildDotNetImportProject> projectList = context.getMappedProjects().stream().map(it -> it instanceof MSBuildDotNetImportProject ? (MSBuildDotNetImportProject) it : null).filter(Objects
				::nonNull).collect(Collectors.toList());

		ListTableModel<MSBuildDotNetImportProject> tableModel = new ListTableModel<>(new ColumnInfo[]{
				nameColumn,
				targetColumn
		}, projectList);

		targetComboBox.addItemListener(e ->
		{
			if(e.getStateChange() == ItemEvent.SELECTED)
			{
				for(MSBuildDotNetImportProject project : projectList)
				{
					project.setTarget((MSBuildDotNetImportTarget) e.getItem());
					tableModel.fireTableDataChanged();
				}
			}
		});

		TableView<MSBuildDotNetImportProject> tableItemTableView = new TableView<>(tableModel);
		tableItemTableView.setEnabled(false);

		myPanel = JBUI.Panels.verticalPanel();
		myPanel.addComponent(new TitledSeparator(".NET Projects Setting:"));
		myPanel.addComponent(LabeledComponent.create(targetComboBox, "Default .NET framework"));
		myPanel.addComponent(ScrollPaneFactory.createScrollPane(tableItemTableView));
	}

	@RequiredUIAccess
	@Nonnull
	@Override
	public Component getSwingComponent()
	{
		return myPanel;
	}

	@RequiredUIAccess
	@Nonnull
	@Override
	public consulo.ui.Component getComponent()
	{
		throw new UnsupportedOperationException("desktop only");
	}
}