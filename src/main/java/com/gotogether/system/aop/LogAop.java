package com.gotogether.system.aop;

import com.gotogether.system.aop.log.LogTrace;
import com.gotogether.system.aop.log.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LogAop {
    private final LogTrace logTrace;

    @Pointcut("execution(* com.gotogether.controller.*Controller*.*(..))")
    public void allController(){};

    @Pointcut("execution(* com.gotogether.service.*Service*.*(..))")
    public void allService(){};

    @Pointcut("execution(* com.gotogether.repository.*Repository*.*(..))")
    public void allRepository(){};

    @Around("allController() || allService() || allRepository()")
    public Object logTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        try{
            status = logTrace.begin(joinPoint.getSignature().toShortString());
            Object result = joinPoint.proceed();
            logTrace.end(status);
            return result;
        }catch (Throwable e){
            logTrace.exception(status, e);
            throw e;
        }
    }
}
