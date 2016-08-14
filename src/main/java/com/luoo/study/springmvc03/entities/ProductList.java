package com.luoo.study.springmvc03.entities;

import java.util.List;

/**
 * @author Luoo2
 * @create 2016-08-14 22:09
 */

public class ProductList {
    private List<Product> items;

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }
}
