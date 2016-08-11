package test.com.luoo.study.Spring051.ioc03;

import com.luoo.study.Spring051.ioc03.BookService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * BookService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>八月 11, 2016</pre>
 */
public class BookServiceTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: storeBook(String bookname)
     */
    @Test
    public void testStoreBook() throws Exception {
//TODO: Test goes here...
        //容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("IOCBeans03.xml");

        BookService bookservice = (BookService) ctx.getBean(BookService.class);
        bookservice.storeBook("《Spring MVC权威指南 第一版》");
    }


} 
