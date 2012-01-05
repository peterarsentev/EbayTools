package com.ebaytools.gui.linteners;

import com.ebay.services.finding.SearchItem;
import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.Product;
import com.ebaytools.util.Pair;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class SaveToDbListener implements ActionListener {
    private Data data;
    private JFrame main;

    public SaveToDbListener(JFrame main, Data data) {
        this.data = data;
        this.main = main;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Map<Pair, List<SearchItem>> result = data.getSaveData();
        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(main, "Data is empty!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            for (Map.Entry<Pair, List<SearchItem>> entry : result.entrySet()) {
                Product product = new Product();
                product.setReferenceId(entry.getKey().getKey());
                Long productId = ManagerDAO.getInstance().getProductDAO().create(product);
                Calendar createTime = Calendar.getInstance();
                for (SearchItem searchItem : entry.getValue()) {
                    Item item = new Item();
                    item.setCreateDate(createTime);
                    item.setProductId(productId);
                    item.setEbayItemId(searchItem.getItemId());
                    ManagerDAO.getInstance().getItemDAO().create(item);
                }
            }
        }
    }
}
