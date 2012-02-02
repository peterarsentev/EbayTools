package com.ebaytools.gui.dialog;

import com.ebaytools.gui.linteners.AddfileActionListener;
import com.ebaytools.gui.linteners.SaveFileSearchingAction;
import com.ebaytools.gui.linteners.SaveFilterListener;
import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.GraphPaperLayout;
import com.ebaytools.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class CreateFileSearchingDialog extends JDialog {
    private JDialog dialog;
    private Data data;

    public CreateFileSearchingDialog(JFrame main, Data data) {
        dialog = this;
        this.data = data;
        dialog.setTitle("Add new file searching");
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        dialog.add(buildCreatePanel(), BorderLayout.CENTER);
        dialog.pack();
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(main);
        dialog.setVisible(true);
    }

    private JPanel buildCreatePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GraphPaperLayout(new Dimension(12, 12), 1, 1));
        JLabel filePart = new JLabel("-");
        panel.add(filePart, new Rectangle(3, 1, 11, 1));
        JButton selectFile = new JButton("Select");
        selectFile.addActionListener(new AddfileActionListener(this, filePart));
        panel.add(selectFile, new Rectangle(0, 1, 3, 1));
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
        panel.add(new JLabel("List type : "), new Rectangle(0, 6, 6, 1));
        JTextField listType = new JTextField();
        panel.add(listType, new Rectangle(6, 6, 6, 1));
        panel.add(new JLabel("Time of day (in hours) : "), new Rectangle(0, 7, 6, 1));
        JTextField timeOfDay = new JTextField();
        panel.add(timeOfDay, new Rectangle(6, 7, 6, 1));
        panel.add(new JLabel("Interval (min): "), new Rectangle(0, 8, 6, 1));
        JTextField intervalHours = new JTextField();
        panel.add(intervalHours, new Rectangle(6, 8, 6, 1));
        JButton save = new JButton("Save");
        panel.add(save, new Rectangle(5, 9, 3, 1));
        save.addActionListener(new SaveFileSearchingAction(dialog, filePart, valueConditions, listType, timeOfDay,  intervalHours, data));
        return panel;
    }
}
