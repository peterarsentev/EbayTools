package linteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

public class ClearListener implements ActionListener {
	private JTextArea text;
	
	public ClearListener(JTextArea text) {
		this.text = text;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			text.setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
