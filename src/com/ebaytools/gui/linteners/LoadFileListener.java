package com.ebaytools.gui.linteners;

import com.ebay.services.finding.SearchItem;
import com.ebay.services.finding.SortOrderType;
import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.SearchPanel;
import com.ebaytools.util.FormatterText;
import com.ebaytools.util.Pair;
import com.ebaytools.util.SearchUtil;
import com.ebaytools.util.TextUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class LoadFileListener implements ActionListener {
    private JFileChooser fc;
    private JFrame main;
    private JTextArea text;
    private JTextField condition;
    private JTextField listingType;
    private JComboBox<Pair<SortOrderType>> sortedTypeField;
    private JCheckBox golderSearch;
    private Data dataModel;
    private JTextField daysLeft;

    public LoadFileListener(SearchPanel panel) {
        this.fc = new JFileChooser();
        this.condition = panel.getConditionsField();
        this.listingType = panel.getListTypeField();
        this.sortedTypeField = panel.getSortedTypeField();
        this.main = panel.getMain();
        this.text = panel.getText();
        this.golderSearch = panel.getGoldenSearch();
        this.dataModel = panel.getData();
        this.daysLeft = panel.getDaysLeft();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int returnVal = fc.showOpenDialog(main);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String msg;
            if (file.exists()) {
                List<String> data = readFile(file);
                dataModel.setLoadId(data);
                msg = SearchUtil.buildSearchByMultiIDs(dataModel);
            } else {
                msg = "Error this file does not exist file : " + file.getAbsolutePath() + "\n";
            }
            text.setText(text.getText() + msg);
        }
    }

    /**
     * This method reads text from a file.
     *
     * @param file input file
     * @return List output data
     */
    public static List<String> readFile(File file) {
        List<String> list = new ArrayList<String>();
        try {
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                if (strLine.contains(";")) {
                    list.add(strLine.split(";")[0]);
                } else {
                    list.add(strLine);
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return list;
    }
}
