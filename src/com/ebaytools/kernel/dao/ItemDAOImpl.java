package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.Item;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ItemDAOImpl extends HibernateDaoSupport implements ItemDAO {
    @Override
    public Long create(Item item) {
        return (Long) getHibernateTemplate().save(item);
    }

    @Override
    public void update(Item item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Item find(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Item> getItemsByProductId(Long productId) {
        return getHibernateTemplate().find("from com.ebaytools.kernel.entity.Item as item where item.productId=?", productId);
    }

    @Override
    public Map<String, Item> getItemEbayIdByProductId(Long productId) {
        Map<String, Item> map = new LinkedHashMap<String, Item>();
        for (Item item : getItemsByProductId(productId)) {
            map.put(item.getEbayItemId(), item);
        }
        return map;
    }
}
