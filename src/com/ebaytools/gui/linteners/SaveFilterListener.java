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
import java.util.List;
import java.util.TreeSet;

public class SaveFilterListener implements ActionListener {
    private JDialog dialog;
    private JTextField name;
    private JComboBox<Pair<String>> golden;
    private JList<Pair<String>> valueConditions;
    private JTextField timeOfDay;
    private Data data;
    private JComboBox<Pair<String>> sold;
    private JComboBox<Pair<String>> period;

    public SaveFilterListener(JDialog dialog, JTextField name, JComboBox<Pair<String>> golden, JList<Pair<String>> valueConditions, JTextField timeOfDay, Data data, JComboBox<Pair<String>> sold, JComboBox<Pair<String>> period) {
        this.dialog = dialog;
        this.name = name;
        this.golden = golden;
        this.valueConditions = valueConditions;
        this.timeOfDay = timeOfDay;
        this.data = data;
        this.sold = sold;
        this.period = period;
    }
    
    private static FilterConditions addFilterValue(Fields key, List<Pair<String>> pairs) {
        TreeSet<FilterValue> values = new TreeSet<FilterValue>();
        for (Pair<String> pair : pairs) {
            FilterValue conditionValue = new FilterValue();
            conditionValue.setValue(pair.getValue());
            values.add(conditionValue);
        }

        FilterConditions conditionsConditions = new FilterConditions();
        conditionsConditions.setName(key.getKey());
        conditionsConditions.setValues(values);
        return conditionsConditions;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        
        Pair<String> valueGolden = new Pair<String>(null, String.valueOf(golden.getItemAt(golden.getSelectedIndex()).getValue()));
        FilterConditions filterGolden = addFilterValue(Fields.IS_GOLDEN_FILTER_FIELD, Arrays.asList(valueGolden));

        FilterConditions filterConditions = addFilterValue(Fields.CONDITIONS, valueConditions.getSelectedValuesList());

        Pair<String> valueSold = new Pair<String>(null, String.valueOf(sold.getItemAt(sold.getSelectedIndex()).getValue()));
        FilterConditions filterSold = addFilterValue(Fields.SOLD, Arrays.asList(valueSold));

        Pair<String> valueTimeOfDay = new Pair<String>(null, String.valueOf(timeOfDay.getText()));
        FilterConditions filterTimeOfDay = addFilterValue(Fields.TIME_OF_DAY, Arrays.asList(valueTimeOfDay));

        Pair<String> valuePeriod = new Pair<String>(null, String.valueOf(period.getItemAt(period.getSelectedIndex()).getValue()));
        FilterConditions filterPeriod = addFilterValue(Fields.PERIOD, Arrays.asList(valuePeriod));

        Filter filter = new Filter();
        filter.setName(name.getText());
        filter.setConditions(new TreeSet<FilterConditions>(Arrays.asList(filterGolden, filterConditions, filterSold, filterTimeOfDay, filterPeriod, filterPeriod)));

        ManagerDAO.getInstance().getFilterDAO().create(filter);
        dialog.setVisible(false);
        dialog.dispose();
        data.getRefresFilterTable().actionPerformed(null);
    }
}
