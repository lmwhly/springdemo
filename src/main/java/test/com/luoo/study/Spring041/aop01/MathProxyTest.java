package test.com.luoo.study.Spring041.aop01;

import com.luoo.study.Spring041.aop01.IMath;
import com.luoo.study.Spring041.aop01.MathProxy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * MathProxy Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>八月 11, 2016</pre>
 */
public class MathProxyTest {

    IMath math = new MathProxy();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: add(int n1, int n2)
     */
    @Test
    public void testAdd() throws Exception {
//TODO: Test goes here...
        int n1 = 100, n2 = 5;
        math.add(n1, n2);
        math.sub(n1, n2);
        math.mut(n1, n2);
        math.div(n1, n2);
    }


} 
