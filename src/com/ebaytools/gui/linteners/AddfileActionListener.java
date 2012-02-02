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
    private JLabel label;

    public AddfileActionListener(JDialog main, JLabel label) {
        this.fc = new JFileChooser();
        this.main = main;
        this.label = label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int returnVal = fc.showOpenDialog(main);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (file.exists()) {
                label.setText(file.getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(main, "Error this file does not exist file : " + file.getAbsolutePath() , "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
