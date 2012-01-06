package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.SystemSetting;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

public class SystemSettingDAOImpl extends HibernateDaoSupport implements SystemSettingDAO {
    @Override
    public Long create(SystemSetting systemSetting) {
        return (Long) getHibernateTemplate().save(systemSetting);
    }

    @Override
    public void update(SystemSetting systemSetting) {
        throw new UnsupportedOperationException();
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
    public List<String> getChooseOptsValue() {
        return getHibernateTemplate().find("select sysSet.value from com.ebaytools.kernel.entity.SystemSetting as sysSet where sysSet.name=?", "chooseOpt");
    }

    @Override
    public List<SystemSetting> getChooseOpts() {
        return getHibernateTemplate().find("from com.ebaytools.kernel.entity.SystemSetting as sysSet where sysSet.name=?", "chooseOpt");
    }
}
