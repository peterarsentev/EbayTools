import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import panel.SearchPanel;

/**
 * This is main frame. There we can show all gui components. 
 * @author Admin
 *
 */
public class EbayGUI extends JFrame {
	private EbayGUI gui;

	public void init() {
		this.gui = this;
		gui.setTitle("Ebay tools");
		gui.setResizable(false);
		gui.add(new SearchPanel(), BorderLayout.CENTER);
		gui.setSize(700, 500);
		centre(this);
		gui.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		gui.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closePerform();
			}
		});
	}

	public static void centre(Window w) {
		Dimension us = w.getSize();
		Dimension them = Toolkit.getDefaultToolkit().getScreenSize();
		int newX = (them.width - us.width) / 2;
		int newY = (them.height - us.height) / 2;
		w.setLocation(newX, newY);

	}

	public void closePerform() {
		gui.setVisible(false);
		gui.dispose();
		System.exit(0);
	}

}
