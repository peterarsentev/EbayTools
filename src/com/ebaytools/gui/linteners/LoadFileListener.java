package com.ebaytools.gui.linteners;

import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.SearchPanel;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.util.SearchUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoadFileListener implements ActionListener {
    private JFileChooser fc;
    private JFrame main;
    private JTextArea text;
    private Data dataModel;

    public LoadFileListener(SearchPanel panel) {
        this.fc = new JFileChooser();
        this.main = panel.getMain();
        this.text = panel.getText();
        this.dataModel = panel.getData();
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
                ManagerDAO.getInstance().getProductDAO().create(dataModel.getSaveData(), dataModel.getGoldenSearch().isSelected());
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
