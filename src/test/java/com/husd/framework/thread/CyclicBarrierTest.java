package com.husd.framework.thread;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    //从字面上的意思可以知道，这个类的中文意思是“循环栅栏”。大概的意思就是一个可循环利用的屏障。
    //它的作用就是会让所有线程都等待完成后才会继续下一步行动。
    //举个例子，就像生活中我们会约朋友们到某个餐厅一起吃饭，有些朋友可能会早到，有些朋友可能会晚到，
    // 但是这个餐厅规定必须等到所有人到齐之后才会让我们进去。这里的朋友们就是各个线程，餐厅就是 CyclicBarrier。

    //人都到齐了，这个时候上菜了
    //所有人都端起来了酒，这个时候大家才一起喝，如果有1个人没端起来酒，那就先不喝

        static class TaskThread extends Thread {
            CyclicBarrier barrier;
            public TaskThread(CyclicBarrier barrier) {
                this.barrier = barrier;
            }
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println(getName() + " 到达栅栏 A");
                    barrier.await();
                    System.out.println(getName() + " 冲破栅栏 A");
                    Thread.sleep(2000);
                    System.out.println(getName() + " 到达栅栏 B");
                    barrier.await();
                    System.out.println(getName() + " 冲破栅栏 B");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public static void main(String[] args) {
            int threadNum = 5;
            CyclicBarrier barrier = new CyclicBarrier(threadNum, new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " 完成最后任务");
                }
            });
            for(int i = 0; i < threadNum; i++) {
                new TaskThread(barrier).start();
            }
        }

}
