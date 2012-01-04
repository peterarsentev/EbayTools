package com.ebaytools.gui.linteners;

import com.ebaytools.gui.dialog.ChooseOpts;
import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.SearchPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseOptsListener implements ActionListener {
    private JFrame main;
    private Data data;

    public ChooseOptsListener(SearchPanel panel) {
        this.main = panel.getMain();
        this.data = panel.getData();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ChooseOpts chooseOpts = new ChooseOpts(main, data);
        chooseOpts.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}
