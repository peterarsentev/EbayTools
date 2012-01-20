package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.FilterConditions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class FilterConditionsDAOImpl extends HibernateDaoSupport implements FilterConditionsDAO {
    @Override
    public Long create(FilterConditions filterConditions) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(FilterConditions filterConditions) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FilterConditions find(Long id) {
        throw new UnsupportedOperationException();
    }
}
