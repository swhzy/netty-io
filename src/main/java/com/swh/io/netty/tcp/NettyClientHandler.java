package com.swh.io.netty.tcp;

import io.netty.channel.*;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Copyright (C), 2018-2020
 * FileName: NettyClientHandler
 * Author:   songweihui
 * Date:     2020/10/21 10:23
 * Description:
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private int counter;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client receive msg");


        for (int i = 0; i < 300; i++) {
            ctx.writeAndFlush("this is client data "+i);
        }
    }




    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        String body = (String) msg;

        System.out.println("client read msg:"+ body);
    }
}
