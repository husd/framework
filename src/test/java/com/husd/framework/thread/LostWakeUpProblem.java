package com.husd.framework.thread;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class LostWakeUpProblem {

    private static int count = 0;

    private static Object obj = new Object();

    //多线程问题常见的一个场景
    public static void main(String[] args) {

        Thread producer = new Thread() {

            @Override
            public void run() {
                while (true) {
                    if (count == 0) {
                        System.out.println("生产者 增加count的值为1");
                        synchronized (obj) {
                            count++;
                            obj.notify();
                        }
                    } else {
                        //System.out.println("生产者 count的值目前不是0，不管");
                    }
                }
            }
        };

        Thread consumer = new Thread() {

            @Override
            public void run() {
                while (true) {
                    if (count <= 0) {
                        System.out.println("消费者 count的值为0，所以wait()");
                        try {
                            synchronized (obj) {
                                obj.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        count--;
                        System.out.println("消费者 增加count的值为1，所以减去1");
                    }
                }
            }
        };

        producer.start();
        consumer.start();
        try {
            producer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
