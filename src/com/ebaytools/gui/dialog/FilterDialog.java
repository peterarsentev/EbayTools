package com.ebaytools.gui.dialog;

import com.ebaytools.gui.linteners.OpenFilterActionListener;
import com.ebaytools.gui.linteners.RefreshTableListenter;
import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.GraphPaperLayout;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Filter;
import com.ebaytools.kernel.entity.SystemSetting;
import com.ebaytools.util.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class FilterDialog extends JDialog {
    private Data data;
    private JDialog dialog;
    private JFrame main;

    public FilterDialog(JFrame main, Data data) {
        this.main = main;
        dialog = this;
        dialog.setTitle("Filters");
        this.data = data;
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        dialog.add(buildChoosePanel(), BorderLayout.CENTER);
        dialog.pack();
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(main);
        dialog.setVisible(true);
    }

    private JPanel buildChoosePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GraphPaperLayout(new Dimension(15, 15), 1, 1));
        List<Filter> filters = ManagerDAO.getInstance().getFilterDAO().getAllFilters();
        FilterDataImpl filterData = new FilterDataImpl(filters);
        TableModelCheckBox filterModelTable = new TableModelCheckBox(filterData);
        JTable filterProduct =  new JTable(filterModelTable);
        TableCheckBox.buildTable(filterProduct);
        panel.add(new JScrollPane(filterProduct), new Rectangle(0, 0, 12, 15));
        JButton create = new JButton("Create");
        JButton delete = new JButton("Delete");
        JButton apply = new JButton("Apply");
        panel.add(create, new Rectangle(12, 0, 3, 1));
        panel.add(delete, new Rectangle(12, 1, 3, 1));
        panel.add(apply, new Rectangle(12, 2, 3, 1));
        RefreshTableListenter refresAction = new RefreshTableListenter(filterProduct, filterModelTable, RefreshTableListenter.TypeTable.FILTER);
        data.setRefresFilterTable(refresAction);
        create.addActionListener(new OpenFilterActionListener(main, data));
        delete.addActionListener(new DeleteFilterActionListener(main, data, filterModelTable));
        apply.addActionListener(new ApplyFilterActionListener(main, data, filterModelTable));
        return panel;
    }

    private class ApplyFilterActionListener implements ActionListener {
        private JFrame main;
        private Data data;
        private TableModelCheckBox model;

        private ApplyFilterActionListener(JFrame main, Data data, TableModelCheckBox model) {
            this.main = main;
            this.data = data;
            this.model = model;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object[]> objects = model.getDataSelect();
            if (objects.isEmpty() || objects.size() > 1) {
                JOptionPane.showMessageDialog(main, "You must select only one filter for Apply", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                SystemSetting setting = ManagerDAO.getInstance().getSystemSettingDAO().getSystemSettingByName(Fields.APPLY_FILTER.getKey());
                if (setting == null) {
                    setting = new SystemSetting();
                    ManagerDAO.getInstance().getSystemSettingDAO().create(setting);
                }
                setting.setName(Fields.APPLY_FILTER.getKey());
                setting.setValue(objects.get(0)[1].toString());
                ManagerDAO.getInstance().getSystemSettingDAO().update(setting);
                data.getButtonFilter().setText("Filter : " + objects.get(0)[2].toString());
                dialog.setVisible(false);
                dialog.dispose();
            }
        }
    }

    private class DeleteFilterActionListener implements ActionListener {
        private JFrame main;
        private Data data;
        private TableModelCheckBox model;

        private DeleteFilterActionListener(JFrame main, Data data, TableModelCheckBox model) {
            this.main = main;
            this.data = data;
            this.model = model;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object[]> objects = model.getDataSelect();
            if (objects.isEmpty()) {
                JOptionPane.showMessageDialog(main, "You must select at least one filter for Deleting", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                for (Object[] object : objects) {
                    ManagerDAO.getInstance().getFilterDAO().delete((Long) object[1]);
                }
                data.getRefresFilterTable().actionPerformed(null);
            }
        }
    }
}