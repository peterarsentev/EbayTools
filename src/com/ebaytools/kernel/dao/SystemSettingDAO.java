package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.SystemSetting;

import java.util.List;

public interface SystemSettingDAO extends CrUD<SystemSetting> {
    public List<String> getChooseOptsValue();
    public List<SystemSetting> getChooseOpts();
}
