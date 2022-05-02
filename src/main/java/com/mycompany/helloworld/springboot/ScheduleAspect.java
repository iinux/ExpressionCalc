package com.mycompany.helloworld.springboot;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class ScheduleAspect {
    // @Pointcut("execution (public * com.spring..*Test.*(..))")
    @Pointcut("@annotation(com.mycompany.helloworld.springboot.ScheduleAnnotation)")
    protected void testMethod() {
    }

    // @Around("execution(* com.web.controller.app.BffController.backstageGetData(..))")
    @Around(value = "testMethod()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Signature sig = point.getSignature();
        Object[] args = point.getArgs();
        String key = sig.getDeclaringTypeName() + "." + sig.getName();

        int expireTime = 30;
        Method method = point.getTarget().getClass().getMethod(sig.getName());
        ScheduleAnnotation scheduleAnnotation = method.getAnnotation(ScheduleAnnotation.class);
        if (scheduleAnnotation != null) {
            expireTime = scheduleAnnotation.expireTime();
        }

        System.out.println(key + "," + expireTime);

        try {
            log.info("schedule before");
            point.proceed();
            log.info("schedule after");
        } catch (Exception e) {
            log.error("ScheduleAspect failed.", e);
        }
        return null;
    }
}
