package cn.iinux.java.alpha.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PBClientHandler extends SimpleChannelInboundHandler<DataInfo.TypeAndData> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DataInfo.TypeAndData typeAndData) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        DataInfo.TypeAndData typeAndData = DataInfo.TypeAndData.newBuilder()
                .setDataType(DataInfo.TypeAndData.DataType.StudentType)
                .setStudent(DataInfo.Student.newBuilder().setName("johann").setAge(20).setAddress("beijing"))
                .build();
        ctx.writeAndFlush(typeAndData);
    }
}
