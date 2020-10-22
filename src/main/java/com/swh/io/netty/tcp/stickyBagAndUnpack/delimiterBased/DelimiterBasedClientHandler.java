package com.swh.io.netty.tcp.stickyBagAndUnpack.delimiterBased;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Copyright (C), 2018-2020
 * FileName: NettyClientHandler
 * Author:   songweihui
 * Date:     2020/10/21 10:23
 * Description:
 */
public class DelimiterBasedClientHandler extends ChannelInboundHandlerAdapter {
    private int counter;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client receive msg");


        for (int i = 0; i < 300; i++) {
            String s = "this is client data " + i + "$_";
            ByteBuf buffer = Unpooled.buffer(s.length());
            buffer.writeBytes(s.getBytes());
            ctx.writeAndFlush(buffer);
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
