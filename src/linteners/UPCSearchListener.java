package linteners;

import panel.SearchPanel;
import util.SearchUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UPCSearchListener implements ActionListener {
    private JTextArea text;
    private SearchUtil util;
    private JTextField field;

    public UPCSearchListener(SearchPanel panel) {
        this.text = panel.getText();
        this.util = panel.getUtil();
        this.field = panel.getReferenceId();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        try {
            String upc = util.getUpcID(field.getText());
            text.setText(text.getText() + "UPC ID : " + upc + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
