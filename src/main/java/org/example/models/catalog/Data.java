package org.example.models.catalog;

import java.util.ArrayList;
import java.util.List;


public class Data {
    private List<Product> products = new ArrayList<Product>();
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
