package com.husd.framework.refect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 这个是代理类
 */
public class MapperProxy implements InvocationHandler {

    public <T> T newInstance(Class<T> clz) {
        // 反射有2种，一种是JDK的动态接口 如果是接口的话，就是jdk
        // 一种是CGLIB ，对于普通的类的反射，就是cglib
        return (T) Proxy.newProxyInstance(clz.getClassLoader(), new Class[] { clz }, this);
    }

    @Override
    public Object invoke(Object obj, Method method, Object[] args) throws Throwable {

        if (Object.class.equals(method.getDeclaringClass())) {
            try {
                // 诸如hashCode()、toString()、equals()等方法，将target指向当前对象this
                return method.invoke(this, args);
            } catch (Throwable t) {
            }
        }
        //这里要写具体的方法
        return new Student((Integer) args[0], "test name");
    }
}
