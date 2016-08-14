package com.luoo.study.springmvc03.entities;

import java.util.Map;

/**
 * @author Luoo2
 * @create 2016-08-14 22:11
 */

public class ProductMap {
    private Map<String, Product> items;

    public Map<String, Product> getItems() {
        return items;
    }

    public void setItems(Map<String, Product> items) {
        this.items = items;
    }

}
