package com.ebaytools.gui.linteners;

import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.SearchPanel;
import com.ebaytools.jobs.UpdateAuctionClosePriceJob;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.ItemProperties;
import com.ebaytools.util.Fields;
import com.ebaytools.util.FormatterText;
import com.ebaytools.util.SearchUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class CheckItemActionListener implements ActionListener {
    private Data data;
    public CheckItemActionListener(SearchPanel panel, Data data) {
        this.data = data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int response = JOptionPane.showConfirmDialog(null, "Do you really want to check this items?");
        if (response == 0) {
            List<String> showField = ManagerDAO.getInstance().getSystemSettingDAO().getSystemsValue(Fields.SHOW_FIELDS.getKey());
            StringBuilder sb = new StringBuilder();
            for (List<Item> items : data.getFilterItems().values()) {
                for (Item item : items) {
                    sb.append("Old item :\n");
                    FormatterText.buildSingleItem(sb, item, showField);
                    sb.append("Update item :\n");
                    UpdateAuctionClosePriceJob.updateAuctionClosePrice(item);
                    FormatterText.buildSingleItem(sb, item, showField);
                }
            }
            data.getText().setText(data.getText().getText() + sb.toString());
        }
    }
}
