package consulo.msbuild.daemon.impl.message.model;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public enum MSBuildEvent implements EnumInt
{
	None(0),
	BuildStarted(1 << 0),
	BuildFinished(1 << 1),
	ProjectStarted(1 << 2),
	ProjectFinished(1 << 3),
	TargetStarted(1 << 4),
	TargetFinished(1 << 5),
	TaskStarted(1 << 6),
	TaskFinished(1 << 7),
	ErrorRaised(1 << 8),
	WarningRaised(1 << 9),
	MessageRaised(1 << 10),
	CustomEventRaised(1 << 11),
	All(0xffff);

	private int myMask;

	MSBuildEvent(int mask)
	{
		myMask = mask;
	}

	@Override
	public int getValue()
	{
		return myMask;
	}
}
