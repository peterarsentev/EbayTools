package com.ebaytools.gui.model;

import com.ebay.services.finding.SearchItem;
import com.ebay.services.finding.SortOrderType;
import com.ebaytools.kernel.entity.Filter;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.util.Pair;
import com.ebaytools.util.TableModelCheckBox;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

/**
 * This is com.ebaytools.gui.model for data. We will be to use it every way in a project
 */
public class Data {
    private Map<SearchItem, Boolean> items; // search result
    private List<String> loadId;
    private Map<Pair, Map<SearchItem, Boolean>> saveData; // data for save
    private JTextField numbersItem;
    private JTextField referenceId;
    private JTextField conditionsField;
    private JTextField listTypeField;
    private JEditorPane text;
    private JComboBox<Pair<SortOrderType>> sortedTypeField;
    private JCheckBox goldenSearch;
    private JTextField daysLeft;
    private ActionListener refresProductTable;
    private ActionListener refresFilterTable;
    private ActionListener refresFileSearchingTable;
    private JButton buttonFilter;
    private TableModelCheckBox productTable;
    private Map<Filter, List<Item>> filterItems;

    public Map<Filter, List<Item>> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(Map<Filter, List<Item>> filterItems) {
        this.filterItems = filterItems;
    }

    public TableModelCheckBox getProductTable() {
        return productTable;
    }

    public void setProductTable(TableModelCheckBox productTable) {
        this.productTable = productTable;
    }

    public ActionListener getRefresFileSearchingTable() {
        return refresFileSearchingTable;
    }

    public void setRefresFileSearchingTable(ActionListener refresFileSearchingTable) {
        this.refresFileSearchingTable = refresFileSearchingTable;
    }

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

    public JEditorPane getText() {
        return text;
    }

    public void setText(JEditorPane text) {
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

    public List<String> getLoadId() {
        return loadId;
    }

    public void setLoadId(List<String> loadId) {
        this.loadId = loadId;
    }

    public Map<SearchItem, Boolean> getItems() {
        return items;
    }

    public void setItems(Map<SearchItem, Boolean> items) {
        this.items = items;
    }

    public Map<Pair, Map<SearchItem, Boolean>> getSaveData() {
        return saveData;
    }

    public void setSaveData(Map<Pair, Map<SearchItem, Boolean>> saveData) {
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
