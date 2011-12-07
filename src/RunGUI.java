
import model.Data;
import panel.EbayGUI;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.*;

/**
 * This class runs GUI. It does have special code for run gui - SwingUtilities.invokeLater(
 * 
 * @author Admin
 *
 */
public class RunGUI {
	public static void main(String[] arg) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Data data = new Data();
                Map<String, Boolean> showOps = new LinkedHashMap<String, Boolean>();
                showOps.put("itemId", true);
                data.setShowOpts(showOps);
            	EbayGUI gui = new EbayGUI(data);
                gui.init();
                gui.setVisible(true);
            }
        });
	}
}
