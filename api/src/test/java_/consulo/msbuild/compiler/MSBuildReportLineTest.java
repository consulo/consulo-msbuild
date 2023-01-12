package consulo.msbuild.compiler;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author VISTALL
 * @since 2018-09-17
 */
public class MSBuildReportLineTest extends Assert
{
	@Test
	public void testParsing()
	{
		String line = "utils\\ConcurrentListSet.cs(20,13): error CS0246: Some message [W:\\some\\server_backend\\server_backend.csproj]";

		MSBuildReportLine reportLine = MSBuildReportLine.parse(line);
		assertNotNull(reportLine);
		assertEquals("utils\\ConcurrentListSet.cs", reportLine.getFilePath());
		assertEquals(20, reportLine.getLine());
		assertEquals(13, reportLine.getColumn());
		assertEquals("CS0246", reportLine.getId());
		assertEquals("Some message", reportLine.getMessage());
		assertEquals("W:\\some\\server_backend\\server_backend.csproj", reportLine.getProjectPath());
	}
}
