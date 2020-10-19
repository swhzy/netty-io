package com.swh.io.bio.traditional;

import com.swh.io.bio.BioServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Copyright (C), 2018-2020
 * FileName: BioServerSocket
 * Author:   songweihui
 * Date:     2020/10/16 16:56
 * Description: bio 服务端   客户端线程请求数和服务器线程处理数 1:1
 */
public class BioServerSocket {

    private static Integer defaultPort = 8081;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            if(args.length>0&&args[0]!=null){
                try {
                    defaultPort = Integer.valueOf(args[0]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    // 走默认端口
                }

            }
            serverSocket = new ServerSocket(defaultPort);
            System.out.println("the bio server socket is start,port is "+defaultPort);
            while (true){
                Socket socket = serverSocket.accept();
                new Thread(new BioServerHandler(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
