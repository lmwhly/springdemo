package test.com.luoo.study.Spring041.aop01;

import com.luoo.study.Spring041.aop01.DynamicProxy;
import com.luoo.study.Spring041.aop01.IMath;
import com.luoo.study.Spring041.aop01.Math;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * DynamicProxy Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>八月 11, 2016</pre>
 */
public class DynamicProxyTest {


    //实例化一个MathProxy代理对象
    //通过getProxyObject方法获得被代理后的对象
    IMath math = (IMath) new DynamicProxy().getProxyObject(new Math());

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: invoke(Object proxy, Method method, Object[] args)
     */
    @Test
    public void testInvoke() throws Exception {
//TODO: Test goes here...

        int n1 = 100, n2 = 5;
        math.add(n1, n2);
        math.sub(n1, n2);
        math.mut(n1, n2);
        math.div(n1, n2);
    }


} 
