package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.Product;

import java.util.List;

public interface ProductDAO extends CrUD<Product> {
    public List<Product> getAllProduct();
    public List<Object[]> getProducts();
}
