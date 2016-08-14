package com.luoo.study.Spring061.mapping;

import com.luoo.study.Spring061.entities.BookType;

import java.util.List;

/**
 * @author Luoo
 * @create 2016-08-12 14:08
 */

public interface BookTypeDAO {

    /*
     * 获得所有图书类型
     */
    public List<BookType> getAllBookTypes();

    /**
     * 添加新的图书类型
     */public int add(BookType entity);

}
