package com.husd.framework.thread;

import java.util.concurrent.locks.LockSupport;

public class ParkDemo {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("this is start demo for park1");

        //
        LockSupport.unpark(Thread.currentThread());
        LockSupport.park();
        LockSupport.unpark(Thread.currentThread());
        LockSupport.park();
        System.out.println("this is start demo for park2");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("sub thread park for park2");
                LockSupport.park();
            }
        });
        t.start();

        Thread.sleep(1000);
        LockSupport.unpark(t);
        System.out.println("end .....");
    }
}
