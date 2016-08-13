package test.com.luoo.study.Spring041.aop08;

import com.luoo.study.Spring041.aop01.IMath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Advices Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>八月 11, 2016</pre>
 */
public class AdvicesTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: before(JoinPoint jp)
     */
    @Test
    public void testBefore() throws Exception {
//TODO: Test goes here...
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beansOfAOP.xml");
        IMath math = (IMath) ctx.getBean("math");
        int n1 = 100, n2 = 5;
        math.add(n1, n2);
        math.sub(n1, n2);
        math.mut(n1, n2);
        math.div(n1, n2);
    }



} 
