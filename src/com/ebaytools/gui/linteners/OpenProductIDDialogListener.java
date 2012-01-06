package com.ebaytools.gui.linteners;

import com.ebaytools.gui.dialog.CreateOrEditProductDialog;
import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.entity.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenProductIDDialogListener implements ActionListener {
    private JFrame frame;
    private Data data;

    public OpenProductIDDialogListener(JFrame frame, Data data) {
        this.frame = frame;
        this.data = data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CreateOrEditProductDialog productDialog = new CreateOrEditProductDialog(frame, null, data);
        productDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}
