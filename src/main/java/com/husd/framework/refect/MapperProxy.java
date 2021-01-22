package com.husd.framework.refect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 这个是代理类
 */
public class MapperProxy implements InvocationHandler {

    //被代理的对象
    private Object target;

    public MapperProxy(Object target) {
        this.target = target;
    }

    public MapperProxy() {
    }

    public <T> T newInstance(Class<T> clz) {
        // 反射有2种，一种是JDK的动态接口 如果是接口的话，就是jdk
        // 一种是CGLIB ，对于普通的类的反射，就是cglib
        System.out.println("动态代理接口，只能使用JDK的动态代理");
        return (T) Proxy.newProxyInstance(clz.getClassLoader(), clz.getInterfaces(), this);
    }

    public <T> T newInstance2(Class<T> clz) {
        // 反射有2种，一种是JDK的动态接口 如果是接口的话，就是jdk
        // 一种是CGLIB ，对于普通的类的反射，就是cglib
        System.out.println("动态代理接口，只能使用JDK的动态代理");
        return (T) Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, this);
    }

    @Override
    public Object invoke(Object obj, Method method, Object[] args) throws Throwable {

        System.out.println("执行动态代理功能  --------- 进入方法之前");
//        if (Object.class.equals(method.getDeclaringClass())) {
//            try {
//                // 诸如hashCode()、toString()、equals()等方法，将target指向当前对象this
//                // 也就是这些方法，不代理，直接执行自身的方法。
//                return method.invoke(this, args);
//            } catch (Throwable t) {
//            }
//        }
        //这里要写具体的方法
        //执行被代理类的方法
        if (target != null) {
            //对于事务类的应用，需要把target注入进来，这样才能执行本来的方法
            //对于mybatis这样的框架，就是接口，就不需要这么做了。
            method.invoke(target, args);
        }
        Student s = new Student((Integer) args[0], "代理名称");
        System.out.println("执行动态代理功能  --------- 进入方法之后");
        return s;
    }
}
