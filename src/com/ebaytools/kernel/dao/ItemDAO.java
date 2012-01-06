package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.Item;

import java.util.List;

public interface ItemDAO extends CrUD<Item> {
    public List<Item> getItemsByProductId(Long productId);
    public List<String> getItemEbayIdByProductId(Long productId);
}
