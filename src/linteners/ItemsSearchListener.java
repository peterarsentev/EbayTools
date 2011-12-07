package linteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;
import model.Data;
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
    private JCheckBox golderSearch;
    private Data data;
    private JDateChooser startTime;
    private JDateChooser endTime;

	public ItemsSearchListener(SearchUtil util, JTextField product, JTextField condition,
                               JTextField listingType, JComboBox<Pair<SortOrderType>> sortedTypeField, JTextArea text, JCheckBox golderSearch, Data data, JDateChooser startTime, JDateChooser endTime) {
		this.text = text;
		this.util = util;
		this.product = product;
        this.condition = condition;
        this.listingType = listingType;
        this.sortedTypeField = sortedTypeField;
        this.golderSearch = golderSearch;
        this.data = data;
        this.startTime = startTime;
        this.endTime = endTime;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
            Pair<SortOrderType> pairSorted = sortedTypeField.getItemAt(sortedTypeField.getSelectedIndex());
			StringBuilder sb = new StringBuilder("GoldenItems : \n");
            List<SearchItem> items = new ArrayList<SearchItem>();
            List<SearchItem> searchItems = util.getItemsBySortedType(product.getText(), condition.getText(), listingType.getText(), pairSorted.getValue(), "ReferenceID", startTime.getCalendar(), endTime.getCalendar());
            if (golderSearch.isSelected()) {
                items = SearchUtil.getGoldenItems(searchItems);
            } else {
                items = searchItems;
            }
            data.setItems(items);
            sb.append(FormatterText.formatForConsole(data.getItems(), data.getShowOpts(), product.getText(), "ReferenceID"));
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
