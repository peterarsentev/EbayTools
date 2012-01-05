package com.ebaytools.util;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CheckBoxListener implements ItemListener {
    private JTable jTable;
    public CheckBoxListener(JTable jTable) {
        this.jTable = jTable;
    }

    public void itemStateChanged(ItemEvent e) {
        Object source = e.getSource();
        if (source instanceof AbstractButton == false) return;
        boolean checked = e.getStateChange() == ItemEvent.SELECTED;
        for(int x = 0, y = jTable.getRowCount(); x < y; x++) {
            jTable.setValueAt(new Boolean(checked),x,0);
        }
    }
}
