package com.husd.framework.limit;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 方法级别的限流例子
 * <p>
 * 侵入性会很强，作为一种选择
 *
 * @author hushengdong
 */
public class LimitRequest {

    private static AtomicInteger currentNum = new AtomicInteger(0);

    //使用2个数字，来存储每秒的访问总数
    private static AtomicInteger num1 = new AtomicInteger(0);
    private static long version1 = 0L;

    private static AtomicInteger num2 = new AtomicInteger(0);
    private static long version2 = 0L;

    public void test() {

        try {
            // 限流接口，获取最大的限流数量，一般可以从配置文件读取，可以修改
            // 这个限流接口是同时能访问的最大数量
            // 没有单位时间的限制，还有一种限流是每秒钟最多能有多少请求访问，实现会不一样
            int limitNum = 1000;
            int currNum = currentNum.incrementAndGet();
            if (currNum > limitNum) {
                // 切换到备份服务，或者简单返回，或者抛出服务太忙了，请用户稍后再试等
                return;
            }
            // 正常业务逻辑
        } catch (Exception e) {
            // 异常处理
        } finally {
            //把数量降低
            currentNum.decrementAndGet();
        }
    }

    //这个实现代码可能会有BUG
    public void test2() {

        AtomicInteger limit = null;
        long seconds = System.currentTimeMillis() / 1000L;
        boolean qi = (seconds & 1) == 1;
        try {
            int limitNum = 1000;
            //这里就要判断需要用到哪个数量了，定个简单的规则，奇数秒，用num1，偶数秒，用num2
            limit = getCurrentNum(seconds, qi);
            int currNum = limit.getAndIncrement();
            if (currNum > limitNum) {
                // 切换到备份服务，或者简单返回，或者抛出服务太忙了，请用户稍后再试等
                return;
            }
            // 正常业务逻辑
        } catch (Exception e) {
            // 异常处理
        } finally {
            //把数量降低 如果业务时间太长，就会出现 不必要操作的情况
            if (qi && seconds == version1) {
                limit.decrementAndGet();
            } else if (qi == false && seconds == version2) {
                limit.decrementAndGet();
            }
        }
    }

    private AtomicInteger getCurrentNum(long seconds, boolean qi) {

        if (qi) {
            //num1
            if (seconds == version1) {
                return num1;
            } else {
                //这块得加锁了 要不多个线程同时设置为0，结果就不对了
                num1.set(0);
                version1 = seconds;
                return num1;
            }
        } else {
            if (seconds == version2) {
                return num2;
            } else {
                //这块得加锁了
                num2.set(0);
                version2 = seconds;
                return num2;
            }
        }
    }

}
