package com.mycompany.helloworld.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpServer {
    public static void main(String[] args) throws Exception {
        HttpServer httpServer = new HttpServer(8888);
        httpServer.start();
    }

    private final int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        ServerBootstrap b = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        b.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(this.getChannel())
                .option(ChannelOption.SO_BACKLOG, 128) // determining the number of connections queued
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);

        b.bind(port).sync();
    }

    protected SSLChannelInitializer getSslChannel() {
        return new SSLChannelInitializer();
    }

    protected ChannelInitializer<SocketChannel> getChannel() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                System.out.println("initChannel ch:" + ch);
                ch.pipeline()
                        .addLast("decoder", new HttpRequestDecoder())
                        .addLast("encoder", new HttpResponseEncoder())
                        .addLast("aggregator", new HttpObjectAggregator(512 * 1024))
                        .addLast("handler", new HttpHandler());
            }
        };
    }
}
