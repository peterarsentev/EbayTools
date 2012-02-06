package com.ebaytools.start;

import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.EbayGUI;
import com.ebaytools.jobs.FileListSearchingJob;
import com.ebaytools.jobs.UpdateAuctionClosePriceJob;
import com.ebaytools.kernel.dao.ManagerDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
                AppContextManager app = AppContextManager.getInstance();
                ManagerDAO.getInstance();
                Data data = new Data();
            	EbayGUI gui = new EbayGUI(data);
                gui.setTitle("Ebay tools v." + app.getCtx().getBean("ebaytoolsVersion"));
                gui.init();
                gui.setVisible(true);
                FileListSearchingJob.jobs();
                UpdateAuctionClosePriceJob.jobs();
            }
        });
	}
}
