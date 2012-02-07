package com.ebaytools.util;

import com.ebaytools.kernel.entity.Product;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductDataImpl implements TableModelCheckBox.IData {
    private List<Product> products;
    private static final List<String> nameColumn = Arrays.asList("ID", "Ref ID", "Name");

    public ProductDataImpl(List<Product> products) {
        this.products = products;
    }

    @Override
    public List<Object[]> getData() {
        List<Object[]> productFields = new ArrayList<Object[]>();
        for (Product pr : products) {
            Object[] objects = new Object[4];
            objects[0] = Boolean.FALSE;
            objects[1] = pr.getId();
            objects[2] = pr.getReferenceId();
            objects[3] = pr.getName();
            productFields.add(objects);
        }
        return productFields;
    }

    @Override
    public List<String> getNameColumn() {
        return nameColumn;
    }

    public void resizeColumnsByName(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(20);
        tcm.getColumn(1).setPreferredWidth(20);
        tcm.getColumn(2).setPreferredWidth(75);
        tcm.getColumn(3).setPreferredWidth(400);
    }
}
