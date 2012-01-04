package com.ebaytools.util;

import java.util.ArrayList;

import com.ebay.services.finding.FindItemsByProductRequest;
import com.ebay.services.finding.ItemFilter;

public class FindByProduct  extends FindItemsByProductRequest {
    public FindByProduct() {
        this.itemFilter = new ArrayList<ItemFilter>();
    }
    public void add(ItemFilter filter) {
        this.itemFilter.add(filter);
    }
}
