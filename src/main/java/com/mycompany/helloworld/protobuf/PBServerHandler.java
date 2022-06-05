package com.mycompany.helloworld.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PBServerHandler extends SimpleChannelInboundHandler<DataInfo.Student> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DataInfo.Student student) throws Exception {
        System.out.println(student.getName());
        System.out.println(student.getAge());
        System.out.println(student.getAddress());
    }
}
