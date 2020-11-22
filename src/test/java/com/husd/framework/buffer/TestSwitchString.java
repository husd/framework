package com.husd.framework.buffer;

import org.junit.Test;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class TestSwitchString {

    @Test
    public void test() {

        String s = "abc";
        switch (s) {
            case "abc":
                System.out.println("this is abc");
                break;
            case "c":
                System.out.println("this is c");
                break;
            default:
                break;
        }
    }
}
