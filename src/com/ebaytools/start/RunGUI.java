package com.ebaytools.start;

import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.EbayGUI;
import com.ebaytools.kernel.dao.ManagerDAO;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.*;

/**
 * This class runs GUI. It does have special code for run com.ebaytools.gui - SwingUtilities.invokeLater(
 * 
 * @author Admin
 *
 */
public class RunGUI {
	public static void main(String[] arg) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ManagerDAO.getInstance();
                Data data = new Data();
            	EbayGUI gui = new EbayGUI(data);
                gui.init();
                gui.setVisible(true);
            }
        });
	}
}
