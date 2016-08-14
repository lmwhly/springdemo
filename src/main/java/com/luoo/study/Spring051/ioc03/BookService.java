package com.luoo.study.Spring051.ioc03;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Luoo
 * @create 2016-08-11 9:23
 */


/**
 * 图书业务类
 */
public class BookService {
    @Autowired
    IBookDAO bookDAO;

    public void storeBook(String bookname) {

        System.out.println("图书上货");
        String result = bookDAO.addBook(bookname);
        System.out.println(result);
    }

}
