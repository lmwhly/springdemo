package com.luoo.study.springmvc04.entities;

/**
 * @author Luoo2
 * @create 2016-08-15 0:39
 */

public class ProductType {
    private int id;
    private String name;

    public ProductType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
