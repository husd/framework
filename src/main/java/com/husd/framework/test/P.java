package com.husd.framework.test;

/**
 * @author hushengdong
 */
public class P {

    public int age;

    public int getAge() {

        System.out.println("p get age");
        return age;
    }

    public int getAgeNotOverride() {

        System.out.println("p get age not orverride");
        return age;
    }

    public static int getMoreAge() {

        System.out.println(" p get more age");
        return 10;
    }
}
