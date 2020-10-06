package com.husd.framework.thread;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

public class SomeAQSDebugTest {

    @Test
    public void test() throws InterruptedException {

        ReentrantLock lock = new ReentrantLock(true);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Thread.sleep(10000);
                    lock.lock();
                    Thread.sleep(10000);
                    System.out.println("t1 get lock");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        t1.setName("t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Thread.sleep(20000);
                    lock.lock();
                    Thread.sleep(10000);
                    System.out.println("t2 get lock");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        t2.setName("t2");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Thread.sleep(30000);
                    lock.lock();
                    Thread.sleep(10000);
                    System.out.println("t3 get lock");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        t3.setName("t3");

        t1.start();
        t2.start();
        //t3.start();

        t1.join();
        t2.join();
       // t3.join();
        System.out.println("the end");

    }
}
