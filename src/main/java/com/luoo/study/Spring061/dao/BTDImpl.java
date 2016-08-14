package com.luoo.study.Spring061.dao;

import com.luoo.study.Spring061.entities.BookType;
import com.luoo.study.Spring061.mapping.BookTypeDAO;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author Luoo2
 * @create 2016-08-14 0:04
 */

//@Repository
public class BTDImpl implements BookTypeDAO {
//    @Resource
    private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }


    @Override
    public List<BookType> getAllBookTypes() {
        return sqlSession.selectList("com.luoo.study.Spring061.mapping.BookTypeDAO.getAllBookTypes");
    }

    @Override
    public int add(BookType entity) {
        return 0;
    }
}
