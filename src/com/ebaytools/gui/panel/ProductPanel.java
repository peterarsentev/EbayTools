package com.ebaytools.gui.panel;

import com.ebaytools.gui.dialog.CreateOrEditProductDialog;
import com.ebaytools.gui.linteners.OpenProductIDDialogListener;
import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.ItemProperties;
import com.ebaytools.kernel.entity.Product;
import com.ebaytools.util.*;

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
        JTable tableProduct =  new JTable(productModelTable);
        TableCheckBox.buildTable(tableProduct);
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
        RefreshTableListenter refresAction = new RefreshTableListenter(tableProduct, productModelTable);
        data.setRefresAction(refresAction);
        createProduct.addActionListener(new OpenProductIDDialogListener(main, data));
        editProduct.addActionListener(new UpdateProductListenter(main, productModelTable, data));
        deleteProduct.addActionListener(new DeleteProductListenter(main, productModelTable));
        refreshTable.addActionListener(refresAction);
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
                JOptionPane.showMessageDialog(main, "You must select at least one product", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                for (Object[] object : selectData) {
                    ManagerDAO.getInstance().getProductDAO().delete((Long) object[1]);
                }
            }
            data.getRefreshAction().actionPerformed(e);
        }
    }

    private class RefreshTableListenter implements ActionListener {
        private JTable tableProduct;
        private TableModelCheckBox modelCheckBox;

        private RefreshTableListenter(JTable tableProduct, TableModelCheckBox productModelTable) {
            this.tableProduct = tableProduct;
            this.modelCheckBox = productModelTable;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Product> productList = ManagerDAO.getInstance().getProductDAO().getAllProduct();
            ProductDataImpl productData = new ProductDataImpl(productList);
            modelCheckBox.setIDate(productData);
            TableCheckBox.buildTable(tableProduct);
            tableProduct.updateUI();
        }
    }

    private class UpdateProductListenter implements ActionListener {
        private TableModelCheckBox productModelTable;
        private JFrame main;
        private Data data;

        private UpdateProductListenter(JFrame main, TableModelCheckBox productModelTable, Data data) {
            this.main = main;
            this.productModelTable = productModelTable;
            this.data = data;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object[]> selectData = productModelTable.getDataSelect();
            if (selectData.isEmpty() || selectData.size() > 1) {
                JOptionPane.showMessageDialog(main, "You must select only one product", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                CreateOrEditProductDialog productDialog = new CreateOrEditProductDialog(main, (Long) selectData.get(0)[1], data);
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
                JOptionPane.showMessageDialog(main, "You must select at least one product", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                List<String> loadId = new ArrayList<String>();
                for (Object[] object : selectData) {
                    loadId.add(object[2].toString());
                }
                data.setLoadId(loadId);
                data.getText().setText(data.getText().getText() + SearchUtil.buildSearchByMultiIDs(data));
                ManagerDAO.getInstance().getProductDAO().create(data.getSaveData(), data.getGoldenSearch().isSelected());
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
                JOptionPane.showMessageDialog(main, "You must select at least one product", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder();
                for (Object[] objects : selectData) {
                    sb.append("Reference ID : " + objects[2] + "\n");
                    List<Item> items = ManagerDAO.getInstance().getItemDAO().getItemsByProductId((Long) objects[1]); 
                    for (Item item : items) {
                        sb.append("itemID : ").append(item.getEbayItemId()).append("\t\t").append(FormatterText.dateformatter.format(item.getCreateDate().getTime())).append("\n");
                        List<ItemProperties> list = new ArrayList<ItemProperties>(item.getProperties());
                        Collections.sort(list);
                        for (ItemProperties properties : list) {
                            String value = properties.getValue();
                            if (Fields.AUCTION_CLOSE_TIME.getKey().equals(properties.getName()) && TextUtil.isNotNull(value)) {
                                value = FormatterText.dateformatter.format(new Date(Long.parseLong(value)));
                            } 
                            sb.append(properties.getName()).append(" : ").append(value).append("\n");
                        }
                        sb.append("\n");
                    }
                    sb.append("Total items : ").append(items.size()).append("\n");
                    data.getText().setText(data.getText().getText() + sb.toString());
                }
            }
        }
    }
}