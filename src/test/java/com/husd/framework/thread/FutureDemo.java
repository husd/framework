package com.husd.framework.thread;

import java.util.concurrent.*;

/**
 * 演示future 实现异步操作
 */
public class FutureDemo {

    public static void main(String[] args) {

        FutureDemo futureDemo = new FutureDemo();
        futureDemo.method();
    }

    private void method() {
        System.out.println("主线程在执行任务");
        // 假设
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Integer> result = executor.submit(new Task());
        executor.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        try {
            //等待线程执行完成之后，再结束
            System.out.println("task运行结果" + result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");
    }

    class Task implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {

            System.out.println("子线程开始 执行计算任务 thread start .....");
            Thread.sleep(3000);
            System.out.println("子线程结束执行任务 thread end .....");
            return 100;
        }
    }
}
