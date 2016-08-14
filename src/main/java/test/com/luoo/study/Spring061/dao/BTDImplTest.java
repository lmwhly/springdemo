package test.com.luoo.study.Spring061.dao;

import com.luoo.study.Spring061.dao.BTDImpl;
import com.luoo.study.Spring061.entities.BookType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;

/**
 * BTDImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>八月 14, 2016</pre>
 */
public class BTDImplTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


    /**
     * Method: getAllBookTypes()
     */
    @Test
    public void testGetAllBookTypes() throws Exception {
//TODO: Test goes here...

        //初始化容器
        ApplicationContext ctx=new ClassPathXmlApplicationContext("ApplicationContext.xml");
        //获得bean
        BTDImpl bTDImpl=ctx.getBean(BTDImpl.class);
        //访问数据库
        List<BookType> booktypes=bTDImpl.getAllBookTypes();
        for (BookType bookType : booktypes) {
            System.out.println(bookType);
        }
        assertNotNull(booktypes);

    }


} 
