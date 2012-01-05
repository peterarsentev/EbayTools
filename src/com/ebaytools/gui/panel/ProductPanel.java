package com.ebaytools.gui.panel;

import com.ebaytools.gui.dialog.CreateOrEditProductDialog;
import com.ebaytools.gui.linteners.OpenProductIDDialogListener;
import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.Product;
import com.ebaytools.util.ProductDataImpl;
import com.ebaytools.util.SearchUtil;
import com.ebaytools.util.TableCheckBox;
import com.ebaytools.util.TableModelCheckBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ProductPanel extends JPanel {
    private Data data;
    private JFrame main;

    public ProductPanel(JFrame main, Data data) {
        this.main = main;
        this.data = data;
        JPanel panel = this;
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GraphPaperLayout(new Dimension(16, 20), 1, 1));
        java.util.List<Product> productList = ManagerDAO.getInstance().getProductDAO().getAllProduct();
        ProductDataImpl productData = new ProductDataImpl(productList);
        TableModelCheckBox productModelTable = new TableModelCheckBox(productData);
        JTable tableProduct = TableCheckBox.buildTable(productModelTable);
        panel.add(new JScrollPane(tableProduct), new Rectangle(0, 0, 12, 16));
        JButton createProduct = new JButton("Create");
        JButton editProduct = new JButton("Edit");
        JButton deleteProduct = new JButton("Delete");
        JButton refreshTable = new JButton("Refresh");
        JButton searchItem = new JButton("Search");
        JButton showItems = new JButton("Items");
        panel.add(createProduct, new Rectangle(12, 0, 4, 1));
        panel.add(editProduct, new Rectangle(12, 1, 4, 1));
        panel.add(deleteProduct, new Rectangle(12, 2, 4, 1));
        panel.add(refreshTable, new Rectangle(12, 3, 4, 1));
        panel.add(searchItem, new Rectangle(12, 4, 4, 1));
        panel.add(showItems, new Rectangle(12, 5, 4, 1));
        createProduct.addActionListener(new OpenProductIDDialogListener(main));
        editProduct.addActionListener(new UpdateProductListenter(main, productModelTable));
        deleteProduct.addActionListener(new DeleteProductListenter(main, productModelTable));
        refreshTable.addActionListener(new RefreshTableListenter(tableProduct));
        searchItem.addActionListener(new SearchListenter(main, productModelTable));
        showItems.addActionListener(new ShowItemsListenter(main, productModelTable));
    }

    private class DeleteProductListenter implements ActionListener {
        private TableModelCheckBox productModelTable;
        private JFrame main;

        private DeleteProductListenter(JFrame main, TableModelCheckBox productModelTable) {
            this.main = main;
            this.productModelTable = productModelTable;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object[]> selectData = productModelTable.getDataSelect();
            if (selectData.isEmpty()) {
                JOptionPane.showMessageDialog(main, "You must select at last one product", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                for (Object[] object : selectData) {
                    ManagerDAO.getInstance().getProductDAO().delete((Long) object[1]);
                }
            }
        }
    }

    private class RefreshTableListenter implements ActionListener {
        private JTable tableProduct;

        private RefreshTableListenter(JTable tableProduct) {
            this.tableProduct = tableProduct;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Product> productList = ManagerDAO.getInstance().getProductDAO().getAllProduct();
            ProductDataImpl productData = new ProductDataImpl(productList);
            tableProduct.setModel(new TableModelCheckBox(productData));
            tableProduct.updateUI();
            TableModelCheckBox.resizeColumnsByName(tableProduct);
        }
    }

    private class UpdateProductListenter implements ActionListener {
        private TableModelCheckBox productModelTable;
        private JFrame main;

        private UpdateProductListenter(JFrame main, TableModelCheckBox productModelTable) {
            this.main = main;
            this.productModelTable = productModelTable;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object[]> selectData = productModelTable.getDataSelect();
            if (selectData.isEmpty() || selectData.size() > 1) {
                JOptionPane.showMessageDialog(main, "You must select only one product", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                CreateOrEditProductDialog productDialog = new CreateOrEditProductDialog(main, (Long) selectData.get(0)[1]);
                productDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        }
    }

    private class SearchListenter implements ActionListener {
        private TableModelCheckBox productModelTable;
        private JFrame main;

        private SearchListenter(JFrame main, TableModelCheckBox productModelTable) {
            this.main = main;
            this.productModelTable = productModelTable;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object[]> selectData = productModelTable.getDataSelect();
            if (selectData.isEmpty()) {
                JOptionPane.showMessageDialog(main, "You must select at last one product", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                List<String> loadId = new ArrayList<String>();
                for (Object[] object : selectData) {
                    loadId.add(object[2].toString());
                }
                data.setLoadId(loadId);
                data.getText().setText(data.getText().getText() + SearchUtil.buildSearchByMultiIDs(data));
            }
        }
    }

    private class ShowItemsListenter implements ActionListener {
        private TableModelCheckBox productModelTable;
        private JFrame main;

        private ShowItemsListenter(JFrame main, TableModelCheckBox productModelTable) {
            this.main = main;
            this.productModelTable = productModelTable;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object[]> selectData = productModelTable.getDataSelect();
            if (selectData.isEmpty()) {
                JOptionPane.showMessageDialog(main, "You must select at last one product", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder();
                for (Object[] objects : selectData) {
                    sb.append("Reference ID : " + objects[2] + "\n");
                    for (Item item : ManagerDAO.getInstance().getItemDAO().getItemsByProductId((Long) objects[1])) {
                        sb.append("Item : " + item + "\n");
                    }
                    data.getText().setText(data.getText().getText() + sb.toString());
                }
            }
        }
    }
}
