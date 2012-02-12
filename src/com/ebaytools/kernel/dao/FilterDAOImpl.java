package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.Filter;
import com.ebaytools.kernel.entity.FilterConditions;
import com.ebaytools.kernel.entity.FilterValue;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;
import java.util.Set;

public class FilterDAOImpl extends HibernateDaoSupport implements FilterDAO {
    @Override
    public Long create(Filter filter) {
        ManagerDAO.lock.writeLock().lock();
        try {
            Session session = getSession();
            Transaction tx = session.beginTransaction();
            Set<FilterConditions> conditionsSet = filter.getConditions();
            filter.setConditions(null);
            Long filterId = (Long) session.save(filter);
            for (FilterConditions conditions : conditionsSet) {
                conditions.setFilterId(filterId);
                Set<FilterValue> filterValues = conditions.getValues();
                conditions.setValues(null);
                Long filterConditionsId = (Long) session.save(conditions);
                for (FilterValue value : filterValues) {
                    value.setFilterConditionsId(filterConditionsId);
                    session.save(value);
                }
            }
            tx.commit();
            session.close();
            return filterId;
        } finally {
            ManagerDAO.lock.writeLock().unlock();
        }
    }

    @Override
    public void update(Filter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        ManagerDAO.lock.writeLock().lock();
        try {
            getHibernateTemplate().delete(find(id));
        } finally {
            ManagerDAO.lock.writeLock().unlock();
        }
    }

    @Override
    public Filter find(Long id) {
        return getHibernateTemplate().get(Filter.class, id);
    }

    @Override
    public List<Filter> getAllFilters() {
        return getHibernateTemplate().find("from com.ebaytools.kernel.entity.Filter");
    }
}
