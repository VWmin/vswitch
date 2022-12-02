package com.vwmin;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6653;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new OFChannelInitializer());

        ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
        if (future.isSuccess()) {
            log.info("OK");
        }
    }
}