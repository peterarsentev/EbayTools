package com.ebaytools.gui.linteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.ebaytools.gui.dialog.BarDialog;
import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.SearchPanel;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.util.*;
import com.ebay.services.finding.*;
import org.apache.log4j.Logger;

import java.util.*;

public class ItemsSearchListener implements ActionListener {
    private static final Logger log = Logger.getLogger(ItemsSearchListener.class);
    
    private JTextArea text;
	private JTextField product;
    private JTextField condition;
    private JTextField listingType;
    private JComboBox<Pair<SortOrderType>> sortedTypeField;
    private JCheckBox golderSearch;
    private Data data;
    private JTextField daysLeft;
    private JFrame main;

    public ItemsSearchListener(SearchPanel panel) {
		this.text = panel.getText();
		this.product = panel.getReferenceId();
        this.condition = panel.getConditionsField();
        this.listingType = panel.getListTypeField();
        this.sortedTypeField = panel.getSortedTypeField();
        this.golderSearch = panel.getGoldenSearch();
        this.data = panel.getData();
        this.daysLeft = panel.getDaysLeft();
        this.main = panel.getMain();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
            Pair<SortOrderType> pairSorted = sortedTypeField.getItemAt(sortedTypeField.getSelectedIndex());
			StringBuilder sb = new StringBuilder("GoldenItems : \n");
            Map<SearchItem, Boolean> items;
            log.debug(product.getText()+" "+condition.getText()+ " " +listingType.getText() + " " +pairSorted.getValue()+ " " +"ReferenceID"+ " " +TextUtil.convertDayToHours(daysLeft.getText()));
            List<SearchItem> searchItems = SearchUtil.getInstance().getItemsBySortedType(product.getText(), condition.getText(), listingType.getText(), pairSorted.getValue(), "ReferenceID", TextUtil.convertDayToHours(daysLeft.getText()));
            log.debug("total : " + searchItems.size());
            if (golderSearch.isSelected()) {
                items = SearchUtil.getGoldenItems(searchItems);
            } else {
                items = SearchUtil.fullingGoldenItems(searchItems);
            }
            data.setItems(items);
            sb.append(FormatterText.formatForConsole(data.getItems(), product.getText(), "ReferenceID"));
            sb.append("Total items : ").append(items.size());
            text.setText(text.getText() + sb.toString() + "\n");
            Map<Pair, Map<SearchItem, Boolean>> saveData = new LinkedHashMap<Pair, Map<SearchItem, Boolean>>();
            saveData.put(new Pair<String>(product.getText(), "ReferenceID"), items);
            data.setSaveData(saveData);
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
}
