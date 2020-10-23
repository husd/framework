package com.husd.framework.util;

import org.junit.Test;

public class SomeTest {

    @Test
    public void test() {

        int a = 10;
        int b = a + --a;
        // 这段代码测试运算符的优先级，jdk 1.8 b = 19
        System.out.println("b is :" + b);
    }
}
