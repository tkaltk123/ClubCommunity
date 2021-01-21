package util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeChecker {
    @Around("execution(* service.*Service.*(..))")
    public void check(ProceedingJoinPoint jp) throws  Throwable{
        long start = System.nanoTime();
        jp.proceed();
        long time = System.nanoTime() - start;
        String name = jp.getSignature().getName();
        System.out.println(name + " : "+time+"ns");
    }
}
