package com.husd.framework.thread;

import org.junit.Test;

/**
 * 2个线程，1个线程打印奇数，1个线程打印偶数，
 * 使用wait() notify() 使最终的控制台打印
 * 1
 * 2
 * 3
 * 4
 * 5
 * 6
 * 这样的数字序列
 *
 * @author hushengdong
 * @date 2019/11/19
 */
public class ThreadTest {

    Object object = new Object();

    int num = 1;

    @Test
    public void test() throws InterruptedException {

        Thread thread1 = new Thread() {

            @Override
            public void run() {

                synchronized (object) {
                    while (num <= 20) {
                        if (num % 2 == 1) {
                            System.out.println("thread1:" + num);
                            num = num + 1;
                            object.notify();
                        } else {
                            //System.out.println("thread 1 wait......");
                            try {
                                // wait方法会立即释放当前拥有的锁，他下次运行，必须要其它线程调用object.notify()方法
                                //重新唤醒自己，但是即使唤醒了自己，也要重新去竞争锁。
                                object.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };

        Thread thread2 = new Thread() {

            @Override
            public void run() {

                synchronized (object) {
                    while (num <= 20) {
                        if (num % 2 == 0) {
                            System.out.println("thread2:" + num);
                            num = num + 1;
                            object.notify();
                        } else {
                            //System.out.println("thread 2 wait......");
                            try {
                                object.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}
