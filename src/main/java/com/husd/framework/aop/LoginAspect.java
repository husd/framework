package com.husd.framework.aop;

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
 * 登录拦截器 这里是通过注解实现
 *
 * @author hushengdong
 */
@Aspect
@Configuration
public class LoginAspect {

    @Pointcut("@annotation(com.husd.framework.aop.Login)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        boolean isValidateToken = true;

        Signature s = joinPoint.getSignature();
        MethodSignature ms = (MethodSignature) s;
        Method m = ms.getMethod();
        Login annotation = m.getAnnotation(Login.class);
        //可以用这个，也可以不用
        boolean required = annotation.required();
        //检查登录是否成功
        boolean check = false;
        if (check) {
            Object proceed = joinPoint.proceed();
            return proceed;
        } else {
            //这里可以返回固定的登录失败信息
            return HttpResponse.notAuth();
        }


    }


}
