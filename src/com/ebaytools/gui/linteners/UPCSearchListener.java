package com.ebaytools.gui.linteners;

import com.ebaytools.gui.panel.SearchPanel;
import com.ebaytools.util.SearchUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UPCSearchListener implements ActionListener {
    private JEditorPane text;
    private JTextField field;

    public UPCSearchListener(SearchPanel panel) {
        this.text = panel.getText();
        this.field = panel.getReferenceId();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        try {
            String upc = SearchUtil.getInstance().getUpcID(field.getText());
            text.setText(text.getText() + "UPC ID : " + upc + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
