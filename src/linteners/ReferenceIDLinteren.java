package linteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import panel.SearchPanel;
import util.*;

public class ReferenceIDLinteren implements ActionListener {
	private JTextArea text;
	private SearchUtil util;
	private JTextField field;
	
	public ReferenceIDLinteren(SearchPanel panel) {
		this.text = panel.getText();
		this.util = panel.getUtil();
		this.field = panel.getNumbersItem();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			Integer referenceID = util.getReferenceID(field.getText());
			text.setText(text.getText() + "Reference ID : " + referenceID + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
