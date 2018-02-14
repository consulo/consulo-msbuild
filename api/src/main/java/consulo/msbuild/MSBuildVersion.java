/*
 * Copyright 2013-2015 must-be.org
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

package consulo.msbuild;

import javax.annotation.Nonnull;

/**
 * @author VISTALL
 * @since 09.03.2015
 */
public enum MSBuildVersion
{
	Visual_Studio("4.0", "4.0"),
	Visual_Studio_97("5.0", "5.0"),
	Visual_Studio_6("6.0", "6.0"),

	Visual_Studio_2002("2002", "7.0"),
	Visual_Studio_2003("2003", "7.1"),
	Visual_Studio_2005("2005", "8.0"),
	Visual_Studio_2008("2008", "9.0"),
	Visual_Studio_2010("2010", "10.0"),
	Visual_Studio_2012("2012", "11.0"),
	Visual_Studio_2013("2013", "12.0"),
	Visual_Studio_2015("2015", "14.0"),
	Visual_Studio_2017("2017", "15.0");

	public static final String[] ourVisualStudioEditions = new String[]{
			"Community",
			"Professional"
	};

	private final String myYearVersion;
	private final String myInternalVersion;
	private final String myPresentableName;

	MSBuildVersion(@Nonnull String yearVersion, String internalVersion)
	{
		myYearVersion = yearVersion;
		myInternalVersion = internalVersion;
		myPresentableName = name().replace("_", " ");
	}

	@Nonnull
	public String getYearVersion()
	{
		return myYearVersion;
	}

	@Nonnull
	public String getPresentableName()
	{
		return myPresentableName;
	}

	public String getInternalVersion()
	{
		return myInternalVersion;
	}
}
