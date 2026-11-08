/*
 * Copyright 2013-2026 consulo.io
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
package consulo.msbuild.dotnet.impl.library;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author VISTALL
 */
public class NuGetFrameworksTest {
    // the lib folders of Newtonsoft.Json 13.0.3
    private static final List<String> NEWTONSOFT = List.of("net20", "net35", "net40", "net45", "net6.0", "netstandard1.0", "netstandard1.3", "netstandard2.0");

    @Test
    public void testSelect() {
        Assert.assertEquals("net6.0", NuGetFrameworks.select("net10.0", NEWTONSOFT));
        Assert.assertEquals("net6.0", NuGetFrameworks.select("net6.0-windows", NEWTONSOFT));
        Assert.assertEquals("netstandard2.0", NuGetFrameworks.select("netcoreapp3.1", NEWTONSOFT));
        Assert.assertEquals("netstandard2.0", NuGetFrameworks.select("netstandard2.1", NEWTONSOFT));
        Assert.assertEquals("netstandard1.3", NuGetFrameworks.select("netstandard1.6", NEWTONSOFT));
        Assert.assertEquals("net45", NuGetFrameworks.select("net472", NEWTONSOFT));
        Assert.assertEquals("net40", NuGetFrameworks.select("net40", NEWTONSOFT));
        // .NET Framework 4.6.1+ uses .NET Standard 2.0 when there is no .NET Framework folder
        Assert.assertEquals("netstandard2.0", NuGetFrameworks.select("net472", List.of("netstandard2.0", "net8.0")));
        Assert.assertNull(NuGetFrameworks.select("net45", List.of("netstandard2.0")));
        Assert.assertNull(NuGetFrameworks.select("net6.0", List.of("net8.0")));
        Assert.assertNull(NuGetFrameworks.select("unknown", NEWTONSOFT));
    }

    @Test
    public void testVersions() {
        Assert.assertTrue(NuGetFrameworks.compareVersions("13.0.3", "13.0.1") > 0);
        Assert.assertTrue(NuGetFrameworks.compareVersions("10.0.0", "9.0.10") > 0);
        Assert.assertTrue(NuGetFrameworks.compareVersions("2.0.0", "2.0.0-preview.1") > 0);
        Assert.assertTrue(NuGetFrameworks.compareVersions("2.0.0-rc.1", "2.0.0-preview.1") > 0);
        Assert.assertEquals(0, NuGetFrameworks.compareVersions("1.0", "1.0.0"));
    }
}
