package com.ebaytools.gui.linteners;

import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.ItemProperties;
import com.ebaytools.util.Fields;
import com.ebaytools.util.FileUtil;
import com.ebaytools.util.FormatterText;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ExportItemActionListener implements ActionListener {
    private Data data;
    private JFrame main;
    
    public ExportItemActionListener(JFrame main, Data data) {
        this.main = main;
        this.data = data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Item> items = ManagerDAO.getInstance().getItemDAO().getAllItems();
        StringBuilder sb = new StringBuilder();
        sb.append("referenceid;itemid;golden;closeDate;closePrice;condition;listingType;submitDate;");
        sb.append("sold;totalCost;ShippingCost;AuctionStatus;TotalBid;PositiveFeedBackPercent;FeedBackScore;");
        sb.append("TotalRateSeller;ShipToLocation;HandlingTime");
        sb.append("\r\n");
        for (Item item : items) {
            Map<Fields, ItemProperties> values = Fields.buildProperties(item.getProperties());
            sb.append(item.getProduct().getReferenceId()).append(";");
            sb.append(item.getEbayItemId()).append(";");
            sb.append(item.getGolden()).append(";");
            sb.append(FormatterText.dateformatter.format(item.getCloseDate().getTime())).append(";");
            sb.append(values.get(Fields.AUCTION_PRICE).getValue()).append(";");
            sb.append(values.get(Fields.CONDITIONS).getValue()).append(";");
            sb.append(values.get(Fields.LISTING_TYPE).getValue()).append(";");
            sb.append(FormatterText.dateformatter.format(item.getCreateDate().getTime())).append(";");
            sb.append(item.getNameStatus()).append(";");
            sb.append(values.get(Fields.TOTAL_COST).getValue()).append(";");
            sb.append(values.get(Fields.SHIPPING_COST).getValue()).append(";");
            sb.append(values.get(Fields.AUCTION_STATUS).getValue()).append(";");
            sb.append(item.getTotalBid()).append(";");
            sb.append(values.get(Fields.POSITIVE_FEEDBACK_PERCENT).getValue()).append(";");
            sb.append(values.get(Fields.FEEDBACK_SCORE).getValue()).append(";");
            sb.append(values.get(Fields.TOP_RATED_SELLER).getValue()).append(";");
            sb.append(values.get(Fields.SHIP_TO_LOCATION).getValue()).append(";");
            sb.append(values.get(Fields.HANDLING_TIME).getValue()).append(";");
            sb.append("\r\n");
        }
        File file = new File(Calendar.getInstance().getTimeInMillis()+".csv");
        FileUtil.save(file, sb.toString());
        data.getText().append("\nFile was saved successful part : " + file.getAbsolutePath());
    }
}
