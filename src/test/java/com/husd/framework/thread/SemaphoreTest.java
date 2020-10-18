package com.husd.framework.thread;

import org.junit.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class SemaphoreTest {

    //信号量，作用是控制线程的并发数量。

    @Test
    public void test() throws InterruptedException {

        //控制同时只能有4个线程执行
        Semaphore semaphore = new Semaphore(4);

        AtomicInteger count = new AtomicInteger(0);

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        count.getAndIncrement();
                        System.out.println("线程 " + Thread.currentThread().getName() + " 获取了执行权限，当前一共有" + count + "个线程正在执行");
                        //模拟下阻塞，要不线程执行太快结束了
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        count.getAndDecrement();
                        semaphore.release();
                    }
                }
            });
            t.setName("[" + i + "]");
            t.start();
        }

    }
}
