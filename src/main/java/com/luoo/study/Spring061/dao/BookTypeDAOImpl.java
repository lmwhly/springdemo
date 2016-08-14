package com.luoo.study.Spring061.dao;

/**
 * @author Luoo
 * @create 2016-08-12 14:21
 */

import com.luoo.study.Spring061.entities.BookType;
import com.luoo.study.Spring061.mapping.BookTypeDAO;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * 实现图书类型数据访问
 */
public class BookTypeDAOImpl implements BookTypeDAO {

    @Override
    public List<BookType> getAllBookTypes() {
        //获得会话对象
        SqlSession session = MyBatisUtil.getSession();
        try {
            //通过MyBatis实现接口BookTypeDAO，返回实例
            BookTypeDAO bookTypeDAO = session.getMapper(BookTypeDAO.class);
            return bookTypeDAO.getAllBookTypes();
        } finally {
            session.close();
        }
    }

    @Override
    public int add(BookType entity) {
        return 0;
    }

}