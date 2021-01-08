package com.husd.framework.buffer;

import java.lang.management.ManagementFactory;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("this is a test");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = ManagementFactory.getRuntimeMXBean().getName();
                String pid = name.split("@")[0];
                long tid = Thread.currentThread().getId();
                String s = "t1 is running pid is:" + pid + " tid is:" + tid;
                while (true) {
                    System.out.println(s);
                    try {
                        Thread.sleep(1000 * 30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = ManagementFactory.getRuntimeMXBean().getName();
                String pid = name.split("@")[0];
                long tid = Thread.currentThread().getId();
                String s = "t2 is running pid is:" + pid + " tid is:" + tid;
                while (true) {
                    System.out.println(s);
                    try {
                        Thread.sleep(1000 * 30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();

        t.join();
        t1.join();
    }
}
