package com.ebaytools.gui.model;

import com.ebay.services.finding.SearchItem;
import com.ebaytools.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * This is com.ebaytools.gui.model for data. We will be to use it every way in a project
 */
public class Data {
    private Map<String, Boolean> showOpts; //this map consists from options which we can see
    private List<SearchItem> items; // search result
    private List<String> loadId;
    private Map<Pair, List<SearchItem>> saveData; // data for save

    public Map<String, Boolean> getShowOpts() {
        return showOpts;
    }

    public void setShowOpts(Map<String, Boolean> showOpts) {
        this.showOpts = showOpts;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (items != null ? !items.equals(data.items) : data.items != null) return false;
        if (loadId != null ? !loadId.equals(data.loadId) : data.loadId != null) return false;
        if (saveData != null ? !saveData.equals(data.saveData) : data.saveData != null) return false;
        if (showOpts != null ? !showOpts.equals(data.showOpts) : data.showOpts != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = showOpts != null ? showOpts.hashCode() : 0;
        result = 31 * result + (items != null ? items.hashCode() : 0);
        result = 31 * result + (loadId != null ? loadId.hashCode() : 0);
        result = 31 * result + (saveData != null ? saveData.hashCode() : 0);
        return result;
    }
}
