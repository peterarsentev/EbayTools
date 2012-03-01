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
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(main);
        dialog.setVisible(true);
    }

    private JPanel buildCreatePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GraphPaperLayout(new Dimension(12, 12), 1, 1));

        panel.add(new JLabel("Name : "), new Rectangle(0, 0, 3, 1));
        JTextField name = new JTextField();
        panel.add(name, new Rectangle(3, 0, 9, 1));

        panel.add(new JLabel("Golder : "), new Rectangle(0, 1, 3, 1));
        JComboBox<Pair<String>> golden = new JComboBox<Pair<String>>();
        golden.addItem(new Pair<String>("All", null));
        golden.addItem(new Pair<String>("Golden", "true"));
        golden.addItem(new Pair<String>("Not golden", "false"));
        panel.add(golden, new Rectangle(3, 1, 4, 1));

        panel.add(new JLabel("Conditions : "), new Rectangle(0, 2, 4, 1));
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
        panel.add(new JScrollPane(valueConditions), new Rectangle(4, 2, 8, 3));

        panel.add(new JLabel("Time of day (in hours) : "), new Rectangle(0, 5, 6, 1));
        JTextField timeOfDay = new JTextField();
        panel.add(timeOfDay, new Rectangle(6, 5, 6, 1));

        JComboBox<Pair<String>> soldCheck = new JComboBox<Pair<String>>();
        soldCheck.addItem(new Pair<String>("All", null));
        soldCheck.addItem(new Pair<String>("Unsold", "false"));
        soldCheck.addItem(new Pair<String>("Sold", "true"));
        panel.add(new JLabel("State : "), new Rectangle(0, 6, 3, 1));
        panel.add(soldCheck, new Rectangle(3, 6, 4, 1));

        JComboBox<Pair<String>> period = new JComboBox<Pair<String>>();
        period.addItem(new Pair<String>("Hour", "hour"));
        period.addItem(new Pair<String>("Day", "day"));
        period.addItem(new Pair<String>("Ref", "ref"));
        panel.add(new JLabel("Period (only for average) : "), new Rectangle(0, 7, 7, 1));
        panel.add(period, new Rectangle(7, 7, 5, 1));

        panel.add(new JLabel("Auction state : "), new Rectangle(0, 8, 4, 1));
        JTextField auctionState = new JTextField();
        panel.add(auctionState, new Rectangle(4, 8, 8, 1));

        panel.add(new JLabel("Total bid : "), new Rectangle(0, 9, 3, 1));
        JComboBox<Pair<String>> typeTotalBid = new JComboBox<Pair<String>>();
        typeTotalBid.addItem(new Pair<String>("<", "<"));
        typeTotalBid.addItem(new Pair<String>(">", ">"));
        typeTotalBid.addItem(new Pair<String>("=", "="));
        JTextField totalBid = new JTextField();
        panel.add(typeTotalBid, new Rectangle(3, 9, 2, 1));
        panel.add(totalBid, new Rectangle(5, 9, 4, 1));

        JButton save = new JButton("Save");
        panel.add(save, new Rectangle(5, 11, 3, 1));
        save.addActionListener(new SaveFilterListener(dialog, name, golden, valueConditions, timeOfDay, data, soldCheck, period, auctionState, typeTotalBid, totalBid));
        return panel;
    }
}
