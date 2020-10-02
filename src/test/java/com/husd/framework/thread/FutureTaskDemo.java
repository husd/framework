package com.husd.framework.thread;

import java.util.concurrent.*;

public class FutureTaskDemo {

    public static void main(String[] args) {

        System.out.println("主线程在执行任务。。。。。。。。。。。。。。");
        //实际的执行线程池不会关掉，会一直存在
        ExecutorService executor = Executors.newCachedThreadPool();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Task());
        executor.submit(futureTask);
        executor.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        try {
            System.out.println("task运行结果：" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕。。。。。。。。。。。。。");
    }

    static class Task implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("子线程在进行计算任务。。。。。");
            Thread.sleep(3000);
            return 100;
        }
    }
}

