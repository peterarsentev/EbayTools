package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.FilterValue;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class FilterValueDAOImpl extends HibernateDaoSupport implements FilterValueDAO {
    @Override
    public Long create(FilterValue filterValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(FilterValue filterValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FilterValue find(Long id) {
        throw new UnsupportedOperationException();
    }
}
