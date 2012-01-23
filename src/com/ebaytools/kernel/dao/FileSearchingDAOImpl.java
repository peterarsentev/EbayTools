package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.FileSearching;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

public class FileSearchingDAOImpl extends HibernateDaoSupport implements FileSearchingDAO {
    @Override
    public Long create(FileSearching fileSearching) {
        return (Long) getHibernateTemplate().save(fileSearching);
    }

    @Override
    public void update(FileSearching fileSearching) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        getHibernateTemplate().delete(find(id));
    }

    @Override
    public FileSearching find(Long id) {
        return getHibernateTemplate().get(FileSearching.class, id);
    }

    @Override
    public synchronized List<FileSearching> getAllFileSearching() {
        return getHibernateTemplate().find("from " + FileSearching.class.getName());
    }
}
