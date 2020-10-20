package com.swh.io.nio;

/**
 * Copyright (C), 2018-2020
 * FileName: NioServer
 * Author:   songweihui
 * Date:     2020/10/20 9:57
 * Description: nio server 启动
 */
public class NioServer {

    public static void main(String[] args) {
        NioServerChannelHandler nioServerChannelHandler = new NioServerChannelHandler(8082);

        new Thread(nioServerChannelHandler,"nio server").start();
    }
}
