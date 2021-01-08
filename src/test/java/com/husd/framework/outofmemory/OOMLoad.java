package com.husd.framework.outofmemory;

import com.husd.framework.TestHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 测试100W个对象
 *
 * @author hushengdong
 */
public class OOMLoad {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("start");
        List<OOMObj> largeList = new ArrayList<>(1000000);
        for (int i = 0; i < 1000000; i++) {
            OOMObj oomObj = new OOMObj(TestHelper.anyString(10), TestHelper.anyString(16));
            if (i % 100000 == 0) {
                Thread.sleep(1000);
            }
            largeList.add(oomObj);
        }
        //大概占用160M内存
        System.out.println("end");
        List<OOMObj> userList = largeList.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(
                        Comparator.comparing(user -> user.getName()))), ArrayList::new));
        //加上userList之后，大概占用207M内存
        System.out.println("end 123");
        Thread.sleep(100000);
    }

}
