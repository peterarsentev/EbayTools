package linteners;

import dialog.ChooseOpts;
import model.Data;
import panel.SearchPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
