package test.com.luoo.study.Spring061.dao;

import com.luoo.study.Spring061.entities.BookType;
import com.luoo.study.Spring061.mapping.BookTypeDAO;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;

/**
 * BookTypeDAOImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>八月 12, 2016</pre>
 */
public class BookTypeDAOImplTest2 {

    /**
     * Method: getAllBookTypes()
     */
    @Test
    public void testGetAllBookTypes() throws Exception {
//TODO: Test goes here...
        //初始化容器
        ApplicationContext ctx=new ClassPathXmlApplicationContext("ApplicationContext.xml");
        //获得bean
        BookTypeDAO bookTypeDao=ctx.getBean("bookTypeDao",BookTypeDAO.class);
        //访问数据库
        List<BookType> booktypes=bookTypeDao.getAllBookTypes();
        for (BookType bookType : booktypes) {
            System.out.println(bookType);
        }
        assertNotNull(booktypes);

    }


} 
