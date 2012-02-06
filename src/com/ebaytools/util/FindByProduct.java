package com.ebaytools.util;

import java.util.ArrayList;

import com.ebay.services.finding.FindItemsByProductRequest;
import com.ebay.services.finding.ItemFilter;
import com.ebay.services.finding.OutputSelectorType;

public class FindByProduct  extends FindItemsByProductRequest {
    public FindByProduct() {
        this.itemFilter = new ArrayList<ItemFilter>();
        this.outputSelector = new ArrayList<OutputSelectorType>();
    }

    public void addFilter(ItemFilter filter) {
        this.itemFilter.add(filter);
    }

    public void addSelect(OutputSelectorType type) {
        this.outputSelector.add(type);
    }
}
