package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.ItemProperties;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

public class ItemPropertiesDAOImpl extends HibernateDaoSupport implements ItemPropertiesDAO {
    @Override
    public Long create(ItemProperties itemProperties) {
        return (Long) getHibernateTemplate().save(itemProperties);
    }

    @Override
    public List<ItemProperties> getItemPropetiesForItemId(Long itemId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(ItemProperties itemProperties) {
        getHibernateTemplate().update(itemProperties);
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ItemProperties find(Long id) {
        throw new UnsupportedOperationException();
    }
}
