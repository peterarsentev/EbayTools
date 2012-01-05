package com.ebaytools.gui.linteners;

import com.ebaytools.gui.dialog.CreateOrEditProductDialog;
import com.ebaytools.kernel.entity.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenProductIDDialogListener implements ActionListener {
    private JFrame frame;

    public OpenProductIDDialogListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CreateOrEditProductDialog productDialog = new CreateOrEditProductDialog(frame, null);
        productDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}
