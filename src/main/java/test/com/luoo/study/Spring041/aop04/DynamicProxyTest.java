package test.com.luoo.study.Spring041.aop04;

import com.luoo.study.Spring041.aop01.IMath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * DynamicProxy Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>八月 11, 2016</pre>
 */
public class DynamicProxyTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


    /**
     * Method: intercept(Object object, Method method, Object[] args, MethodProxy methodProxy)
     */
    @Test
    public void testIntercept() throws Exception {
//TODO: Test goes here...
//    Math math = (Math) new DynamicProxy().getProxyObject(new Math());

        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        //从代理工厂中获得代理对象
        IMath math = (IMath) ctx.getBean("proxy");

        int n1 = 100, n2 = 5;
        math.add(n1, n2);
        math.sub(n1, n2);
        math.mut(n1, n2);
        math.div(n1, n2);
    }


} 
