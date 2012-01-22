package com.ebaytools.gui.linteners;

import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.FileSearching;
import com.ebaytools.util.SearchUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchingByFilesOfListActionListener implements ActionListener {
    private JFrame main;
    private JTextArea text;
    private Data dataModel;

    public SearchingByFilesOfListActionListener(JFrame main, Data data) {
        this.main = main;
        this.text = data.getText();
        this.dataModel = data;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO don't implemented yet
        /*List<String> data = new ArrayList<String>();
        for (FileSearching fileSearching : ManagerDAO.getInstance().getFileSearchingDAO().getAllFileSearching()) {
            File file = new File(fileSearching.getPath());
            if (file.exists()) {
                data.addAll(LoadFileSearchItemActionListener.readFile(file));
            } else {
                String msg = "Error this file does not exist file : " + file.getAbsolutePath() + "\n";
                text.setText(text.getText() + msg);
            }
            SearchUtil.buildSearchByMultiIDs(dataModel);
            text.setText(text.getText() + msg);
        } */
    }
}
