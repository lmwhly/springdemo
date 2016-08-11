package test.com.luoo.study.Spring041.aop05;

import com.luoo.study.Spring041.aop01.IMath;
import com.luoo.study.Spring041.aop01.Math;
import com.luoo.study.Spring041.aop05.SurroundAdvice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;

/**
 * AfterAdvice Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>八月 11, 2016</pre>
 */
public class AfterAdviceTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: afterReturning(Object o, Method method, Object[] objects, Object o1)
     */
    @Test
    public void testAfterReturning() throws Exception {
//TODO: Test goes here...
        //实例化Spring代理工厂
        ProxyFactory factory = new ProxyFactory();
        //设置被代理的对象
        factory.setTarget(new Math());
        //添加通知，横切逻辑
//        factory.addAdvice(new BeforeAdvice());
//        factory.addAdvice(new AfterAdvice());
        factory.addAdvice(new SurroundAdvice());

        //从代理工厂中获得代理对象
        IMath math = (IMath) factory.getProxy();
        int n1 = 100, n2 = 5;
        math.add(n1, n2);
        math.sub(n1, n2);
        math.mut(n1, n2);
        math.div(n1, n2);
    }
}
