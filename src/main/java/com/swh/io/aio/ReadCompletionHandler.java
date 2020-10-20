package com.swh.io.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Copyright (C), 2018-2020
 * FileName: ReadCompletionHandler
 * Author:   songweihui
 * Date:     2020/10/20 14:07
 * Description:
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;
    public ReadCompletionHandler(AsynchronousSocketChannel result) {
        if(this.channel==null){
            channel = result;
        }

    }


    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();

        byte[] bytes = new byte[attachment.remaining()];
        attachment.get(bytes);

        try {
            String req = new String(bytes, "UTF-8");

            System.out.println("the server receive msg:"+req);

            String res = "this is server ,hello client";

            doWrite(res);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    private void doWrite(String res) {

        if(res!=null&&res.trim().length()>0){
            byte[] bytes = res.getBytes();
            final ByteBuffer wb = ByteBuffer.allocate(bytes.length);
            wb.put(bytes);
            wb.flip();

            channel.write(wb, wb, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    if(attachment.hasRemaining()){
                        channel.write(wb,wb,this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        exc.printStackTrace();
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
