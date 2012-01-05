package com.ebaytools.util;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;
import java.util.List;

public class TableModelCheckBox extends AbstractTableModel {
    public interface IData {
        public List<Object[]> getData();
        public List<String> getNameColumn();
    }

    private List<String> nameColumn;
    private List<Object[]> data = new ArrayList<Object[]>();

    public List<Object[]> getDataSelect() {
        List<Object[]> dataSelect = new ArrayList<Object[]>();
        for(Object[] line: data) {
            Boolean checkBox = (Boolean) line[0];
            if (checkBox) {
                dataSelect.add(line);
            }
        }
        return dataSelect;
    }

    public int getColumnCount() {
        return nameColumn.size() + 1;
    }

    public TableModelCheckBox(IData iData) {
        this.data = iData.getData();
        this.nameColumn = iData.getNameColumn();
    }

    public int getRowCount() {
        return data == null ? 0 : data.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public Class getColumnClass(int columnIndex) {
        if (data == null || data.size() == 0) {
            return Object.class;
        }
        Object o = getValueAt(0, columnIndex);
        return o == null ? Object.class : o.getClass();
    }

    public String getColumnName(int column) {
        if (column != 0) {
            return nameColumn.get(column-1);
        }
        return null;
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        data.get(rowIndex)[columnIndex] = value;
        super.fireTableCellUpdated(rowIndex, columnIndex);
    }

    public static TableColumnModel resizeColumnsByName(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tcm = table.getColumnModel();
        int addedSize = table.getFont().getSize() + 160;
        tcm.getColumn(0).setPreferredWidth(20);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        for (int i = 1; i < table.getColumnCount(); i++) {
            String hvalue = tcm.getColumn(i).getHeaderValue().toString();
            tcm.getColumn(i).setPreferredWidth(table.getFontMetrics(table.getFont()).stringWidth(hvalue) + addedSize);
        }
        return tcm;
    }
}
