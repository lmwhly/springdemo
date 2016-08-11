package com.luoo.study.Spring051.ioc01;

/**
 * @author Luoo
 * @create 2016-08-11 9:28
 */

public class BookDAO implements IBookDAO {
    @Override
    public String addBook(String bookname) {
        return "添加图书"+bookname+"成功！";
    }
}
