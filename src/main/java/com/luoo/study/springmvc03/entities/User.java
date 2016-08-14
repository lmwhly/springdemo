package com.luoo.study.springmvc03.entities;

/**
 * @author Luoo2
 * @create 2016-08-14 22:06
 */

public class User {
    private String username;
    private Product product;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
