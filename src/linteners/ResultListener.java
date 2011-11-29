package linteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.Pair;
import util.SearchUtil;

import com.ebay.services.finding.SearchItem;

public class ResultListener implements ActionListener {
	private JTextArea text;
	private JComboBox<Pair<String>> typeList;
	private JComboBox<Pair<String>> condition;
	private List<SearchItem> items;
	
	public ResultListener(List<SearchItem> items, JComboBox<Pair<String>> condition, JComboBox<Pair<String>> typeList, JTextArea text) {
		this.text = text;
		this.condition = condition;
		this.typeList = typeList;
		this.items = items;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			Pair<String> pairCondition = condition.getItemAt(condition.getSelectedIndex());
			Pair<String> pairTypeList = typeList.getItemAt(typeList.getSelectedIndex());
			String conditionValue = pairCondition != null ? pairCondition.getValue() : null;
			String typeListValue = pairTypeList != null ? pairTypeList.getValue() : null;
			StringBuilder sb = new StringBuilder("Result : \n");
			List<SearchItem> searchItems = SearchUtil.getItemsByConditionAndListType(items, conditionValue, typeListValue);
			for (SearchItem item : searchItems) {
				sb.append(item.getItemId()).append("\n");
				sb.append(item.getTitle()).append("\n");
				sb.append(item.getCondition().getConditionDisplayName()).append("\n");
				sb.append(item.getListingInfo().getListingType()).append("\n\n");
			}
            sb.append("Total items : ").append(searchItems.size());
			text.setText(text.getText() + sb.toString() + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
