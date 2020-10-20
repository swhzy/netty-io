package com.swh.io.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Copyright (C), 2018-2020
 * FileName: NioServerChennalHandler
 * Author:   songweihui
 * Date:     2020/10/20 9:06
 * Description: 用于处理一个客户端请求
 */
public class NioServerChannelHandler implements Runnable{

    private volatile boolean stop = false;

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    public NioServerChannelHandler(int port) {
        try {
            // 开启selector(选择器)
            selector = Selector.open();
            // 开启服务器 socket 通道
            serverSocketChannel = ServerSocketChannel.open();
            // 绑定端口号
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            serverSocketChannel.configureBlocking(false);
            // 把 selector 选择器 注册到
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server socket is start in port :"+port);
        } catch (IOException e) {
            e.printStackTrace();
            //初始化失败 退出
            System.exit(1);
        }
    }

    public void stop(){
        // 用于关闭nio server socket
        stop = true;
    }

    @Override
    public void run() {
        while (!stop){

            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                while (iterator.hasNext()){

                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();

                    try {
                        handleInput(selectionKey);
                    } catch (Exception e) {
                        if(selectionKey!=null){
                            selectionKey.cancel();
                            if(selectionKey.channel()!=null){
                                selectionKey.channel().close();
                            }
                        }
                    }


                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void handleInput(SelectionKey selectionKey) throws IOException {
        if(selectionKey.isValid()){

            if(selectionKey.isAcceptable()){

                ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();

                SocketChannel accept = channel.accept();
                accept.configureBlocking(false);
                accept.register(selector,SelectionKey.OP_READ);

            }
            if(selectionKey.isReadable()){

                SocketChannel channel = (SocketChannel) selectionKey.channel();

                ByteBuffer readBuffer = ByteBuffer.allocate(1024);

                int read = channel.read(readBuffer);
                if(read>0){
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String string = new String(bytes,"UTF-8");
                    System.out.println("server socket receive data:"+string);

                    String msg = "this is server";

                    doWrite(channel,msg);

                }

            }

        }

    }

    private void doWrite(SocketChannel channel, String msg) throws IOException {

        if(msg!=null){

            byte[] bytes = msg.getBytes();

            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            channel.write(byteBuffer);

        }
    }


}
