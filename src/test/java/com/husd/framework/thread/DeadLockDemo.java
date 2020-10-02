package com.husd.framework.thread;

//最基本的死锁问题
public class DeadLockDemo {

    private static Object reourceA = new Object();
    private static Object reourceB = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {

                synchronized (reourceA) {
                    System.out.println("a is get resource a");

                    try{
                        Thread.sleep(2000);
                    } catch (Exception e) {

                    }
                    System.out.println("a is waiting b");
                    synchronized (reourceB) {
                        System.out.println("a is get resource b");
                    }
                }
            }
        });

        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {

                synchronized (reourceB) {
                    System.out.println("b is get resource b");

                    try{
                        Thread.sleep(2000);
                    } catch (Exception e) {

                    }
                    System.out.println("b is waiting a");
                    synchronized (reourceA) {
                        System.out.println("b is get resource a");
                    }
                }

            }
        });

        a.start();
        b.start();
    }
}
