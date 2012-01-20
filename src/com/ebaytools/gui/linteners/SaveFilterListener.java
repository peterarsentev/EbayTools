package com.ebaytools.gui.linteners;

import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Filter;
import com.ebaytools.kernel.entity.FilterConditions;
import com.ebaytools.kernel.entity.FilterValue;
import com.ebaytools.util.Fields;
import com.ebaytools.util.Pair;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.TreeSet;

public class SaveFilterListener implements ActionListener {
    private JDialog dialog;
    private JTextField name;
    private JCheckBox golden;
    private JList<Pair<String>> valueConditions;
    private JTextField timeOfDay;
    private Data data;

    public SaveFilterListener(JDialog dialog, JTextField name, JCheckBox golden, JList<Pair<String>> valueConditions, JTextField timeOfDay, Data data) {
        this.dialog = dialog;
        this.name = name;
        this.golden = golden;
        this.valueConditions = valueConditions;
        this.timeOfDay = timeOfDay;
        this.data = data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        FilterValue goldenValue = new FilterValue();
        goldenValue.setValue(String.valueOf(golden.isSelected()));
        FilterConditions goldenConditions = new FilterConditions();
        goldenConditions.setName(Fields.IS_GOLDEN.getKey());
        goldenConditions.setValues(new TreeSet<FilterValue>(Arrays.asList(goldenValue)));

        TreeSet<FilterValue> conditionsValues = new TreeSet<FilterValue>();
        for (Pair<String> pair : valueConditions.getSelectedValuesList()) {
            FilterValue conditionValue = new FilterValue();
            conditionValue.setValue(pair.getValue());
            conditionsValues.add(conditionValue);
        }

        FilterConditions conditionsConditions = new FilterConditions();
        conditionsConditions.setName(Fields.CONDITIONS.getKey());
        conditionsConditions.setValues(conditionsValues);

        FilterConditions timeOfDayConditions = new FilterConditions();
        timeOfDayConditions.setName(Fields.TIME_OF_DAY.getKey());
        FilterValue timeOfDayValue = new FilterValue();
        timeOfDayValue.setValue(String.valueOf(timeOfDay.getText()));
        timeOfDayConditions.setValues(new TreeSet<FilterValue>(Arrays.asList(timeOfDayValue)));

        Filter filter = new Filter();
        filter.setName(name.getText());
        filter.setConditions(new TreeSet<FilterConditions>(Arrays.asList(goldenConditions, conditionsConditions, timeOfDayConditions)));
        ManagerDAO.getInstance().getFilterDAO().create(filter);
        dialog.setVisible(false);
        dialog.dispose();
        data.getRefresFilterTable().actionPerformed(null);
    }
}
