package com.luoo.study.Spring051.ioc02;

/**
 * @author Luoo
 * @create 2016-08-11 9:28
 */

import org.springframework.stereotype.Component;

/**
 * 图书数据访问实现类
 */
@Component("bookdaoObj")
public class BookDAO implements IBookDAO {
    @Override
    public String addBook(String bookname) {
        return "添加图书"+bookname+"成功！";
    }
}
