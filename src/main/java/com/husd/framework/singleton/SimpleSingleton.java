package com.husd.framework.singleton;

/**
 * 单实例Singleton 示例
 * <p>
 * 现在更推荐使用枚举的方式实现了
 *
 * @author hushengdong
 */
public class SimpleSingleton {

    private static SimpleSingleton singleInstance = new SimpleSingleton();

    //Marking default constructor private
    //to avoid direct instantiation.
    private SimpleSingleton() {
    }

    //Get instance for class SimpleSingleton
    public static SimpleSingleton getInstance() {

        return singleInstance;
    }
}
