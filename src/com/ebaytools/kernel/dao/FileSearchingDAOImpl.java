package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.FileSearching;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.Calendar;
import java.util.List;

public class FileSearchingDAOImpl extends HibernateDaoSupport implements FileSearchingDAO {
    @Override
    public Long create(FileSearching fileSearching) {
        return (Long) getHibernateTemplate().save(fileSearching);
    }

    @Override
    public synchronized void update(FileSearching fileSearching) {
        getHibernateTemplate().update(fileSearching);
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

    @Override
    public List<FileSearching> getFileSearchingCurrentTime() {
        return getHibernateTemplate().find("from " + FileSearching.class.getName() + " as fs where fs.runTime<=?", Calendar.getInstance());
    }

    @Override
    public synchronized void updateRunTime(FileSearching fileSearch) {
        Calendar cal =  Calendar.getInstance();
        cal.add(Calendar.MINUTE, fileSearch.getTimeInterval());
        fileSearch.setRunTime(cal);
        update(fileSearch);
    }
}
