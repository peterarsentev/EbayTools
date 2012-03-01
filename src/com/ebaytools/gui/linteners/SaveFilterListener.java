package com.ebaytools.gui.linteners;

import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Filter;
import com.ebaytools.kernel.entity.FilterConditions;
import com.ebaytools.kernel.entity.FilterValue;
import com.ebaytools.util.Fields;
import com.ebaytools.util.Pair;
import com.ebaytools.util.TextUtil;

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
    private JTextField auctionState;
    private JComboBox<Pair<String>> typeTotalBid;
    private JTextField totalBid;

    public SaveFilterListener(JDialog dialog, JTextField name, JComboBox<Pair<String>> golden,
                              JList<Pair<String>> valueConditions, JTextField timeOfDay,
                              Data data, JComboBox<Pair<String>> sold, JComboBox<Pair<String>> period,
                              JTextField auctionState, JComboBox<Pair<String>> typeTotalBid, JTextField totalBid) {
        this.dialog = dialog;
        this.name = name;
        this.golden = golden;
        this.valueConditions = valueConditions;
        this.timeOfDay = timeOfDay;
        this.data = data;
        this.sold = sold;
        this.period = period;
        this.auctionState = auctionState;
        this.typeTotalBid = typeTotalBid;
        this.totalBid = totalBid;
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

        TreeSet<FilterConditions> values = new TreeSet<FilterConditions>();

        Pair<String> valueGolden = new Pair<String>(null, String.valueOf(golden.getItemAt(golden.getSelectedIndex()).getValue()));
        FilterConditions filterGolden = addFilterValue(Fields.IS_GOLDEN_FILTER_FIELD, Arrays.asList(valueGolden));
        values.add(filterGolden);

        FilterConditions filterConditions = addFilterValue(Fields.CONDITIONS, valueConditions.getSelectedValuesList());
        values.add(filterConditions);

        Pair<String> valueSold = new Pair<String>(null, String.valueOf(sold.getItemAt(sold.getSelectedIndex()).getValue()));
        FilterConditions filterSold = addFilterValue(Fields.SOLD, Arrays.asList(valueSold));
        values.add(filterSold);

        Pair<String> valueTimeOfDay = new Pair<String>(null, String.valueOf(timeOfDay.getText()));
        FilterConditions filterTimeOfDay = addFilterValue(Fields.TIME_OF_DAY, Arrays.asList(valueTimeOfDay));
        values.add(filterTimeOfDay);

        Pair<String> valuePeriod = new Pair<String>(null, String.valueOf(period.getItemAt(period.getSelectedIndex()).getValue()));
        FilterConditions filterPeriod = addFilterValue(Fields.PERIOD, Arrays.asList(valuePeriod));
        values.add(filterPeriod);

        Pair<String> valueAuctionState = new Pair<String>(null, String.valueOf(auctionState.getText()));
        FilterConditions filterAuctionState = addFilterValue(Fields.AUCTION_STATUS, Arrays.asList(valueAuctionState));
        values.add(filterAuctionState);

        if (TextUtil.getIntegerOrNull(totalBid.getText()) != null) {
            String valueTotalBid = typeTotalBid.getItemAt(typeTotalBid.getSelectedIndex()).getValue() + "|" + totalBid.getText();
            Pair<String> pairTotalBid = new Pair<String>(null, valueTotalBid);
            FilterConditions filterTotalBid = addFilterValue(Fields.TOTAL_BID, Arrays.asList(pairTotalBid));
            values.add(filterTotalBid);
        }

        Filter filter = new Filter();
        filter.setName(name.getText());
        filter.setConditions(values);

        ManagerDAO.getInstance().getFilterDAO().create(filter);
        dialog.setVisible(false);
        dialog.dispose();
        data.getRefresFilterTable().actionPerformed(null);
    }
}
