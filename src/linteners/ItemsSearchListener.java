package linteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import util.*;
import com.ebay.services.finding.*;
import java.util.*;

public class ItemsSearchListener implements ActionListener {
	private JTextArea text;
	private SearchUtil util;
	private JTextField product;
    private JTextField condition;
    private JTextField listingType;
    private JComboBox<Pair<SortOrderType>> sortedTypeField;

	public ItemsSearchListener(SearchUtil util, JTextField product, JTextField condition, JTextField listingType, JComboBox<Pair<SortOrderType>> sortedTypeField, JTextArea text) {
		this.text = text;
		this.util = util;
		this.product = product;
        this.condition = condition;
        this.listingType = listingType;
        this.sortedTypeField = sortedTypeField;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
            Pair<SortOrderType> pairSorted = sortedTypeField.getItemAt(sortedTypeField.getSelectedIndex());
			StringBuilder sb = new StringBuilder("GoldenItems : \n");
            List<SearchItem> searchItems = util.getItemsBySortedType(product.getText(), condition.getText(), listingType.getText(), pairSorted.getValue(), "ReferenceID");
            List<SearchItem> goldenItems = SearchUtil.getGoldenItems(searchItems);
            if (!goldenItems.isEmpty()) {
                for (SearchItem item : goldenItems) {
                    sb.append(item.getItemId()).append("\n");
                    sb.append(item.getTitle()).append("\n");
                    sb.append(item.getCondition().getConditionDisplayName()).append("\n");
                    sb.append(item.getListingInfo().getListingType()).append("\n\n");
                }
            }
            sb.append("Total items : ").append(goldenItems.size());
            text.setText(text.getText() + sb.toString() + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
