package com.husd.framework.aop;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD})
@Retention(RUNTIME)
public @interface Login {

    //这个方法，可以是其它的，可以认为是注解的配置信息
    boolean required() default true;

    Class<?>[] groups() default {};

}
