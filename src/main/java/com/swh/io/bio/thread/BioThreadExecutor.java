package com.swh.io.bio.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (C), 2018-2020
 * FileName: BioThreadExcutorServerSocket
 * Author:   songweihui
 * Date:     2020/10/19 16:15
 * Description: 多线程版本的服务器端   客户端线程请求数和服务器线程处理数 M:N
 */
public class BioThreadExecutor {

    private ThreadPoolExecutor executor;

    public BioThreadExecutor(int corePoolSize,
                             int maximumPoolSize) {
        executor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,30, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(5));
    }

    public void submit(Runnable runnable){
        executor.submit(runnable);
    }
}
