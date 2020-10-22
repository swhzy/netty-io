package com.swh.io.netty.tcp.stickyBagAndUnpack.fixedLength;

import com.swh.io.netty.tcp.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Copyright (C), 2018-2020
 * FileName: NettyClient
 * Author:   songweihui
 * Date:     2020/10/21 10:17
 * Description: netty客户端
 */
public class FixedLengthNettyClient implements Runnable {
    public static void main(String[] args) {
        new FixedLengthNettyClient().run();
    }


    private void connect(String host, int port) {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(10)).addLast(new StringDecoder()).addLast(new StringEncoder()).addLast(new NettyClientHandler());
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);


            ChannelFuture sync = bootstrap.connect(host, port).sync();
            sync.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        for (int i = 0; i < 1; i++) {
            this.connect("127.0.0.1", 8084);
        }
    }
}
