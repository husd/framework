package com.husd.framework.refect;

/**
 * 借用理解mybatis原理的时机，理解JDK的动态代理技术
 */
public class JdkReflectDemo {

    public static void main(String[] args) {

        //right();
        wrong();
    }

    public static void right() {
        //只能代理接口
        MapperProxy mapperProxy = new MapperProxy();
        StudentMapper studentMapper = mapperProxy.newInstance(StudentMapper.class);
        Student student = studentMapper.getStudentById(10);
        // System.out.println(student.getId());
        // System.out.println(student.getName());
    }

    public static void wrong() {

        StudentMapper s2 = new StudentMapper2();
        MapperProxy mapperProxy = new MapperProxy(s2);
        StudentMapper studentMapper2 = mapperProxy.newInstance(s2.getClass());
        Student student2 = studentMapper2.getStudentById(10);
        //System.out.println(student2.getId());
        //System.out.println(student2.getName());
    }

}
