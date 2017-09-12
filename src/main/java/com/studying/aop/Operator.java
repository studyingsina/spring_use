package com.studying.aop;

import com.studying.util.LoggerUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Lazy
public class Operator {

    @Pointcut("execution(* com.studying.aop.inner..*.*(..))")
    public void pointCut() {
    }

//    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        LoggerUtil.logger.info("AOP Before Advice...");
    }

//    @After("pointCut()")
    public void doAfter(JoinPoint joinPoint) {
        LoggerUtil.logger.info("AOP After Advice...");
    }

//    @AfterReturning(pointcut = "pointCut()", returning = "returnVal")
    public void afterReturn(JoinPoint joinPoint, Object returnVal) {
        LoggerUtil.logger.info("AOP AfterReturning Advice:" + returnVal);
    }

//    @AfterThrowing(pointcut = "pointCut()", throwing = "error")
    public void afterThrowing(JoinPoint joinPoint, Throwable error) {
        LoggerUtil.logger.info("AOP AfterThrowing Advice..." + error);
        LoggerUtil.logger.info("AfterThrowing...");
    }

    @Around("pointCut()")
    public void around(ProceedingJoinPoint pjp) {
        LoggerUtil.logger.info("AOP Aronud before...");
        try {
            pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        LoggerUtil.logger.info("AOP Aronud after...");
    }

}