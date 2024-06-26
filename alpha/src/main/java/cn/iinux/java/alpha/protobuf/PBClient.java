package cn.iinux.java.alpha.protobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/*
public class PBClient {
    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new PBClientChannelInitializer());
            ChannelFuture channel = bootstrap.connect("localhost", 8899).sync().channel().closeFuture()
                    .sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}


 */