package com.ebaytools.gui.dialog;

import com.ebaytools.gui.linteners.AddfileActionListener;
import com.ebaytools.gui.linteners.SaveFileSearchingAction;
import com.ebaytools.gui.linteners.SaveFilterListener;
import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.GraphPaperLayout;
import com.ebaytools.kernel.entity.FileSearching;
import com.ebaytools.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(main);
        dialog.setVisible(true);
    }

    private static class ChangeListener implements ActionListener {
        private CreateFileSearchingDialog searchingDialog;
        public ChangeListener(CreateFileSearchingDialog searchingDialog) {
            this.searchingDialog = searchingDialog;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Integer type = searchingDialog.typeUpdate.getItemAt(searchingDialog.typeUpdate.getSelectedIndex()).getValue();
            type = type == null ? 0 : type;
            searchingDialog.listType.setEnabled(type != 1);
            searchingDialog.valueConditions.setEnabled(type != 1);
            searchingDialog.timeOfDay.setEnabled(type != 1);
            searchingDialog.intervalHours.setEnabled(type != 1);
        }
    }

    private JTextField listType;
    private JList<Pair<String>> valueConditions;
    private JTextField timeOfDay;
    private JTextField intervalHours;
    private JComboBox<Pair<Integer>> typeUpdate;

    private JPanel buildCreatePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GraphPaperLayout(new Dimension(12, 10), 1, 1));
        JTextField filePart = new JTextField();
        filePart.setEditable(false);
        panel.add(filePart, new Rectangle(0, 0, 9, 1));
        JButton selectFile = new JButton("Select");
        selectFile.addActionListener(new AddfileActionListener(this, filePart));
        panel.add(selectFile, new Rectangle(9, 0, 3, 1));

        this.typeUpdate = new JComboBox<Pair<Integer>>();
        typeUpdate.addItem(new Pair<Integer>("By time", FileSearching.TypeSearch.BY_TIME.key));
        typeUpdate.addItem(new Pair<Integer>("By push on button", FileSearching.TypeSearch.BY_PUSH_ON_BUTTON.key));
        panel.add(new JLabel("Type how update : "), new Rectangle(0, 1, 6, 1));
        panel.add(typeUpdate, new Rectangle(6, 1, 6, 1));
        typeUpdate.addActionListener(new ChangeListener(this));

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

        this.valueConditions = new JList<Pair<String>>(pairs.toArray(new Pair[pairs.size()]));
        panel.add(new JLabel("List type : "), new Rectangle(0, 6, 6, 1));
        panel.add(new JScrollPane(valueConditions), new Rectangle(3, 3, 9, 3));

        this.listType = new JTextField();
        listType.setText("Auction");
        panel.add(listType, new Rectangle(6, 6, 6, 1));

        panel.add(new JLabel("Time of day (in hours) : "), new Rectangle(0, 7, 6, 1));
        this.timeOfDay = new JTextField();
        panel.add(timeOfDay, new Rectangle(6, 7, 6, 1));

        panel.add(new JLabel("Interval (min): "), new Rectangle(0, 8, 6, 1));
        this.intervalHours = new JTextField();
        panel.add(intervalHours, new Rectangle(6, 8, 6, 1));

        JButton save = new JButton("Save");
        panel.add(save, new Rectangle(5, 9, 3, 1));
        save.addActionListener(new SaveFileSearchingAction(dialog, filePart, valueConditions, listType, timeOfDay,  intervalHours, data, typeUpdate));
        return panel;
    }
}
