package com.ebaytools.gui.linteners;

import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.FileSearching;
import com.ebaytools.kernel.entity.FilterValue;
import com.ebaytools.util.Pair;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveFileSearchingAction implements ActionListener {
    private JDialog dialog;
    private JLabel filePart;
    private JList<Pair<String>> valueConditions;
    private JTextField timeOfDay;
    private JTextField listType;
    private JTextField intervalUpdate;
    private Data data;

    public SaveFileSearchingAction(JDialog dialog, JLabel filePart, JList<Pair<String>> valueConditions, JTextField listType, JTextField timeOfDay, JTextField intervalUpdate, Data data) {
        this.dialog = dialog;
        this.filePart = filePart;
        this.intervalUpdate = intervalUpdate;
        this.valueConditions = valueConditions;
        this.timeOfDay = timeOfDay;
        this.data = data;
        this.timeOfDay = timeOfDay;
        this.listType = listType;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        FileSearching fileSearching = new FileSearching();
        fileSearching.setPath(filePart.getText());
        StringBuilder conditions = new StringBuilder();
        for (Pair<String> pair : valueConditions.getSelectedValuesList()) {
            conditions.append(pair.getValue()).append(";");
        }
        fileSearching.setCondition(conditions.toString());
        fileSearching.setListType(listType.getText());
        fileSearching.setDayLeft(Integer.valueOf(timeOfDay.getText()));
        fileSearching.setTimeInterval(Integer.valueOf(intervalUpdate.getText()));
        ManagerDAO.getInstance().getFileSearchingDAO().create(fileSearching);
        dialog.setVisible(false);
        dialog.dispose();
        data.getRefresFileSearchingTable().actionPerformed(null);
    }
}
