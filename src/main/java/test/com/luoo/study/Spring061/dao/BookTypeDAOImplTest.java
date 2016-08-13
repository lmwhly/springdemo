package test.com.luoo.study.Spring061.dao;

import com.luoo.study.Spring061.dao.BookTypeDAOImpl;
import com.luoo.study.Spring061.entities.BookType;
import com.luoo.study.Spring061.mapping.BookTypeDAO;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;

/**
 * BookTypeDAOImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>八月 12, 2016</pre>
 */
public class BookTypeDAOImplTest {

    static BookTypeDAO bookTypeDao;

    @BeforeClass
    public static void before() throws Exception {

        bookTypeDao = new BookTypeDAOImpl();
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
        List<BookType> booktypes = bookTypeDao.getAllBookTypes();
        for (BookType bookType : booktypes) {
            System.out.println(bookType+"\t"+bookType.getTypeName());
        }
        assertNotNull(booktypes);

    }


} 
