package com.husd.framework.cache;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 布隆过滤器
 * <p>
 * 当一个元素被加入集合时，通过K个Hash函数将这个元素映射成一个位数组中的K个点，
 * 把它们置为1。检索时，我们只要看看这些点是不是都是1就（大约）知道集合中有没有它了：
 * <p>
 * 如果这些点有任何一个0，则被检元素一定不在；
 * 如果都是1，则被检元素很可能在。
 * <p>
 * 这就是布隆过滤器的基本思想。
 *
 * @author hushengdong
 */
public class BloomFilterDemo {

    private static int SIZE = 1000000;

    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), SIZE);

    public static void add() {

        for (int i = 0; i < SIZE; i++) {
            if (i % 10 == 0) {
                bloomFilter.put(i);
            }
        }
    }

    public static void main(String[] args) {

        add();

        long startTime = System.currentTimeMillis(); // 获取开始时间
        //判断这一百万个数中是否包含29999这个数
        int hitCount = 0;
        for (int i = 0; i < 10000; i++) {
            if (bloomFilter.mightContain(i)) {
                hitCount++;
            }
        }
        long endTime = System.currentTimeMillis();   // 获取结束时间
        System.out.println("程序运行时间： " + (endTime - startTime) + "毫秒 hitCount :" + hitCount);
    }

}
