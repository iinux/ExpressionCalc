package com.mycompany.helloworld.netty.httpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

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

        ChannelFuture cf = b.bind(port);
        cf.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                System.out.println("start ok port is " + port);
            }
        });
        cf.sync();
        cf.channel().closeFuture().sync();
    }

    protected SSLChannelInitializer getSslChannel() {
        return new SSLChannelInitializer();
    }

    protected ChannelInitializer<SocketChannel> getChannel() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                System.out.println("initChannel ch: " + ch + " class: " + ch.getClass().getName());
                ch.pipeline()
                        .addLast(new HttpServerCodec())
                        //.addLast("decoder", new HttpRequestDecoder())
                        //.addLast("encoder", new HttpResponseEncoder())
                        .addLast("aggregator", new HttpObjectAggregator(512 * 1024))
                        .addLast("handler", new HttpHandler());
            }
        };
    }
}
