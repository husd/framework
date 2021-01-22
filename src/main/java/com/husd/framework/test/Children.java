package com.husd.framework.test;

/**
 * @author hushengdong
 */
public class Children extends P {

    //@Override
    public int getAge() {

        System.out.println("children get age");
        getMoreAge();
        return age + 10;
    }

    public static int getMoreAge() {

        System.out.println("children getMoreAge");
        return 11;
    }
}
