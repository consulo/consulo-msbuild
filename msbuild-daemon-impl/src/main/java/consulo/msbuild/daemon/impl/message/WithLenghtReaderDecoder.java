package consulo.msbuild.daemon.impl.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author VISTALL
 * @since 13/12/2020
 */
public class WithLenghtReaderDecoder extends ByteToMessageDecoder
{
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
	{
		int bytes = in.readableBytes();

		int requiredSize = 1;
		if(bytes < requiredSize)
		{
			// no header
			return;
		}

		requiredSize += 4;

		if(bytes < requiredSize)
		{
			// no size header
			return;
		}

		in.readByte(); // header

		int bodySize = in.readIntLE();

		requiredSize += bodySize;

		in.resetReaderIndex();

		if(bytes < requiredSize)
		{
			// frame
			return;
		}

		out.add(in.readRetainedSlice(requiredSize));
	}
}
