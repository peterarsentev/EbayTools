package com.ebaytools.gui.dialog;

import com.ebaytools.gui.linteners.CollectChooseOptsListener;
import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.*;
import com.ebaytools.kernel.dao.ManagerDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChooseOpts extends JDialog {
    private Data data;
    private JDialog dialog;

    public ChooseOpts(JFrame main, Data data) {
        dialog = this;
        this.data = data;
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        dialog.add(buildChoosePanel(), BorderLayout.CENTER);
        dialog.pack();
        dialog.setSize(700, 600);
        dialog.setLocationRelativeTo(main);
        dialog.setVisible(true);
    }

    private JPanel buildChoosePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GraphPaperLayout(new Dimension(32, 32), 1, 1));
        panel.add(new JLabel("Choose options."), new Rectangle(14, 0, 35, 1));
        List<JCheckBox> boxList = new ArrayList<JCheckBox>();
        int column = 1;
        int div = EbayGUI.fields.size()/2 + 1;
        int start = 0;
        List<String> chooseOpt = ManagerDAO.getInstance().getSystemSettingDAO().getChooseOptsValue();
        for (Map.Entry<String, Boolean> entry : EbayGUI.fields.entrySet()) {
            if (column > div) {
                start = 16;
                column = 1;
            }
            if (!entry.getKey().startsWith("+")) {
                int startApproximate = start;
                JCheckBox box;
                if (entry.getKey().startsWith("--")) {
                    ++startApproximate;
                    box = new JCheckBox(entry.getKey().substring(2));
                    box.setSelected(chooseOpt.contains(entry.getKey().substring(2)));
                    panel.add(box, new Rectangle(startApproximate, column, 15, 1));
                } else {
                    box = new JCheckBox(entry.getKey());
                    box.setSelected(chooseOpt.contains(entry.getKey()));
                    panel.add(box, new Rectangle(start, column, 15, 1));
                }
                boxList.add(box);
            } else {
                panel.add(new JLabel(entry.getKey().substring(1)), new Rectangle(start, column, 15, 1));
            }
            ++column;
        }
        JButton ok = new JButton("Ok");
        panel.add(ok, new Rectangle(0, 31, 10, 1));
        ok.addActionListener(new CollectChooseOptsListener(dialog, data, boxList));
        return panel;
    }
}
