package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.ItemProperties;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

public class ItemPropertiesDAOImpl extends HibernateDaoSupport implements ItemPropertiesDAO {
    @Override
    public Long create(ItemProperties itemProperties) {
        ManagerDAO.lock.writeLock().lock();
        try {
            return (Long) getHibernateTemplate().save(itemProperties);
        } finally {
            ManagerDAO.lock.writeLock().unlock();
        }
    }

    @Override
    public List<ItemProperties> getItemPropetiesForItemId(Long itemId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(ItemProperties itemProperties) {
        ManagerDAO.lock.writeLock().lock();
        try {
            getHibernateTemplate().update(itemProperties);
        } finally {
            ManagerDAO.lock.writeLock().unlock();
        }
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
