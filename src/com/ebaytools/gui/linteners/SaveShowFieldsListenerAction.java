package com.ebaytools.gui.linteners;

import com.ebaytools.gui.dialog.ShowFieldsDialog;
import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.SystemSetting;
import com.ebaytools.util.Fields;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SaveShowFieldsListenerAction implements ActionListener {
    private Data data;
    private List<ShowFieldsDialog.CheckBoxID> checkBoxes;
    private JDialog dialog;

    public SaveShowFieldsListenerAction(JDialog dialog, Data data, List<ShowFieldsDialog.CheckBoxID> checkBoxes) {
        this.data = data;
        this.checkBoxes = checkBoxes;
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<SystemSetting> settings = ManagerDAO.getInstance().getSystemSettingDAO().getShowFields();
        for (ShowFieldsDialog.CheckBoxID box : checkBoxes) {
            if (box.isSelected()) {
                SystemSetting setting = checkOpt(settings, box.getId());
                if (setting == null) {
                    SystemSetting systemSetting = new SystemSetting();
                    systemSetting.setName(Fields.SHOW_FIELDS.getKey());
                    systemSetting.setValue(box.getId());
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
