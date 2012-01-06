package com.ebaytools.gui.dialog;

import com.ebaytools.gui.linteners.SaveProductListener;
import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.GraphPaperLayout;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Product;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CreateOrEditProductDialog extends JDialog {
    private JDialog dialog;
    private Long productId;
    private Data data;

    public CreateOrEditProductDialog(JFrame main, Long productId, Data data) {
        dialog = this;
        this.data = data;
        this.productId = productId;
        dialog.setTitle(productId == null ? "Create product" : "Edit product");
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        dialog.add(buildCreatePanel(), BorderLayout.CENTER);
        dialog.pack();
        dialog.setSize(350, 150);
        dialog.setLocationRelativeTo(main);
        dialog.setVisible(true);
    }

    private JPanel buildCreatePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GraphPaperLayout(new Dimension(10, 5), 1, 1));
        panel.add(new JLabel("Reference ID : "), new Rectangle(1, 0, 4, 1));
        JTextField referenceId = new JTextField();
        panel.add(referenceId, new Rectangle(5, 0, 4, 1));
        panel.add(new JLabel("Name : "), new Rectangle(1, 1, 4, 1));
        JTextField name = new JTextField();
        panel.add(name, new Rectangle(5, 1, 4, 1));
        JButton save = new JButton("Save");
        panel.add(save, new Rectangle(4, 3, 3, 1));
        if (productId != null) {
            Product product = ManagerDAO.getInstance().getProductDAO().find(productId);
            referenceId.setText(product.getReferenceId());
            name.setText(product.getName());
        }
        save.addActionListener(new SaveProductListener(dialog, productId, referenceId, name, data));
        return panel;
    }
}

