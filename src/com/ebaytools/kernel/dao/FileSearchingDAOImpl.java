package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.FileSearching;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.Calendar;
import java.util.List;

public class FileSearchingDAOImpl extends HibernateDaoSupport implements FileSearchingDAO {
    @Override
    public Long create(FileSearching fileSearching) {
        ManagerDAO.lock.writeLock().lock();
        try {
            return (Long) getHibernateTemplate().save(fileSearching);
        } finally {
            ManagerDAO.lock.writeLock().unlock();
        }
    }

    @Override
    public void update(FileSearching fileSearching) {
        ManagerDAO.lock.writeLock().lock();
        try {
            getHibernateTemplate().update(fileSearching);
        } finally {
            ManagerDAO.lock.writeLock().unlock();
        }
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
    public FileSearching find(Long id) {
        return getHibernateTemplate().get(FileSearching.class, id);
    }

    @Override
    public List<FileSearching> getAllFileSearching() {
        return getHibernateTemplate().find("from " + FileSearching.class.getName());
    }

    @Override
    public List<FileSearching> getFileSearchingCurrentTime() {
        return getHibernateTemplate().find("from " + FileSearching.class.getName() + " as fs where fs.runTime<=?", Calendar.getInstance());
    }

    @Override
    public void updateRunTime(FileSearching fileSearch) {
        ManagerDAO.lock.writeLock().lock();
        try {
            Calendar cal =  Calendar.getInstance();
            cal.add(Calendar.MINUTE, fileSearch.getTimeInterval());
            fileSearch.setRunTime(cal);
            getHibernateTemplate().update(fileSearch);
        } finally {
            ManagerDAO.lock.writeLock().unlock();
        }
    }
}
