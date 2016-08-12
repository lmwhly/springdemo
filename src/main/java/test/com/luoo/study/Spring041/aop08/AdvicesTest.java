package test.com.luoo.study.Spring041.aop08;

import com.luoo.study.Spring041.aop03.MyMath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/** 
* Advices Tester. 
* 
* @author <Authors name> 
* @since <pre>八月 12, 2016</pre> 
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
} 

/** 
* 
* Method: after(JoinPoint jp) 
* 
*/ 
@Test
public void testAfter() throws Exception { 
//TODO: Test goes here...
    ApplicationContext ctx = new ClassPathXmlApplicationContext("aop02.xml");
    MyMath math = (MyMath)ctx.getBean(MyMath.class);

    int n1 = 100, n2 = 5;
   /* math.add(n1, n2);
    math.sub(n1, n2);
    math.mut(n1, n2);
    math.div(n1, n2);*/
    math.argtest(n1);

} 


} 
