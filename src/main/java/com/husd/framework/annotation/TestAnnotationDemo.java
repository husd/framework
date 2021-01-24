package com.husd.framework.annotation;

import java.lang.reflect.Method;

/**
 * @author hushengdong
 */
public class TestAnnotationDemo {

    public static void main(String[] args) throws NoSuchMethodException {

        //设置查看动态代理类
        System.setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
        test(123);
        Class clz = TestAnnotationDemo.class;
        Method method = clz.getMethod("test", Integer.class);
        AnnotationDemo annotationDemo = method.getAnnotation(AnnotationDemo.class);
        System.out.println("注解名字:" + annotationDemo);
        System.out.println("注解val:" + annotationDemo.val());
    }

    @AnnotationDemo(val = "this is a test")
    public static void test(Integer a) {

        System.out.println("this is a test method: " + a);
    }
}
