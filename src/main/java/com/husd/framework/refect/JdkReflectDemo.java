package com.husd.framework.refect;

/**
 * 借用理解mybatis原理的时机，理解JDK的动态代理技术
 */
public class JdkReflectDemo {

    public static void main(String[] args) {
        MapperProxy mapperProxy = new MapperProxy();
        StudentMapper studentMapper = mapperProxy.newInstance(StudentMapper.class);
        Student student = studentMapper.getStudentById(10);
        System.out.println(student.getId());
        System.out.println(student.getName());
    }
}
