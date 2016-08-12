package com.luoo.study.Spring061.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @author Luoo
 * @create 2016-08-12 14:17
 */

public abstract class MyBatisUtil {

    //GC不理static
    private static SqlSessionFactory factory = null;

    public static SqlSessionFactory getSqlSessionFactory() {
        if (factory == null) {
            // 获得环境配置文件流
            InputStream config = MyBatisUtil.class.getClassLoader().getResourceAsStream("MyBatisCfg.xml");
            // 创建sql会话工厂
            factory = new SqlSessionFactoryBuilder().build(config);
        }
        return factory;
    }

    //获得会话
    public static SqlSession getSession() {
        return getSqlSessionFactory().openSession(true);
    }

    /**
     * 获得得sql会话
     *
     * @param isAutoCommit 是否自动提交，如果为false则需要sqlSession.commit();rollback();
     * @return sql会话
     */
    public static SqlSession getSession(boolean isAutoCommit) {
        return getSqlSessionFactory().openSession(isAutoCommit);
    }

}