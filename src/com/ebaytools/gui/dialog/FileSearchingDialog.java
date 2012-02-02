package com.ebaytools.gui.dialog;

import com.ebaytools.gui.linteners.AddfileActionListener;
import com.ebaytools.gui.linteners.OpenDialogFileSearchingListener;
import com.ebaytools.gui.linteners.RefreshTableListenter;
import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.GraphPaperLayout;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.FileSearching;
import com.ebaytools.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FileSearchingDialog extends JDialog {
    private Data data;
    private JDialog dialog;
    private JFrame main;

    public FileSearchingDialog(JFrame main, Data data) {
        this.main = main;
        dialog = this;
        dialog.setTitle("Files searching");
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
        java.util.List<FileSearching> fileSearching = ManagerDAO.getInstance().getFileSearchingDAO().getAllFileSearching();
        FileSearchingDataImpl fileSearchingData = new FileSearchingDataImpl(fileSearching);
        TableModelCheckBox fileSearchingModelTable = new TableModelCheckBox(fileSearchingData);
        JTable fileSearchingTable =  new JTable(fileSearchingModelTable);
        TableCheckBox.buildTable(fileSearchingTable);
        panel.add(new JScrollPane(fileSearchingTable), new Rectangle(0, 0, 12, 15));
        RefreshTableListenter refresAction = new RefreshTableListenter(fileSearchingTable, fileSearchingModelTable, RefreshTableListenter.TypeTable.FILE_SEARCHING);
        data.setRefresFileSearchingTable(refresAction);
        JButton create = new JButton("Add");
        JButton delete = new JButton("Delete");
        panel.add(create, new Rectangle(12, 0, 3, 1));
        panel.add(delete, new Rectangle(12, 1, 3, 1));
        create.addActionListener(new OpenDialogFileSearchingListener(main, data));
        delete.addActionListener(new DeleteFileSearchingActionListener(main, data, fileSearchingModelTable));
        return panel;
    }


    private class DeleteFileSearchingActionListener implements ActionListener {
        private JFrame main;
        private Data data;
        private TableModelCheckBox model;

        private DeleteFileSearchingActionListener(JFrame main, Data data, TableModelCheckBox model) {
            this.main = main;
            this.data = data;
            this.model = model;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            java.util.List<Object[]> objects = model.getDataSelect();
            if (objects.isEmpty()) {
                JOptionPane.showMessageDialog(main, "You must select at least one filter for Deleting", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure to detele it?");
                if (response == 0) {
                    for (Object[] object : objects) {
                        ManagerDAO.getInstance().getFileSearchingDAO().delete((Long) object[1]);
                    }
                }
                data.getRefresFileSearchingTable().actionPerformed(null);
            }
        }
    }
}
