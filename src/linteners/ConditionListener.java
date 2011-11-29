package linteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.*;

import javax.swing.*;

import util.*;

import com.ebay.services.finding.*;
import java.util.*;

public class ConditionListener implements ItemListener {
	private List<SearchItem> items;
	private JComboBox<Pair<String>> condition;
	private JComboBox<Pair<String>> typeList;
	private JTextArea text;
	
	public ConditionListener(List<SearchItem> items, JComboBox<Pair<String>> condition, JComboBox<Pair<String>> typeList, JTextArea text) {
		this.text = text;
		this.items = items;
		this.condition = condition;
		this.typeList = typeList;
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		try {
			Pair<String> pair = condition.getItemAt(condition.getSelectedIndex());
			typeList.removeAllItems();
			if (pair != null && pair.getValue() != null) {
				typeList.addItem(new Pair<String>("--- Choose ---", null));
				for (String value : SearchUtil.getListType(items, pair.getValue())) {
					typeList.addItem(new Pair<String>(value, value));
				}
				typeList.updateUI();
			}
			text.setText(text.getText() + "Update list type combo box\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
