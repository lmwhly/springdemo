package com.luoo.study.Spring061.service;

import com.luoo.study.Spring061.entities.BookType;
import com.luoo.study.Spring061.mapping.BookTypeDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Luoo2
 * @create 2016-08-14 0:34
 */

/*
 * 图书类型服务
 */
//@Service
public class BookTypeService {
//    @Resource
    BookTypeDAO bookTypeDAO;


    public List<BookType> getAllBookTypes() {
        System.err.println("一些被省去的业务");
        return bookTypeDAO.getAllBookTypes();
    }

    @Transactional
    public int addDouble(BookType entity1,BookType entity2){
        int rows=0;
        rows+=bookTypeDAO.add(entity1);
        rows+=bookTypeDAO.add(entity2);
        return rows;
    }
}
