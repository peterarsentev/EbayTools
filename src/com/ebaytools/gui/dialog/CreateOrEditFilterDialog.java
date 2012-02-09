package com.ebaytools.gui.dialog;

import com.ebaytools.gui.linteners.SaveFilterListener;
import com.ebaytools.gui.linteners.SaveProductListener;
import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.GraphPaperLayout;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Product;
import com.ebaytools.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class CreateOrEditFilterDialog extends JDialog {
    private JDialog dialog;
    private Long filterId;
    private Data data;

    public CreateOrEditFilterDialog(JFrame main, Long filterId, Data data) {
        dialog = this;
        this.data = data;
        this.filterId = filterId;
        dialog.setTitle(filterId == null ? "Create filter" : "Edit filter");
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        dialog.add(buildCreatePanel(), BorderLayout.CENTER);
        dialog.pack();
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(main);
        dialog.setVisible(true);
    }

    private JPanel buildCreatePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GraphPaperLayout(new Dimension(12, 10), 1, 1));
        panel.add(new JLabel("Name : "), new Rectangle(0, 1, 3, 1));
        JTextField name = new JTextField();
        panel.add(name, new Rectangle(3, 1, 9, 1));
        panel.add(new JLabel("Golder : "), new Rectangle(0, 2, 3, 1));
        JComboBox<Pair<String>> golden = new JComboBox<Pair<String>>();
        golden.addItem(new Pair<String>("All", null));
        golden.addItem(new Pair<String>("Golden", "true"));
        golden.addItem(new Pair<String>("Not golden", "false"));
        panel.add(golden, new Rectangle(3, 2, 6, 1));
        panel.add(new JLabel("Conditions : "), new Rectangle(0, 3, 3, 1));
        java.util.List<Pair<String>> pairs = new ArrayList< Pair<String>>();
        pairs.add(new Pair<String>("New", "1000"));
        pairs.add(new Pair<String>("New other (see details)", "1500"));
        pairs.add(new Pair<String>("New with defects", "1750"));
        pairs.add(new Pair<String>("Manufacturer refurbished", "2000"));
        pairs.add(new Pair<String>("Seller refurbished", "2500"));
        pairs.add(new Pair<String>("Used", "3000"));
        pairs.add(new Pair<String>("Very Good", "4000"));
        pairs.add(new Pair<String>("Good", "5000"));
        pairs.add(new Pair<String>("Acceptable", "6000"));
        pairs.add(new Pair<String>("For parts or not working", "7000"));
        JList<Pair<String>> valueConditions = new JList<Pair<String>>(pairs.toArray(new Pair[pairs.size()]));
        panel.add(new JScrollPane(valueConditions), new Rectangle(3, 3, 9, 3));
        panel.add(new JLabel("Time of day (in hours) : "), new Rectangle(0, 6, 6, 1));
        JTextField timeOfDay = new JTextField();
        panel.add(timeOfDay, new Rectangle(6, 6, 6, 1));
        JCheckBox soldCheckBox = new JCheckBox();
        panel.add(new JLabel("Sold : "), new Rectangle(0, 7, 3, 1));
        panel.add(soldCheckBox, new Rectangle(3, 7, 1, 1));
        JButton save = new JButton("Save");
        panel.add(save, new Rectangle(5, 9, 3, 1));
        save.addActionListener(new SaveFilterListener(dialog, name, golden, valueConditions, timeOfDay, data, soldCheckBox));
        return panel;
    }
}
