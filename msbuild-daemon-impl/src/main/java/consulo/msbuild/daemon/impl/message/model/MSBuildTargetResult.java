package consulo.msbuild.daemon.impl.message.model;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public class MSBuildTargetResult implements DataObject
{
	public String ProjectFile;

	public boolean IsWarning;

	public String Subcategory;

	public String Code;

	public String File;

	public int LineNumber;

	public int ColumnNumber;

	public int EndLineNumber;

	public int EndColumnNumber;

	public String Message;

	public String HelpKeyword;

	@Override
	public String toString()
	{
		return "MSBuildTargetResult{" +
				"ProjectFile='" + ProjectFile + '\'' +
				", IsWarning=" + IsWarning +
				", Subcategory='" + Subcategory + '\'' +
				", Code='" + Code + '\'' +
				", File='" + File + '\'' +
				", LineNumber=" + LineNumber +
				", ColumnNumber=" + ColumnNumber +
				", EndLineNumber=" + EndLineNumber +
				", EndColumnNumber=" + EndColumnNumber +
				", Message='" + Message + '\'' +
				", HelpKeyword='" + HelpKeyword + '\'' +
				'}';
	}
}
