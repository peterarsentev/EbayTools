package com.ebaytools.util;

import java.util.ArrayList;

import com.ebay.services.finding.ItemFilter;

public class Filter extends ItemFilter {
	public Filter() {
		this.value = new ArrayList<String>();
	}

	public void addValue(String value) {
		this.value.add(value);
	}
}