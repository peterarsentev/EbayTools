package com.ebaytools.kernel.dao;

import com.ebay.services.finding.SearchItem;
import com.ebaytools.kernel.entity.Product;
import com.ebaytools.util.Pair;

import java.util.List;
import java.util.Map;

public interface ProductDAO extends CrUD<Product> {
    public List<Product> getAllProduct();
    public List<Object[]> getProducts();
    public Product findProductByReferenceId(String referenceId);
    public void create(Map<Pair, Map<SearchItem, Boolean>> map);
}
