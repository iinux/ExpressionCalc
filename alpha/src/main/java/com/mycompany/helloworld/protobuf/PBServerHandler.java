package com.mycompany.helloworld.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PBServerHandler extends SimpleChannelInboundHandler<DataInfo.TypeAndData> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DataInfo.TypeAndData typeAndData) throws Exception {
        System.out.println(typeAndData.getDataType());
        System.out.println(typeAndData.getStudent().getName());
        System.out.println(typeAndData.getStudent().getAge());
        System.out.println(typeAndData.getStudent().getAddress());
    }
}
