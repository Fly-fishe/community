package com.nowcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class AlphaAspect {

    @Pointcut("execution(* come.nowcoder.community.service.*.*(..))")
    public void pointcut(){

    }

    @After("pointcut()")
    public void after(){

        System.out.println("after");
    }

    @AfterReturning("pointcut()")
    public void afterreturning(){

        System.out.println("afterreturning");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing(){

        System.out.println("afterThrowing");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws  Throwable {
        System.out.println("around before");
        Object obj = joinPoint.proceed();
        System.out.println("around after");
        return obj;
    }

}
