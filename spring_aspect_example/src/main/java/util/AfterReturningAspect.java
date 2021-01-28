package util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AfterReturningAspect {
    @AfterReturning(value = "execution(* service.*Service.*(..))")
    public void test(JoinPoint jp){
        System.out.println(jp.getSignature().getName()+" : 정상 종료");
    }
}
