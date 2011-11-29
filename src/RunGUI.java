
import javax.swing.*;
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
            	EbayGUI gui = new EbayGUI();
                gui.init();
                gui.setVisible(true);
            }
        });
	}
}
