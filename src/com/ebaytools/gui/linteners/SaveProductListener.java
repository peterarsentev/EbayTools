package com.ebaytools.gui.linteners;

import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveProductListener implements ActionListener {
    private JDialog dialog;
    private JTextField referenceId;
    private JTextField name;
    private Long productId;
    private Data data;

    public SaveProductListener(JDialog dialog, Long productId, JTextField referenceId, JTextField name, Data data) {
        this.name = name;
        this.referenceId = referenceId;
        this.dialog = dialog;
        this.productId = productId;
        this.data = data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Product product = new Product();
        product.setName(name.getText());
        product.setReferenceId(referenceId.getText());
        if (productId == null) {
            ManagerDAO.getInstance().getProductDAO().create(product);
        } else {
            product.setId(productId);
            ManagerDAO.getInstance().getProductDAO().update(product);
        }
        data.getRefreshAction().actionPerformed(null);
        dialog.setVisible(false);
        dialog.dispose();
    }
}
