package com.ebaytools.gui.model;

import com.ebay.services.finding.SearchItem;
import com.ebay.services.finding.SortOrderType;
import com.ebaytools.util.Pair;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

/**
 * This is com.ebaytools.gui.model for data. We will be to use it every way in a project
 */
public class Data {
    private List<SearchItem> items; // search result
    private List<String> loadId;
    private Map<Pair, List<SearchItem>> saveData; // data for save
    private JTextField numbersItem;
    private JTextField referenceId;
    private JTextField conditionsField;
    private JTextField listTypeField;
    private JTextArea text;
    private JComboBox<Pair<SortOrderType>> sortedTypeField;
    private JCheckBox goldenSearch;
    private JTextField daysLeft;
    private ActionListener refresProductTable;
    private ActionListener refresFilterTable;
    private JButton buttonFilter;

    public JTextField getNumbersItem() {
        return numbersItem;
    }

    public void setNumbersItem(JTextField numbersItem) {
        this.numbersItem = numbersItem;
    }

    public JTextField getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(JTextField referenceId) {
        this.referenceId = referenceId;
    }

    public JTextField getConditionsField() {
        return conditionsField;
    }

    public void setConditionsField(JTextField conditionsField) {
        this.conditionsField = conditionsField;
    }

    public JTextField getListTypeField() {
        return listTypeField;
    }

    public void setListTypeField(JTextField listTypeField) {
        this.listTypeField = listTypeField;
    }

    public JTextArea getText() {
        return text;
    }

    public void setText(JTextArea text) {
        this.text = text;
    }

    public JComboBox<Pair<SortOrderType>> getSortedTypeField() {
        return sortedTypeField;
    }

    public void setSortedTypeField(JComboBox<Pair<SortOrderType>> sortedTypeField) {
        this.sortedTypeField = sortedTypeField;
    }

    public JCheckBox getGoldenSearch() {
        return goldenSearch;
    }

    public void setGoldenSearch(JCheckBox goldenSearch) {
        this.goldenSearch = goldenSearch;
    }

    public JTextField getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(JTextField daysLeft) {
        this.daysLeft = daysLeft;
    }

    public List<SearchItem> getItems() {
        return items;
    }

    public void setItems(List<SearchItem> items) {
        this.items = items;
    }

    public List<String> getLoadId() {
        return loadId;
    }

    public void setLoadId(List<String> loadId) {
        this.loadId = loadId;
    }

    public Map<Pair, List<SearchItem>> getSaveData() {
        return saveData;
    }

    public void setSaveData(Map<Pair, List<SearchItem>> saveData) {
        this.saveData = saveData;
    }

    public ActionListener getRefreshAction() {
        return refresProductTable;
    }

    public void setRefresProductTable(ActionListener refresProductTable) {
        this.refresProductTable = refresProductTable;
    }

    public ActionListener getRefresFilterTable() {
        return refresFilterTable;
    }

    public void setRefresFilterTable(ActionListener refresFilterTable) {
        this.refresFilterTable = refresFilterTable;
    }

    public JButton getButtonFilter() {
        return buttonFilter;
    }

    public void setButtonFilter(JButton buttonFilter) {
        this.buttonFilter = buttonFilter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (items != null ? !items.equals(data.items) : data.items != null) return false;
        if (loadId != null ? !loadId.equals(data.loadId) : data.loadId != null) return false;
        if (saveData != null ? !saveData.equals(data.saveData) : data.saveData != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = items != null ? items.hashCode() : 0;
        result = 31 * result + (loadId != null ? loadId.hashCode() : 0);
        result = 31 * result + (saveData != null ? saveData.hashCode() : 0);
        return result;
    }
}
