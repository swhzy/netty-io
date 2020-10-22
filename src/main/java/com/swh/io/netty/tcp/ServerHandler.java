package com.swh.io.netty.tcp;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Copyright (C), 2018-2020
 * FileName: TimeServerHandler
 * Author:   songweihui
 * Date:     2020/10/20 16:47
 * Description:
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private int count;




    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        count++;
        String body = (String) msg;
        System.out.println("server read :【"+body+"】-"+ count);


    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String response = "hello from server";
        ctx.writeAndFlush(response);
    }
}
