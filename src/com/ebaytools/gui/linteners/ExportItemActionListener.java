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
        sb.append("\"referenceid\";\"itemid\";\"golden\";\"closeDate\";\"closePrice\";\"condition\";\"listingType\";\"submitDate\";");
        sb.append("\"sold\";\"totalCost\";\"ShippingCost\";\"AuctionStatus\";\"TotalBid\";\"PositiveFeedBackPercent\";\"FeedBackScore\";");
        sb.append("\"TotalRateSeller\";\"ShipToLocation\";\"HandlingTime\";");
        sb.append("\r\n");
        for (Item item : items) {
            Map<Fields, ItemProperties> values = Fields.buildProperties(item.getProperties());
            sb.append("\"").append(item.getProduct().getReferenceId()).append("\"").append(";");
            sb.append("\"").append(item.getEbayItemId()).append("\"").append(";");
            sb.append("\"").append(item.getGolden()).append("\"").append(";");
            sb.append("\"").append(FormatterText.dateformatter.format(item.getCloseDate().getTime())).append("\"").append(";");
            sb.append("\"").append(values.get(Fields.AUCTION_PRICE).getValue()).append("\"").append(";");
            sb.append("\"").append(values.get(Fields.CONDITIONS).getValue()).append("\"").append(";");
            sb.append("\"").append(values.get(Fields.LISTING_TYPE) != null ? values.get(Fields.LISTING_TYPE).getValue() : "-").append("\"").append(";");
            sb.append("\"").append(FormatterText.dateformatter.format(item.getCreateDate().getTime())).append("\"").append(";");
            sb.append("\"").append(item.getNameStatus()).append("\"").append(";");
            sb.append("\"").append(values.get(Fields.TOTAL_COST).getValue()).append("\"").append(";");
            sb.append("\"").append(values.get(Fields.SHIPPING_COST).getValue()).append("\"").append(";");
            sb.append("\"").append(values.get(Fields.AUCTION_STATUS).getValue()).append("\"").append(";");
            sb.append("\"").append(item.getTotalBid()).append("\"").append(";");
            sb.append("\"").append(values.get(Fields.POSITIVE_FEEDBACK_PERCENT) != null ? values.get(Fields.POSITIVE_FEEDBACK_PERCENT).getValue() : "-").append("\"").append(";");
            sb.append("\"").append(values.get(Fields.FEEDBACK_SCORE) != null ? values.get(Fields.FEEDBACK_SCORE).getValue() : "-").append("\"").append(";");
            sb.append("\"").append(values.get(Fields.SHIP_TO_LOCATION) != null ? values.get(Fields.SHIP_TO_LOCATION).getValue() : "-").append("\"").append(";");
            sb.append("\"").append(values.get(Fields.HANDLING_TIME) != null ? values.get(Fields.HANDLING_TIME).getValue() : "-").append("\"").append(";");
            sb.append("\r\n");
        }
        File file = new File(Calendar.getInstance().getTimeInMillis()+".csv");
        FileUtil.save(file, sb.toString());
        data.getText().append("\nFile was saved successful part : " + file.getAbsolutePath());
    }
}
