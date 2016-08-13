package com.luoo.study.Spring061.entities;

/**
 * @author Luoo
 * @create 2016-08-12 14:06
 */

public class BookType {

    /**
     * 编号
     */private int id;
    /**
     * 类型名
     */private String typeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
