package com.ebaytools.gui.linteners;

import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Filter;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.SystemSetting;
import com.ebaytools.util.Fields;
import com.ebaytools.util.FormatterText;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FilterActionListener implements ActionListener {
    private JFrame main;
    private Data data;

    public FilterActionListener(JFrame main, Data data) {
        this.main = main;
        this.data = data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<SystemSetting> settings = ManagerDAO.getInstance().getSystemSettingDAO().getSystemSettingByName(Fields.APPLY_FILTER.getKey());
        if (settings.isEmpty()) {
            JOptionPane.showMessageDialog(main, "First of all you must apply filter!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            for (SystemSetting setting : settings) {
                Filter filter = ManagerDAO.getInstance().getFilterDAO().find(Long.valueOf(setting.getValue()));
                StringBuilder sb = new StringBuilder();
                sb.append("Filter : " + filter.getName() + "\n");
                List<Object[]> selectData = data.getProductTable().getDataSelect();
                List<Long> prs = new ArrayList<Long>();
                if (!selectData.isEmpty()) {
                    for (Object[] object : selectData) {
                        prs.add((Long) object[1]);
                    }
                }
                if (!selectData.isEmpty()) {
                    sb.append("For reference ids : ");
                    for (Object[] object : selectData) {
                        sb.append(object[2]).append(";");
                    }
                    sb.append("\n");
                }
                List<Item> items = ManagerDAO.getInstance().getItemDAO().getProductByFilter(filter, prs);
                List<String> showField = ManagerDAO.getInstance().getSystemSettingDAO().getSystemsValue(Fields.SHOW_FIELDS.getKey());
                sb.append(FormatterText.formatShowFields(items, showField)).append("\n");
                data.getText().setText(data.getText().getText() + sb.toString());
            }
        }
    }
}
