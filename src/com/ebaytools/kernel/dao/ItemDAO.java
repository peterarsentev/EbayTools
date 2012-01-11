package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.Item;

import java.util.List;
import java.util.Map;

public interface ItemDAO extends CrUD<Item> {
    public List<Item> getItemsByProductId(Long productId);
    public Map<String, Item> getItemEbayIdByProductId(Long productId);
}
