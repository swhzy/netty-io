package com.swh.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Copyright (C), 2018-2020
 * FileName: BioClientSocket
 * Author:   songweihui
 * Date:     2020/10/16 17:10
 * Description: bio 客户端socket
 */
public class BioClientSocket {

    private static int defaultPort = 8081;


    public static void main(String[] args) {

        Socket socket = null;
        BufferedReader  in = null;
        PrintWriter pw = null;
        try {
            socket = new Socket("127.0.0.1", defaultPort);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream());


            pw.println("xxxxxxxxxx\n");
            pw.flush();
            String readLine = in.readLine();
            System.out.println("this is client ,receive server data:"+readLine);



        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(pw!=null){
                pw.close();
            }
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
