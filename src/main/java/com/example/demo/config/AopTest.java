package com.example.demo.config;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component//该注解是让AopTest注入到spring容器中
public class AopTest {

    private static final Logger logger = Logger.getLogger(AopTest.class);   //日志，我用的log4j

    //配置切点
    @Pointcut("execution(* com.example.demo.controller..*(..))")
    public void log() {
    }

    @Before("log()")  //前置通知
    public void beforeExcution(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        logger.info("前置通知开启=====================");

//        logger.info("URL : " + request.getRequestURL().toString());
//        logger.info("HTTP_METHOD : " + request.getMethod());
//        logger.info("IP : " + request.getRemoteAddr());
//        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
//
//        logger.info("前置通知结束=====================");
    }

    @AfterReturning(returning = "ret", pointcut = "log()")  //后置返回
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
    }
//    pom配置依赖
    /*<dependency>
			<groupId>org.aspectj</groupId>
			<artifactId>aspectjweaver</artifactId>
			<version>1.8.5</version>
		</dependency>*/
}

//重点：
//@Configration标识的类应该增加@EnableAspectJAutoproxy，并且声明AopTest类
//
//@Configration
//@EnableAspectJAutoproxy
//public class name {
//	
//	@Bean
//	public AopTest aopTest(){
//		return new AopTest();
//	}