package com.luoo.study.Spring051.ioc02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Luoo
 * @create 2016-08-11 9:23
 */


/**
 * 图书业务类
 */
public class BookService {
   IBookDAO bookDAO;

    public void storeBook(String bookname){

        //容器
        ApplicationContext ctx=new ClassPathXmlApplicationContext("IOCBeans02.xml");
        //从容器中获得id为bookdao的bean
        bookDAO=(IBookDAO)ctx.getBean("bookdaoObj");

        System.out.println("图书上货");
        String result=bookDAO.addBook(bookname);
        System.out.println(result);
    }

}
