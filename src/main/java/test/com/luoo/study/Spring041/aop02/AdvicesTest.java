package test.com.luoo.study.Spring041.aop02;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.luoo.study.Spring041.aop02.Math;

/** 
* Advices Tester. 
* 
* @author <Authors name> 
* @since <pre>锟斤拷锟斤拷 11, 2016</pre> 
* @version 1.0 
*/ 
public class AdvicesTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: before(JoinPoint jp) 
* 
*/ 
@Test
public void testBefore() throws Exception { 
//TODO: Test goes here...
    ApplicationContext ctx = new ClassPathXmlApplicationContext("aop02.xml");
    Math math = ctx.getBean("math", Math.class);
    int n1 = 100, n2 = 5;
    math.add(n1, n2);
    math.sub(n1, n2);
    math.mut(n1, n2);
    math.div(n1, n2);
} 

/** 
* 
* Method: after(JoinPoint jp) 
* 
*/ 
@Test
public void testAfter() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: around(ProceedingJoinPoint pjd) 
* 
*/ 
@Test
public void testAround() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: afterThrowing(JoinPoint jp, Exception exp) 
* 
*/ 
@Test
public void testAfterThrowing() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: afterReturning(JoinPoint joinPoint, Object result) 
* 
*/ 
@Test
public void testAfterReturning() throws Exception { 
//TODO: Test goes here... 
} 


} 
