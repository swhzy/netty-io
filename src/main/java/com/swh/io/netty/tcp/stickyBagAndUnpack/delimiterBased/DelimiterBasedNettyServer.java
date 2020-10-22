package com.swh.io.netty.tcp.stickyBagAndUnpack.delimiterBased;

import com.swh.io.netty.tcp.stickyBagAndUnpack.lineBased.LineBasedServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
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
public class DelimiterBasedNettyServer {

    public static void main(String[] args) {
        new DelimiterBasedNettyServer().bind(8084);
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
            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("$_".getBytes()))).addLast(new StringDecoder()).addLast(new StringEncoder()).addLast(new DelimiterBasedServerHandler());
            System.out.println("success to init handler");
        }
    }
}
