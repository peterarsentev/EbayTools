package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.Item;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

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
    public List<String> getItemEbayIdByProductId(Long productId) {
        return getHibernateTemplate().find("select item.ebayItemId from com.ebaytools.kernel.entity.Item as item where item.productId=?", productId);
    }
}
