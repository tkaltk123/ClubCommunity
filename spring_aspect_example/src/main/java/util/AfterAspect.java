package util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AfterAspect {
    @After("execution(* service.*Service.*(..))")
    public void test(JoinPoint jp){
        System.out.println(jp.getSignature().getName()+" : 실행 완료");
    }
}
