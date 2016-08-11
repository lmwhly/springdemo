package test.com.luoo.study.Spring051.ioc01;

import com.luoo.study.Spring051.ioc01.BookService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** 
* BookService Tester. 
* 
* @author <Authors name> 
* @since <pre>八月 11, 2016</pre> 
* @version 1.0 
*/ 
public class BookServiceTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: storeBook(String bookname) 
* 
*/ 
@Test
public void testStoreBook() throws Exception { 
//TODO: Test goes here...
    BookService bookservice=new BookService();
    bookservice.storeBook("《Spring MVC权威指南 第一版》");

} 


} 
