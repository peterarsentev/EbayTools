package com.ebaytools.gui.linteners;

import com.ebaytools.gui.dialog.FilterDialog;
import com.ebaytools.gui.dialog.ShowFieldsDialog;
import com.ebaytools.gui.model.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenDialogShowFieldsActionListener implements ActionListener {
    private JFrame main;
    private Data data;

    public OpenDialogShowFieldsActionListener(JFrame main, Data data) {
        this.main = main;
        this.data = data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ShowFieldsDialog dialog = new ShowFieldsDialog(main, data);
        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}
