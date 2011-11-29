package linteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import util.*;

public class ReferenceIDLinteren implements ActionListener {
	private JTextArea text;
	private SearchUtil util;
	private JTextField field;
	
	public ReferenceIDLinteren(SearchUtil util, JTextField field, JTextArea text) {
		this.text = text;
		this.util = util;
		this.field = field;
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
