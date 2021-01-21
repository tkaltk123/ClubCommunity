package util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ErrorChecker {
    @AfterThrowing(pointcut = "execution(* service.*Service.*(..))", throwing = "exception")
    public void checker(JoinPoint jp, Exception exception) throws Exception{
        String name = jp.getSignature().getName();
        System.out.println(name+"에서 에러 발생");
    }
}
