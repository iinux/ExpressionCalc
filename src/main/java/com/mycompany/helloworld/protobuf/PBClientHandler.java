package com.mycompany.helloworld.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PBClientHandler extends SimpleChannelInboundHandler<DataInfo.Student> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DataInfo.Student student) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        DataInfo.Student student = DataInfo.Student.newBuilder().setName("johann").setAge(20).setAddress("beijing")
                .build();
        ctx.writeAndFlush(student);
    }
}
