package com.ebaytools.gui.linteners;

import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.FileSearching;
import com.ebaytools.kernel.entity.Filter;
import com.ebaytools.kernel.entity.Product;
import com.ebaytools.util.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RefreshTableListenter implements ActionListener {
    public enum TypeTable {
        PRODUCT, FILTER, FILE_SEARCHING
    }
    private JTable tableProduct;
    private TableModelCheckBox modelCheckBox;
    private TypeTable type;

    public RefreshTableListenter(JTable tableProduct, TableModelCheckBox productModelTable, TypeTable type) {
        this.tableProduct = tableProduct;
        this.modelCheckBox = productModelTable;
        this.type = type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TableModelCheckBox.IData iData = null;
        if (TypeTable.PRODUCT == type) {
            List<Product> productList = ManagerDAO.getInstance().getProductDAO().getAllProduct();
            iData = new ProductDataImpl(productList);
        } else if (TypeTable.FILTER == type) {
            List<Filter> filters = ManagerDAO.getInstance().getFilterDAO().getAllFilters();
            iData = new FilterDataImpl(filters);
        } else if (TypeTable.FILE_SEARCHING == type) {
            List<FileSearching> fileSearching = ManagerDAO.getInstance().getFileSearchingDAO().getAllFileSearching();
            iData = new FileSearchingDataImpl(fileSearching);
        }
        modelCheckBox.setIDate(iData);
        TableCheckBox.buildTable(tableProduct);
        if (iData != null) {
            iData.resizeColumnsByName(tableProduct);
        }
        tableProduct.updateUI();
    }
}
