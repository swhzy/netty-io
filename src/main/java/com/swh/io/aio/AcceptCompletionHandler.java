package com.swh.io.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Copyright (C), 2018-2020
 * FileName: AcceptCompletionHandler
 * Author:   songweihui
 * Date:     2020/10/20 13:50
 * Description: io 操作完成后的回调
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AioServerHandler> {


    @Override
    public void completed(AsynchronousSocketChannel result, AioServerHandler attachment) {
        attachment.asynchronousServerSocketChannel.accept(attachment,this);
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        result.read(allocate,allocate,new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AioServerHandler attachment) {
        exc.printStackTrace();
        attachment.countDownLatch.countDown();
    }
}
