package com.swh.io.aio;

/**
 * Copyright (C), 2018-2020
 * FileName: AioClient
 * Author:   songweihui
 * Date:     2020/10/20 15:16
 * Description: aio
 */
public class AioClient {
    public static void main(String[] args) {
        new Thread(new AioClientHandler("127.0.0.1",8083),"aio client").start();
    }
}
