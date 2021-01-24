package com.husd.framework.refect;

/**
 * 这个是实体的
 */
public class StudentMapper2 implements StudentMapper {

    public Student getStudentById(Integer id) {

        System.out.println("---------- 执行了具体的业务方法 -------------");
        return new Student(1, "name");
    }
}
