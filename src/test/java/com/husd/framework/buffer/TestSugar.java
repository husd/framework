package com.husd.framework.buffer;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class TestSugar {

    @Test
    public void test() throws IOException {

        int a = 1290;
        Integer b = new Integer(1000);
        Integer a1 = a;
        int b1 = b + 1;

        System.out.println(" b is " + b);


        List<String> arr = new ArrayList();
        arr.add("this is first");
        arr.add("this is another");

        for (String obj : arr) {
            System.out.println(obj);
        }

        String content = "this is what you want to write to file";
        try (BufferedWriter out = new BufferedWriter(new FileWriter("/tmp/a.txt", true))) {
            out.write(content);
            System.out.println("content is :" + content);
        } catch (IOException e) {
            // error processing code
        } finally {
        }

//        String arr1 = arr.get(0);
//
//        for (String str : arr) {
//            System.out.println(str);
//        }
        printStr("first", "second");

        System.out.println(Len.M);
        System.out.println(Len.N);

    }

    private void printStr(String... strArr) {

        for (String s : strArr) {
            System.out.println(s);
        }
    }

}

enum Len {

    M, N;
}
