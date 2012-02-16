package com.ebaytools.gui.linteners;

import com.ebay.services.finding.SearchItem;
import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.SearchPanel;
import com.ebaytools.util.Pair;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

public class ClearListener implements ActionListener {
	private JEditorPane text;
    private Data data;
	
	public ClearListener(SearchPanel panel) {
		this.text = panel.getText();
        this.data = panel.getData();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
            data.setItems(new LinkedHashMap<SearchItem, Boolean>());
            data.setSaveData(new LinkedHashMap<Pair, Map<SearchItem, Boolean>>());
			text.setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
