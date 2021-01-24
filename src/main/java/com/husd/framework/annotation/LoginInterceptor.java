package com.husd.framework.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 注释例子
 * <p>
 * 使用Spring AOP演示怎么使用注解的
 *
 * @author hushengdong
 */
@Aspect
@Configuration
public class LoginInterceptor {

    /**
     * 表示拦截这个注解
     */
    @Pointcut("@annotation(com.husd.framework.annotation.Login)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature s = joinPoint.getSignature();
        MethodSignature ms = (MethodSignature) s;
        Method m = ms.getMethod();
        Login login = m.getAnnotation(Login.class);
        //这里拿到了login的注解，可以读取注解的内容，做对应的处理
        //这里只有一个version字段
        String version = login.version();
        //这里可以处理登录信息了。
        //如果失败，直接返回失败信息就可以了。

        //处理好了之后，就返回即可。
        return joinPoint.proceed();
    }
}
