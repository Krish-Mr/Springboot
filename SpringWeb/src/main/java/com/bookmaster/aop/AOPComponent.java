package com.bookmaster.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AOPComponent {

    @Pointcut("execution(* com.bookmaster.aop.run.*.*(..))")
    public void pointcut() {}

    @Before("pointcut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("Before Method: " + joinPoint.getSignature().getName());
    }

    @After("pointcut()")
    public void afterAdvice() {
        System.out.println("After method execution");
    }

    @Around("pointcut()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Before invoke");
        Object result = pjp.proceed();
        System.out.println("After invoke");
        return result;
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "ex")
    public void afterThrowingAdvice(Exception ex) {
        System.out.println("Exception thrown: " + ex.getMessage());
    }

    @AfterReturning(pointcut = "pointcut()", returning = "result")
    public void afterReturningAdvice(Object result) {
        System.out.println("After successful return without exception");
        System.out.println("Returned value: " + result);
    }
}
