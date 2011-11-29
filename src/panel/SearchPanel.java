package panel;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.*;
import linteners.*;
import util.*;
import java.util.*;

import com.ebay.services.finding.*;

public class SearchPanel extends JPanel {

	public SearchPanel() {
		// This section consists from initial objects
		SearchPanel panel = this;
		SearchUtil util = new SearchUtil();
		JButton searchReference = new JButton("Reference");
        JButton showResultFilter = new JButton("Result");
        JButton clear = new JButton("Clear");
        JButton save = new JButton("Save");
        JButton loadReferenceIDList = new JButton("Load Ref. ID list");
        JTextField numbersItem = new JTextField();
        JTextField referenceId = new JTextField();
        referenceId.setText("77826847");
        JTextField conditionsField = new JTextField();
        conditionsField.setText("Used");
        JTextField listTypeField = new JTextField();
        listTypeField.setText("Auction");
        JTextArea text = new JTextArea();
        JComboBox<Pair<SortOrderType>> sortedTypeField = new JComboBox<Pair<SortOrderType>>();
        for (SortOrderType sortedType : SortOrderType.values()) {
            sortedTypeField.addItem(new Pair<SortOrderType>(sortedType.value(), sortedType));
        }
        JCheckBox goldenSearch = new JCheckBox("Golden Search");

        //  In this section we paint own components, We use special layer manager GraphPaperLayout 
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GraphPaperLayout(new Dimension(20, 20), 1, 1));
        panel.add(new JLabel("Search reference id"), new Rectangle(0, 0, 5, 1));
        panel.add(new JLabel("Reference ID"), new Rectangle(0, 1, 5, 1));
        panel.add(numbersItem, new Rectangle(6, 0, 4, 1));
        panel.add(referenceId, new Rectangle(4, 1, 6, 1));
        panel.add(loadReferenceIDList, new Rectangle(11, 1, 5, 1));
        panel.add(goldenSearch, new Rectangle(11, 2, 5, 1));
        panel.add(searchReference, new Rectangle(11, 0, 5, 1));
        panel.add(new JLabel("Condition"), new Rectangle(0, 2, 5, 1));
        panel.add(conditionsField, new Rectangle(4, 2, 6, 1));
        panel.add(new JLabel("List Type"), new Rectangle(0, 3, 5, 1));
        panel.add(listTypeField, new Rectangle(4, 3, 6, 1));
        panel.add(showResultFilter, new Rectangle(11, 4, 5, 1));
        panel.add(new JScrollPane(text), new Rectangle(0,5,20,16));
        panel.add(clear, new Rectangle(17, 0, 3, 1));
        panel.add(save, new Rectangle(17, 2, 3, 1));
        panel.add(new JLabel("Sorted type"), new Rectangle(0, 4, 5, 1));
        panel.add(sortedTypeField, new Rectangle(4, 4, 6, 1));

        // in this section we add listeners in components, We use listeners for handle some action like press on button or change some items in combobox  
		searchReference.addActionListener(new ReferenceIDLinteren(util, numbersItem, text));
	    showResultFilter.addActionListener(new ItemsSearchListener(util, referenceId, conditionsField, listTypeField, sortedTypeField, text));
        clear.addActionListener(new ClearListener(text));
	    save.addActionListener(new SaveListener(util, panel, referenceId, text));
	    loadReferenceIDList.addActionListener(new LoadFileListener(util, panel, conditionsField, listTypeField, sortedTypeField, goldenSearch, text));
    }
}
