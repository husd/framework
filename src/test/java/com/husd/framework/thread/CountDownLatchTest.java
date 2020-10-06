package com.husd.framework.thread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

//CountDownLatch  有2种经典的用法
public class CountDownLatchTest {

    @Test
    public void test1() throws InterruptedException {

        // 多个线程协作，然后执行完任务之后，再继续执行下一个任务

        CountDownLatch countDownLatch = new CountDownLatch(2);

        // t1 t2协作执行任务，都执行完了之后，再继续向下走
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("t1 is working");
                    System.out.println("t1 is working");
                    System.out.println("t1 is working");
                    System.out.println("t1 end");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("t2 is working");
                    System.out.println("t2 is working");
                    System.out.println("t2 is working");
                    System.out.println("t2 end");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }
        });

        t1.start();
        t2.start();

        while (countDownLatch.getCount() != 0) {
            System.out.println("main is waiting");
            Thread.sleep(30);
        }

        System.out.println("t1 t2 end ");
    }

    @Test
    public void test2() throws InterruptedException {

        // 多个线程协作，然后执行完任务之后，再继续执行下一个任务
        // 这里主要用到了await()，CountDownLatch 可以唤醒多个线程
        /*创建CountDownLatch实例,计数器的值初始化为3*/
        final CountDownLatch countDownLatch = new CountDownLatch(3);
        /*创建三个线程,每个线程等待1s,表示执行比较耗时的任务*/
        for (int i = 0; i < 3; i++) {
            final int n = i;
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        countDownLatch.countDown();
                    }
                    System.out.println(String.format("thread %d has finished", n));
                }
            }).start();
        }
        /*主线程调用await()方法,等到其他三个线程执行完后才继续执行 这种方式会阻塞主线程 相比于第一种方法
        * 第一种方法可能会更自由，效率也许会更好
        *
        * */
        countDownLatch.await();
        System.out.println("all threads have finished,main thread will continue run");
    }
}
