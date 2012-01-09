package com.ebaytools.gui.dialog;

import com.ebaytools.gui.panel.GraphPaperLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class BarDialog extends JDialog {
    private JDialog dialog;
    private boolean isClose = true;

    public BarDialog(JFrame main, String title) {
        dialog = this;
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        dialog.add(new JLabel(title), BorderLayout.CENTER);
        dialog.pack();
        dialog.setSize(200, 100);
        dialog.setLocationRelativeTo(main);
        dialog.setVisible(true);
    }

    public void close() {
        isClose = false;
        dialog.setVisible(false);
        dialog.dispose();
    }
}
