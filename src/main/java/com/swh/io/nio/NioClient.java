package com.swh.io.nio;

/**
 * Copyright (C), 2018-2020
 * FileName: NioClient
 * Author:   songweihui
 * Date:     2020/10/20 10:13
 * Description: nio client
 */
public class NioClient {
    public static void main(String[] args) {
        new Thread(new NioClientHandler("127.0.0.1",8082),"nio client").start();
    }
}
