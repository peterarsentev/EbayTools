package com.ebaytools.gui.linteners;

import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.SystemSetting;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CollectChooseOptsListener implements ActionListener {
    private Data data;
    private List<JCheckBox> checkBoxes;
    private JDialog dialog;

    public CollectChooseOptsListener(JDialog dialog, Data data, List<JCheckBox> checkBoxes) {
        this.data = data;
        this.checkBoxes = checkBoxes;
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<SystemSetting> settings = ManagerDAO.getInstance().getSystemSettingDAO().getChooseOpts();
        for (JCheckBox box : checkBoxes) {
            if (box.isSelected()) {
                SystemSetting setting = checkOpt(settings, box.getText());
                if (setting == null) {
                    SystemSetting systemSetting = new SystemSetting();
                    systemSetting.setName("chooseOpt");
                    systemSetting.setValue(box.getText());
                    ManagerDAO.getInstance().getSystemSettingDAO().create(systemSetting);
                } else {
                    settings.remove(setting);
                }
            }
        }
        for (SystemSetting setting : settings) {
            ManagerDAO.getInstance().getSystemSettingDAO().delete(setting.getId());
        }
        dialog.setVisible(false);
        dialog.dispose();
    }

    private static SystemSetting checkOpt(List<SystemSetting> settings, String value) {
        for (SystemSetting setting : settings) {
            if (setting.getValue().equals(value)) {
                return setting;
            }
        }
        return null;
    }
}
