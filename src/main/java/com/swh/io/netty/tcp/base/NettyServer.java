package com.swh.io.netty.tcp.base;

import com.swh.io.netty.tcp.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Copyright (C), 2018-2020
 * FileName: NettyServer
 * Author:   songweihui
 * Date:     2020/10/20 16:39
 * Description: netty server
 */
public class NettyServer {

    public static void main(String[] args) {
        new NettyServer().bind(8084);
    }

    private void bind(int port) {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)

                    .childHandler(new ChildChannelHandler());
            serverBootstrap.option(ChannelOption.SO_BACKLOG,10);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);
            serverBootstrap.childOption(ChannelOption.TCP_NODELAY,true);

            ChannelFuture sync = serverBootstrap.bind(port).sync();

            // 等待服务器监听端口关闭
            sync.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline()/*.addLast(new FixedLengthFrameDecoder(20))*/.addLast(new StringDecoder()).addLast(new StringEncoder()).addLast(new ServerHandler());
            System.out.println("success to init handler");
        }
    }
}
