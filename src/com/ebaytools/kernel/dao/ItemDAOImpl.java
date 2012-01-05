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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Long id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Item find(Long id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Item> getItemsByProductId(Long productId) {
        return getHibernateTemplate().find("from com.ebaytools.kernel.entity.Item as item where item.productId=?", productId);
    }
}
