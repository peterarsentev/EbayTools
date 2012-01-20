package com.ebaytools.gui.linteners;

import com.ebaytools.gui.dialog.CreateOrEditFilterDialog;
import com.ebaytools.gui.model.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenFilterActionListener implements ActionListener {
    private JFrame frame;
    private Data data;

    public OpenFilterActionListener(JFrame frame, Data data) {
        this.frame = frame;
        this.data = data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CreateOrEditFilterDialog createOrEditFilterDialog = new CreateOrEditFilterDialog(frame, null, data);
        createOrEditFilterDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}
