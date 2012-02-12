package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.SystemSetting;
import com.ebaytools.util.Fields;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.List;

public class SystemSettingDAOImpl extends HibernateDaoSupport implements SystemSettingDAO {
    @Override
    public Long create(SystemSetting systemSetting) {
        ManagerDAO.lock.writeLock().lock();
        try {
            return (Long) getHibernateTemplate().save(systemSetting);
        } finally {
            ManagerDAO.lock.writeLock().unlock();
        }
    }

    @Override
    public void update(SystemSetting systemSetting) {
        ManagerDAO.lock.writeLock().lock();
        try {
            getHibernateTemplate().update(systemSetting);
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
    public SystemSetting find(Long id) {
        ManagerDAO.lock.readLock().lock();
        try {
            return getHibernateTemplate().get(SystemSetting.class, id);
        } finally {
            ManagerDAO.lock.readLock().unlock();
        }
    }

    @Override
    public List<String> getChooseOptsValue() {
        ManagerDAO.lock.readLock().lock();
        try {
            return getHibernateTemplate().find("select sysSet.value from com.ebaytools.kernel.entity.SystemSetting as sysSet where sysSet.name=?", Fields.CHOSE_OPTIONS.getKey());
        } finally {
            ManagerDAO.lock.readLock().unlock();
        }
    }

    @Override
    public List<SystemSetting> getChooseOpts() {
        ManagerDAO.lock.readLock().lock();
        try {
            return getHibernateTemplate().find("from com.ebaytools.kernel.entity.SystemSetting as sysSet where sysSet.name=?", Fields.CHOSE_OPTIONS.getKey());
        } finally {
            ManagerDAO.lock.readLock().unlock();
        }
    }

    @Override
    public List<SystemSetting> getSystemSettingByName(String name) {
        ManagerDAO.lock.readLock().lock();
        try {
            return getHibernateTemplate().find("from com.ebaytools.kernel.entity.SystemSetting as sysSet where sysSet.name=?", name);
        } finally {
            ManagerDAO.lock.readLock().unlock();
        }
    }

    @Override
    public List<SystemSetting> getShowFields() {
        ManagerDAO.lock.readLock().lock();
        try {
            return getHibernateTemplate().find("from " + SystemSetting.class.getName() + " as sysSet where sysSet.name=?", Fields.SHOW_FIELDS.getKey());
        } finally {
            ManagerDAO.lock.readLock().unlock();
        }
    }

    @Override
    public List<String> getSystemsValue(String field) {
        ManagerDAO.lock.readLock().lock();
        try {
            return getHibernateTemplate().find("select sysSet.value from " + SystemSetting.class.getName() + " as sysSet where sysSet.name=?", field);
        } finally {
            ManagerDAO.lock.readLock().unlock();
        }
    }
}
