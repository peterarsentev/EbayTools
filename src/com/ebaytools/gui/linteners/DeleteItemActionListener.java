package com.ebaytools.gui.linteners;

import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.SearchPanel;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Item;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DeleteItemActionListener implements ActionListener {
    private Data data;
    public DeleteItemActionListener(SearchPanel panel, Data data) {
        this.data = data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int response = JOptionPane.showConfirmDialog(null, "Are you sure to detele items?");
        if (response == 0) {
            for (List<Item> items : data.getFilterItems().values()) {
                for (Item item : items) {
                    ManagerDAO.getInstance().getItemDAO().delete(item.getId());
                }
            }
        }
    }
}
