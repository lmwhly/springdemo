package test.com.luoo.study.Spring051.ioc06;

import com.luoo.study.Spring051.ioc06.ApplicationCfg;
import com.luoo.study.Spring051.ioc06.BookService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
    //容器，注解配置应用程序容器，Spring通过反射ApplicationCfg.class初始化容器
    ApplicationContext ctx=new AnnotationConfigApplicationContext(ApplicationCfg.class);

    BookService bookservice = (BookService) ctx.getBean(BookService.class);
    bookservice.storeBook("《Spring MVC权威指南 第一版》");

    /*User user1=ctx.getBean("user1",User.class);
    user1.show();
    User getUser=ctx.getBean("getUser",User.class);
    getUser.show();*/

}


} 
