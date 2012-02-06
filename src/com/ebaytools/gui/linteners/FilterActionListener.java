package com.ebaytools.gui.linteners;

import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Filter;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.ItemProperties;
import com.ebaytools.kernel.entity.SystemSetting;
import com.ebaytools.util.Fields;
import com.ebaytools.util.FormatterText;
import com.ebaytools.util.TextUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
        SystemSetting setting = ManagerDAO.getInstance().getSystemSettingDAO().getSystemSettingByName(Fields.APPLY_FILTER.getKey());
        if (setting == null) {
            JOptionPane.showMessageDialog(main, "First of all you must apply filter!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Filter filter = ManagerDAO.getInstance().getFilterDAO().find(Long.valueOf(setting.getValue()));
            StringBuilder sb = new StringBuilder();
            List<Object[]> selectData = data.getProductTable().getDataSelect();
            Long productId = null;
            if (selectData.size() > 1) {
                JOptionPane.showMessageDialog(main, "You must select only one product or nothing!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!selectData.isEmpty()) {
                productId = (Long) selectData.get(0)[1];
            }
            List<Item> items = ManagerDAO.getInstance().getItemDAO().getProductByFilter(filter, productId);
            List<String> showField = ManagerDAO.getInstance().getSystemSettingDAO().getShowFieldsValue();
            sb.append(FormatterText.formatShowFields(items, showField));
            data.getText().setText(data.getText().getText() + sb.toString());
        }
    }
}
