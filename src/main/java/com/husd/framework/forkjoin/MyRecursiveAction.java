package com.husd.framework.forkjoin;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

/**
 * 这个是异步代码，可以不用等线程返回
 */
public class MyRecursiveAction extends RecursiveAction {

    private long start;
    private long end;
    private AtomicLong sum;

    public MyRecursiveAction(long start, long end,AtomicLong sum) {
        this.start = start;
        this.end = end;
        this.sum = sum;
    }

    @Override
    protected void compute() {
        if(end - start < 1000) {
            //如果觉得任务不能再细分了，就执行计算逻辑
            // 这里的计算逻辑是计算sum(start,end)
            // 包含结束的节点
            sum.addAndGet(LongStream.rangeClosed(start,end).sum());
        } else {
            //如果觉得任务还是太大，可以拆分成子任务处理。
            long mid = start + (end - start) / 2;
            MyRecursiveTask left = new MyRecursiveTask(start,mid);
            MyRecursiveTask right = new MyRecursiveTask(mid + 1,end);
            left.fork();
            right.fork();
        }
    }
}
