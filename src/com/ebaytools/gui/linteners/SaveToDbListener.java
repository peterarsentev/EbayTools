package com.ebaytools.gui.linteners;

import com.ebay.services.finding.SearchItem;
import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.ItemProperties;
import com.ebaytools.kernel.entity.Product;
import com.ebaytools.util.Fields;
import com.ebaytools.util.FormatterText;
import com.ebaytools.util.Pair;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class SaveToDbListener implements ActionListener {
    private Data data;
    private JFrame main;

    public SaveToDbListener(JFrame main, Data data) {
        this.data = data;
        this.main = main;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Map<Pair, Map<SearchItem, Boolean>> result = data.getSaveData();
        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(main, "Data is empty!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            ManagerDAO.getInstance().getProductDAO().create(result);
            data.getRefreshAction().actionPerformed(null);
        }
    }
}
