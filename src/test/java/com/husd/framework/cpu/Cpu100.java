package com.husd.framework.cpu;

import com.husd.framework.TestHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class Cpu100 {

    public static void main(String[] args) {

        //模拟CPU100的场景
        List<String> list = new LinkedList<>();
        for (int i = 0; i < 10000000; i++) {
            list.add(TestHelper.anyString(20));
        }

        for (int i = 0; i < 10000000; i++) {
            list.contains("this is a long string that");
        }


    }
}
