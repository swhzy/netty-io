package com.swh.io.aio;

/**
 * Copyright (C), 2018-2020
 * FileName: AioServer
 * Author:   songweihui
 * Date:     2020/10/20 15:17
 * Description: aio server
 */
public class AioServer {

    public static void main(String[] args) {
        new Thread(new AioServerHandler(8083),"aio server 001").start();
    }
}
