package linteners;

import com.ebay.services.finding.SearchItem;
import com.ebay.services.finding.SortOrderType;
import util.Pair;
import util.SearchUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SortedResultListener implements ActionListener {
    private JTextArea text;
    private JTextField productField;
    private JComboBox<Pair<String>> typeList;
    private JComboBox<Pair<String>> condition;
    private JComboBox<Pair<SortOrderType>> sortedType;
    private SearchUtil util;

    public SortedResultListener(SearchUtil util, JTextField productField, JComboBox<Pair<String>> condition, JComboBox<Pair<String>> typeList, JComboBox<Pair<SortOrderType>> sortedType, JTextArea text) {
        this.text = text;
        this.condition = condition;
        this.typeList = typeList;
        this.sortedType = sortedType;
        this.productField = productField;
        this.util = util;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        try {
            Pair<String> pairCondition = condition.getItemAt(condition.getSelectedIndex());
            Pair<String> pairTypeList = typeList.getItemAt(typeList.getSelectedIndex());
            Pair<SortOrderType> pairSorted = sortedType.getItemAt(sortedType.getSelectedIndex());
            String conditionValue = pairCondition != null ? pairCondition.getValue() : null;
            String typeListValue = pairTypeList != null ? pairTypeList.getValue() : null;
            StringBuilder sb = new StringBuilder("GoldenItems : \n");
            List<SearchItem> searchItems = util.getItemsBySortedType(productField.getText(), conditionValue, typeListValue, pairSorted.getValue(), "ReferenceID");
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
