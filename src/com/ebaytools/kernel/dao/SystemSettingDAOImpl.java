package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.SystemSetting;
import com.ebaytools.util.Fields;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

public class SystemSettingDAOImpl extends HibernateDaoSupport implements SystemSettingDAO {
    @Override
    public Long create(SystemSetting systemSetting) {
        return (Long) getHibernateTemplate().save(systemSetting);
    }

    @Override
    public void update(SystemSetting systemSetting) {
        getHibernateTemplate().update(systemSetting);
    }

    @Override
    public void delete(Long id) {
        getHibernateTemplate().delete(find(id));
    }

    @Override
    public SystemSetting find(Long id) {
        return getHibernateTemplate().get(SystemSetting.class, id);
    }

    @Override
    public synchronized List<String> getChooseOptsValue() {
        return getHibernateTemplate().find("select sysSet.value from com.ebaytools.kernel.entity.SystemSetting as sysSet where sysSet.name=?", Fields.CHOSE_OPTIONS.getKey());
    }

    @Override
    public synchronized List<SystemSetting> getChooseOpts() {
        return getHibernateTemplate().find("from com.ebaytools.kernel.entity.SystemSetting as sysSet where sysSet.name=?", Fields.CHOSE_OPTIONS.getKey());
    }

    @Override
    public SystemSetting getSystemSettingByName(String name) {
        List<SystemSetting> setting = getHibernateTemplate().find("from com.ebaytools.kernel.entity.SystemSetting as sysSet where sysSet.name=?", name);
        if (setting.isEmpty()) {
            return null;
        } else {
            if (setting.size() > 1) {
                throw new IllegalStateException("This setting has multi values : " + name);
            } else {
                return setting.get(0);
            }
        }
    }

    @Override
    public List<SystemSetting> getShowFields() {
        return getHibernateTemplate().find("from " + SystemSetting.class.getName() + " as sysSet where sysSet.name=?", Fields.SHOW_FIELDS.getKey());
    }

    @Override
    public List<String> getShowFieldsValue() {
        return getHibernateTemplate().find("select sysSet.value from " + SystemSetting.class.getName() + " as sysSet where sysSet.name=?", Fields.SHOW_FIELDS.getKey());
    }
}
