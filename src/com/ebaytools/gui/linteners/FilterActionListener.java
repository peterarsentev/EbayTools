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
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
import org.hibernate.dialect.DataDirectOracle9Dialect;

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
            List<Item> items = ManagerDAO.getInstance().getItemDAO().getProductByFilter(filter);
            for (Item item : items) {
                sb.append("itemID : ").append(item.getEbayItemId()).append("\t\t").append(FormatterText.dateformatter.format(item.getCreateDate().getTime())).append("\n");
                List<ItemProperties> list = new ArrayList<ItemProperties>(item.getProperties());
                Collections.sort(list);
                for (ItemProperties properties : list) {
                    String value = properties.getValue();
                    if (Fields.AUCTION_CLOSE_TIME.getKey().equals(properties.getName()) && TextUtil.isNotNull(value)) {
                        value = FormatterText.dateformatter.format(new Date(Long.parseLong(value)));
                    }
                    sb.append(properties.getName()).append(" : ").append(value).append("\n");
                }
                sb.append("\n");
            }
            sb.append("Total items : ").append(items.size()).append("\n");
            data.getText().setText(data.getText().getText() + sb.toString());
        }
    }
}
