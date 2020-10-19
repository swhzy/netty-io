package com.swh.io.bio;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;
import java.net.Socket;

/**
 * Copyright (C), 2018-2020
 * FileName: BioServerHandler
 * Author:   songweihui
 * Date:     2020/10/16 17:04
 * Description: bio 服务器实际业务处理线程
 */
public class BioServerHandler implements Runnable{

    private Socket socket;

    public BioServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        if(socket==null){
            System.out.println("获取字节流异常");
        }
        BufferedReader in = null;
        PrintWriter pw = null;
        try {
            InputStream inputStream = socket.getInputStream();
//            byte[] bytes = new byte[1024];
//            while (inputStream.read(bytes)!=-1){
//                String s = new String(bytes);
//                System.out.println(s);
//            }

            in = new BufferedReader(new InputStreamReader(inputStream));

            pw = new PrintWriter(socket.getOutputStream());
            while (true){
                String readLine = in.readLine();
                if("".equals(readLine)){
                    break;
                }
                System.out.println("bio server handler receive data:"+readLine);
            }

            pw.println("接受到了,over\n");
            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(pw!=null){
                pw.close();
                pw = null;
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
