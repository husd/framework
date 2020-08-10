package com.husd.framework.thread;

import org.junit.Test;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class ThreadInterrupted {

    @Test
    public void test() throws Exception {

        //对于处于new和terminated状态的线程对于中断是屏蔽的，也就是说中断操作对这两种状态下的线程是无效的

        //处于BLOCKED状态，执行中断操作之后，该线程仍然处于BLOCKED状态，但是中断标志位却已被修改。
        // 这种状态下的线程和处于RUNNABLE状态下的线程是类似的，给了我们程序更大的灵活性去判断和处理中断
        Thread thread = new MyThread();
        thread.interrupt();
        System.out.println("线程当前状态1 NEW：" + thread.isInterrupted());

        Thread thread2 = new MyThread();
        thread2.start();
        thread2.join();
        System.out.println(thread2.getState());
        thread2.interrupt();
        System.out.println("线程当前状态2 TERMINATED：" + thread2.isInterrupted());
    }

    @Test
    public void testRunnable() throws InterruptedException {

        //处于RUNNBALE状态下的线程即便遇到中断操作，也只会设置中断标志位并不会实际中断线程运行
        //那么设置中断标志位，有什么用呢？
        //可以在程序中判断 线程是否被中断
        Thread thread = new MyThread2();
        thread.start();
        System.out.println(thread.getState());
        thread.interrupt();
        Thread.sleep(1000);//等到thread线程被中断之后
        System.out.println(thread.isInterrupted());
        System.out.println(thread.getState());
    }

    @Test
    public void testBlock() throws InterruptedException {

        //WAITING/TIMED_WAITING
        // 这两种状态本质上是同一种状态，只不过TIMED_WAITING在等待一段时间后会自动释放自己，而WAITING则是无限期等待，
        // 需要其他线程调用notify方法释放自己。但是他们都是线程在运行的过程中由于缺少某些条件而被挂起在某个对象的等待队列上。
        // 当这些线程遇到中断操作的时候，会抛出一个InterruptedException异常，并清空中断标志位。

        Thread thread = new MyThread3();
        thread.start();
        Thread.sleep(500);
        System.out.println(thread.getState());
        thread.interrupt();
        Thread.sleep(1000);
        System.out.println(thread.isInterrupted());
        thread.join();
    }
}

class MyThread2 extends Thread {

    @Override
    public void run() {
        while (true) {
            //do something
            //可以判断是否被中断，如果中断了，就可以做一些事情
//            if (Thread.currentThread().isInterrupted()){
//                System.out.println("exit MyThread");
//                break;
//            }
        }
    }
}

class MyThread3 extends Thread {

    @Override
    public void run() {
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("i am waiting but facing interrupt exception now");
            }
        }
    }
}
