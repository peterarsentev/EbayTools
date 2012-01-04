package com.ebaytools.gui.linteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.ebaytools.gui.panel.SearchPanel;
import com.ebaytools.util.*;

public class ReferenceIDLinteren implements ActionListener {
	private JTextArea text;
	private JTextField field;
	
	public ReferenceIDLinteren(SearchPanel panel) {
		this.text = panel.getText();
		this.field = panel.getNumbersItem();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			String referenceID = SearchUtil.getInstance().getProductByItemNumber(field.getText()).getProductListingDetails().getProductReferenceID();
			text.setText(text.getText() + "Reference ID : " + referenceID + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
