package util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BeforeAspect {
    private int num = 0;
    @Before("execution(* service.*Service.*(..))")
    public void test(JoinPoint jp){
        System.out.println(++num+"번 째 실행 : "+jp.getSignature().getName());
    }
}
