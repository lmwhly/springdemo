package com.luoo.study.Spring041.aop08;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * @author Luoo
 * @create 2016-08-11 17:47
 */
@Component
@Aspect
public class Advices {

    //切点
    @Pointcut("execution(* com.luoo.study.Spring041.aop03.MyMath.*(..))")
    public void pointcut(){
    }

    /*@Before("@annotation(com.luoo.study.Spring041.aop03.MyAnno)")
    public void before(JoinPoint jp){
        System.out.println("----------最终通知----------");

    }*/

    @Before("pointcut()")
    public void before(JoinPoint jp) throws SQLException {
        System.out.println("----------前置通知----------");
        if(1==1)
            throw new SQLException("SQL test");
        System.out.println(jp.getSignature().getName());
    }

    /*@After("pointcut()")
    public void after(JoinPoint jp) {
        System.out.println("--------------------after--------------------");
    }*/

    /*@After("within(com.luoo.study.Spring041.aop03.*)")
    public void after(JoinPoint jp) {
        System.out.println("--------------------after--------------------");
    }*/


   /* @After("args(int)")
    public void after(JoinPoint jp) {
        System.out.println("--------------------after--------------------");
    }*/

    //环绕通知
   /* @Around("execution(* com.luoo.study.Spring041.aop03.MyMath.s*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println(pjp.getSignature().getName());
        System.out.println("----------环绕前置----------");
        Object result=pjp.proceed();
        System.out.println("----------环绕后置----------");
        return result;
    }*/

    /*@AfterReturning(pointcut="execution(* com.luoo.study.Spring041.aop03.MyMath.a*(..))",returning="result")
    public void afterReturning(JoinPoint jp, Object result){
        System.out.println(jp.getSignature().getName());
        System.out.println("结果是："+result);
        System.out.println("----------返回结果----------");
    }*/

    //异常后通知
   /* @AfterThrowing(pointcut="execution(* com.luoo.study.Spring041.aop03.MyMath.m*(..))",throwing="exp")
    public void afterThrowing(JoinPoint jp,Exception exp){
        System.out.println(jp.getSignature().getName());
        System.out.println("异常消息："+exp.getMessage());
        System.out.println("----------异常通知----------");
    }*/






}
