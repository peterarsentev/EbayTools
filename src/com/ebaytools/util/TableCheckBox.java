package com.ebaytools.util;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.util.List;

public class TableCheckBox {
    public static void buildTable(final JTable productTable) {
        productTable.setAutoCreateRowSorter(true);
        TableColumn tc = productTable.getColumnModel().getColumn(0);
        tc.setCellEditor(productTable.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(productTable.getDefaultRenderer(Boolean.class));
        tc.setHeaderRenderer(new CheckBoxHeader(new CheckBoxListener(productTable)));
    }
}
