package com.swh.io.bio;

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

    }
}
