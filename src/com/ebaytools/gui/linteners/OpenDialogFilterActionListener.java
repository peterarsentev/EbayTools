package com.ebaytools.gui.linteners;

import com.ebaytools.gui.dialog.FilterDialog;
import com.ebaytools.gui.model.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenDialogFilterActionListener implements ActionListener {
    private JFrame frame;
    private Data data;

    public OpenDialogFilterActionListener(JFrame frame, Data data) {
        this.frame = frame;
        this.data = data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FilterDialog filterDialog = new FilterDialog(frame, data);
        filterDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}
