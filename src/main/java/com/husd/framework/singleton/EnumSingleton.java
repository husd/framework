package com.husd.framework.singleton;

/**
 * 枚举方式的单例
 *
 * @author hushengdong
 */
public enum EnumSingleton {

    INSTANCE;

    public int someMethod() {

        System.out.println("这里是单例的所有逻辑");
        return 0;
    }

    public static void main(String[] args) {

        //使用的时候这样用
        int a = EnumSingleton.INSTANCE.someMethod();
        System.out.println(a);
    }
}
