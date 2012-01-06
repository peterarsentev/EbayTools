package com.ebaytools.gui.linteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.SearchPanel;
import com.ebaytools.util.*;
import com.ebay.services.finding.*;
import java.util.*;

public class ItemsSearchListener implements ActionListener {
	private JTextArea text;
	private JTextField product;
    private JTextField condition;
    private JTextField listingType;
    private JComboBox<Pair<SortOrderType>> sortedTypeField;
    private JCheckBox golderSearch;
    private Data data;
    private JTextField daysLeft;

	public ItemsSearchListener(SearchPanel panel) {
		this.text = panel.getText();
		this.product = panel.getReferenceId();
        this.condition = panel.getConditionsField();
        this.listingType = panel.getListTypeField();
        this.sortedTypeField = panel.getSortedTypeField();
        this.golderSearch = panel.getGoldenSearch();
        this.data = panel.getData();
        this.daysLeft = panel.getDaysLeft();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
            Pair<SortOrderType> pairSorted = sortedTypeField.getItemAt(sortedTypeField.getSelectedIndex());
			StringBuilder sb = new StringBuilder("GoldenItems : \n");
            List<SearchItem> items;
            List<SearchItem> searchItems = SearchUtil.getInstance().getItemsBySortedType(product.getText(), condition.getText(), listingType.getText(), pairSorted.getValue(), "ReferenceID", TextUtil.getIntegerOrNull(daysLeft.getText()));
            if (golderSearch.isSelected()) {
                items = SearchUtil.getGoldenItems(searchItems);
            } else {
                items = searchItems;
            }
            data.setItems(items);
            sb.append(FormatterText.formatForConsole(data.getItems(), product.getText(), "ReferenceID"));
            sb.append("Total items : ").append(items.size());
            text.setText(text.getText() + sb.toString() + "\n");
            Map<Pair, List<SearchItem>> saveData = new LinkedHashMap<Pair, List<SearchItem>>();
            saveData.put(new Pair<String>(product.getText(), "ReferenceID"), items);
            data.setSaveData(saveData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
