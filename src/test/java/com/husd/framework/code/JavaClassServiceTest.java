package com.husd.framework.code;

import com.husd.framework.code.impl.JavaClassServiceImpl;

/**
 * @author hushengdong
 */
public class JavaClassServiceTest {

    public static void main(String[] args) {

        JavaClassService javaClassService = new JavaClassServiceImpl();

        StringBuilder text = new StringBuilder();
        javaClassService.addGET_SET(text, "String", "name");
        System.out.println(text.toString());

    }
}
