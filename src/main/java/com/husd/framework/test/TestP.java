package com.husd.framework.test;

/**
 * @author hushengdong
 */
public class TestP {

    public static void main(String[] args) {

        P p = new Children();
        p.getAge();
        //P.getMoreAge();
        p.getAgeNotOverride();

        Children c = new Children();
        //c.getAge();
        //Children.getMoreAge();

    }
}
