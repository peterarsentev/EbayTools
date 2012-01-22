package com.ebaytools.gui.linteners;

import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.FileSearching;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AddfileActionListener implements ActionListener {
    private JFileChooser fc;
    private JDialog main;
    private Data dataModel;

    public AddfileActionListener(JDialog main, Data data) {
        this.fc = new JFileChooser();
        this.main = main;
        this.dataModel = data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int returnVal = fc.showOpenDialog(main);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (file.exists()) {
                FileSearching fileSearching = new FileSearching();
                fileSearching.setPath(file.getAbsolutePath());
                ManagerDAO.getInstance().getFileSearchingDAO().create(fileSearching);
                dataModel.getRefresFileSearchingTable().actionPerformed(null);
            } else {
                JOptionPane.showMessageDialog(main, "Error this file does not exist file : " + file.getAbsolutePath() , "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
