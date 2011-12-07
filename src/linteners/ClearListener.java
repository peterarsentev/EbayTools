package linteners;

import com.ebay.services.finding.SearchItem;
import model.Data;
import util.Pair;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JTextArea;

public class ClearListener implements ActionListener {
	private JTextArea text;
    private Data data;
	
	public ClearListener(JTextArea text, Data data) {
		this.text = text;
        this.data = data;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
            data.setItems(new ArrayList<SearchItem>());
            data.setSaveData(new LinkedHashMap<Pair, List<SearchItem>>());
			text.setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
