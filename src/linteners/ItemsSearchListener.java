package linteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;
import model.Data;
import panel.SearchPanel;
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
    private JTextField daysLeft;

	public ItemsSearchListener(SearchPanel panel) {
		this.text = panel.getText();
		this.util = panel.getUtil();
		this.product = panel.getReferenceId();
        this.condition = panel.getConditionsField();
        this.listingType = panel.getListTypeField();
        this.sortedTypeField = panel.getSortedTypeField();
        this.golderSearch = panel.getGoldenSearch();
        this.data = panel.getData();
        this.startTime = panel.getStartData();
        this.endTime = panel.getEndData();
        this.daysLeft = panel.getDaysLeft();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
            Pair<SortOrderType> pairSorted = sortedTypeField.getItemAt(sortedTypeField.getSelectedIndex());
			StringBuilder sb = new StringBuilder("GoldenItems : \n");
            List<SearchItem> items;
            List<SearchItem> searchItems = util.getItemsBySortedType(product.getText(), condition.getText(), listingType.getText(), pairSorted.getValue(), "ReferenceID", startTime.getCalendar(), endTime.getCalendar(), TextUtil.getIntegerOrNull(daysLeft.getText()));
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
