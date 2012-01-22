package com.ebaytools.gui.linteners;

import com.ebaytools.gui.dialog.FileSearchingDialog;
import com.ebaytools.gui.dialog.FilterDialog;
import com.ebaytools.gui.model.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenFileSearchingDialogAcitonListener implements ActionListener {
    private JFrame frame;
    private Data data;

    public OpenFileSearchingDialogAcitonListener(JFrame frame, Data data) {
        this.frame = frame;
        this.data = data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileSearchingDialog searchingDialog = new FileSearchingDialog(frame, data);
        searchingDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}
