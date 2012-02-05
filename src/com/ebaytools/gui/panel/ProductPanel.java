package com.ebaytools.gui.panel;

import com.ebaytools.gui.dialog.CreateOrEditProductDialog;
import com.ebaytools.gui.linteners.*;
import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.ItemProperties;
import com.ebaytools.kernel.entity.Product;
import com.ebaytools.util.*;

import javax.swing.*;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
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
        createProduct.setForeground(ColorUtil.IndianRed);
        JButton editProduct = new JButton("Edit");
        editProduct.setForeground(ColorUtil.IndianRed);
        JButton deleteProduct = new JButton("Delete");
        deleteProduct.setForeground(ColorUtil.IndianRed);
        JButton refreshTable = new JButton("Refresh");
        refreshTable.setForeground(ColorUtil.IndianRed);
        JButton searchItem = new JButton("Search");
        searchItem.setForeground(ColorUtil.IndianRed);
        JButton showItems = new JButton("Items");
        JButton loadReferenceIDList = new JButton("Add files");
        loadReferenceIDList.setForeground(ColorUtil.IndianRed);
        showItems.setForeground(ColorUtil.IndianRed);
        showItems.setForeground(ColorUtil.IndianRed);
        panel.add(loadReferenceIDList, new Rectangle(12, 0, 4, 1));
        panel.add(createProduct, new Rectangle(12, 4, 4, 1));
        panel.add(editProduct, new Rectangle(12, 5, 4, 1));
        panel.add(refreshTable, new Rectangle(12, 6, 4, 1));
        panel.add(searchItem, new Rectangle(12, 7, 4, 1));
        panel.add(showItems, new Rectangle(12, 8, 4, 1));
        panel.add(deleteProduct, new Rectangle(12, 10, 4, 1));
        RefreshTableListenter refresAction = new RefreshTableListenter(tableProduct, productModelTable, RefreshTableListenter.TypeTable.PRODUCT);
        data.setRefresProductTable(refresAction);
        createProduct.addActionListener(new OpenProductIDDialogListener(main, data));
        editProduct.addActionListener(new UpdateProductListenter(main, productModelTable, data));
        deleteProduct.addActionListener(new DeleteProductListenter(main, productModelTable));
        refreshTable.addActionListener(refresAction);
        searchItem.addActionListener(new SearchListenter(main, productModelTable));
        showItems.addActionListener(new ShowItemsListenter(main, productModelTable));
        loadReferenceIDList.addActionListener(new OpenFileSearchingDialogAcitonListener(main, data));
        data.setProductTable(productModelTable);
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
            long start = System.currentTimeMillis();
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
                ManagerDAO.getInstance().getProductDAO().create(data.getSaveData());
                data.getText().setText(data.getText().getText() + "Start search total auction ids : " + data.getSaveData().size() + " time : " + (System.currentTimeMillis() - start)/1000 +"s \n");
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
                        sb.append("itemID : ").append(item.getEbayItemId()).append("\n");
                        sb.append("count_bid : ").append(item.getTotalBid()).append("\n");
                        sb.append("auction_close_date : ").append(FormatterText.dateformatter.format(item.getCloseDate().getTime())).append("\n");
                        sb.append("auction_status : ").append(item.getCloseAuction() ? "completed" : "active").append("\n");
                        sb.append("is_golden : ").append(item.getGolden()).append("\n");
                        List<ItemProperties> list = new ArrayList<ItemProperties>(item.getProperties());
                        Collections.sort(list);
                        for (ItemProperties properties : list) {
                            String value = properties.getValue();
                            if (TextUtil.isNotNull(properties.getType())) {
                                value += " " + properties.getType();
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