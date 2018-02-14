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

package consulo.msbuild.projectView.toolWindow;

import javax.annotation.Nonnull;
import javax.swing.JComponent;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import consulo.msbuild.projectView.SolutionViewPane;

/**
 * @author VISTALL
 * @since 03-Feb-17
 */
public class SolutionToolWindowFactory implements ToolWindowFactory
{
	@Override
	public void createToolWindowContent(@Nonnull Project project, @Nonnull ToolWindow toolWindow)
	{
		ContentManager contentManager = toolWindow.getContentManager();

		SolutionViewPane viewPane = SolutionViewPane.getInstance(project);

		JComponent component = viewPane.getComponent();
		component.setBorder(null);

		contentManager.addContent(ContentFactory.getInstance().createContent(component, null, true));
	}
}
