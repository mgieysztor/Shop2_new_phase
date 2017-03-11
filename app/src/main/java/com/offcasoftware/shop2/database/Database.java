package com.offcasoftware.shop2.database;

import com.offcasoftware.shop2.model.Product;

import java.util.List;

/**
 * @author maciej.pachciarek on 2017-03-06.
 */

public interface Database {

    void saveProducts(List<Product> products);

    List<Product> getProducts();

    Product getProduct(int productId);

    void saveProduct(String name, int price);

}
