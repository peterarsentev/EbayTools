package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.ItemProperties;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ItemPropetiesDAOImpl extends HibernateDaoSupport implements ItemPropetiesDAO {
    @Override
    public Long create(ItemProperties itemProperties) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(ItemProperties itemProperties) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Long id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ItemProperties find(Long id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
