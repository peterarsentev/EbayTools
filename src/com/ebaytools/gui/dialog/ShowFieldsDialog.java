package com.ebaytools.gui.dialog;

import com.ebaytools.gui.linteners.SaveShowFieldsListenerAction;
import com.ebaytools.gui.model.Data;
import com.ebaytools.gui.panel.GraphPaperLayout;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.util.Fields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

public class ShowFieldsDialog  extends JDialog {
    private Data data;
    private JDialog dialog;

    public ShowFieldsDialog(JFrame main, Data data) {
        dialog = this;
        dialog.setTitle("Show fields");
        this.data = data;
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        dialog.add(buildChoosePanel(), BorderLayout.CENTER);
        dialog.pack();
        dialog.setSize(250, 400);
        dialog.setLocationRelativeTo(main);
        dialog.setVisible(true);
    }
    
    public static class CheckBoxID extends JCheckBox {
        private String id;
        
        private CheckBoxID(String name, String id) {
            super(name);
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    private JPanel buildChoosePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GraphPaperLayout(new Dimension(10, 18), 1, 1));
        java.util.List<CheckBoxID> boxList = new ArrayList<CheckBoxID>();
        java.util.List<String> showOpts = ManagerDAO.getInstance().getSystemSettingDAO().getSystemsValue(Fields.SHOW_FIELDS.getKey());

        CheckBoxID ref = new CheckBoxID("Reference ID", "ref");
        CheckBoxID soldState = new CheckBoxID("Sold state", "sold_state");
        CheckBoxID ebayItemId = new CheckBoxID("Ebay ItemId", "ebay_item_id");
        CheckBoxID closeAuction = new CheckBoxID("Close Date", "close_date");
        CheckBoxID auctionPrice = new CheckBoxID("Auction Price", "auction_price");
        CheckBoxID totalCost = new CheckBoxID("Total Cost", "total_cost");
        CheckBoxID shippingCost = new CheckBoxID("Shipping Cost", "shipping_cost");
        CheckBoxID auctionStatus = new CheckBoxID("Auction Status", "auction_status");
        CheckBoxID totalBid = new CheckBoxID("Total Bid", "total_bid");
        CheckBoxID golden = new CheckBoxID("Golden", "golden");
        CheckBoxID condition = new CheckBoxID("Conditions", "conditions");
        CheckBoxID positiveFeedbackPercent = new CheckBoxID("Positive Feedback Percent", "positive_feedback_percent");
        CheckBoxID feedbackScore = new CheckBoxID("Feedback Score", "feedback_score");
        CheckBoxID topratedseller = new CheckBoxID("Top rated seller", "top_rated_seller");
        CheckBoxID shipToLocation = new CheckBoxID("Ship to location", "ship_to_location");
        CheckBoxID handlingTime = new CheckBoxID("Handling time", "handling_time");
        CheckBoxID listingType = new CheckBoxID("Listing type", "listing_type");

        boxList.addAll(Arrays.asList(ref, soldState, ebayItemId, closeAuction, auctionPrice,
                totalCost, shippingCost, auctionStatus, totalBid,
                golden, condition, positiveFeedbackPercent, feedbackScore,
                topratedseller, shipToLocation, handlingTime, listingType));

        for (CheckBoxID box : boxList) {
            if (showOpts.contains(box.getId())) {
                box.setSelected(true);
            }
        }
        panel.add(soldState, new Rectangle(0, 0, 19, 1));
        panel.add(ebayItemId, new Rectangle(0, 1, 19, 1));
        panel.add(closeAuction, new Rectangle(0, 2, 19, 1));
        panel.add(auctionPrice, new Rectangle(0, 3, 19, 1));
        panel.add(totalCost, new Rectangle(0, 4, 19, 1));
        panel.add(shippingCost, new Rectangle(0, 5, 19, 1));
        panel.add(auctionStatus, new Rectangle(0, 6, 19, 1));
        panel.add(totalBid, new Rectangle(0, 7, 19, 1));
        panel.add(golden, new Rectangle(0, 8, 19, 1));
        panel.add(condition, new Rectangle(0, 9, 19, 1));
        panel.add(positiveFeedbackPercent, new Rectangle(0, 10, 19, 1));
        panel.add(feedbackScore, new Rectangle(0, 11, 19, 1));
        panel.add(topratedseller, new Rectangle(0, 12, 19, 1));
        panel.add(shipToLocation, new Rectangle(0, 13, 19, 1));
        panel.add(handlingTime, new Rectangle(0, 14, 19, 1));
        panel.add(listingType, new Rectangle(0, 15, 19, 1));
        panel.add(ref, new Rectangle(0, 16, 19, 1));
        
        JButton ok = new JButton("Save as default");
        panel.add(ok, new Rectangle(1, 17, 8, 1));
        ok.addActionListener(new SaveShowFieldsListenerAction(dialog, data, boxList));
        return panel;
    }
}
