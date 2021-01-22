package com.husd.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加上这个注解，就表示需要进行登录拦截
 * <p>
 * 这里其实本来只需要是METHOD就可以了
 * TYPE直接看源码的注释就行了。
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {

    String version() default "";
}
