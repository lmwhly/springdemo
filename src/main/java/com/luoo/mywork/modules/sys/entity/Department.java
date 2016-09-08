package com.luoo.mywork.modules.sys.entity;

import com.luoo.mywork.common.persistence.BaseEntity;

/**
 * @author Luoo
 * @create 2016-09-08 17:29
 */

public class Department extends BaseEntity<User> {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void preInsert() {

    }

    @Override
    public void preUpdate() {

    }
}
