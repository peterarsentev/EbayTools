package com.ebaytools.gui.linteners;

import au.com.bytecode.opencsv.CSVWriter;
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
import java.io.StringWriter;
import java.util.ArrayList;
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
        List<String[]> datas = new ArrayList<String[]>();
        datas.add(new String[] {"referenceid", "itemid", "golden", "closeDate", "closePrice", "condition", "listingType", "submitDate",
        "sold", "totalCost", "ShippingCost", "AuctionStatus", "TotalBid", "PositiveFeedBackPercent", "FeedBackScore",
        "TotalRateSeller", "ShipToLocation", "HandlingTime"});
        for (Item item : items) {
            Map<Fields, ItemProperties> values = Fields.buildProperties(item.getProperties());
            datas.add(new String[] {
            item.getProduct().getReferenceId(),
            item.getEbayItemId(),
            String.valueOf(item.getGolden()),
            FormatterText.dateformatter.format(item.getCloseDate().getTime()),
            values.get(Fields.AUCTION_PRICE).getValue(),
            values.get(Fields.CONDITIONS).getValue(),
            values.get(Fields.LISTING_TYPE) != null ? values.get(Fields.LISTING_TYPE).getValue() : "-",
            FormatterText.dateformatter.format(item.getCreateDate().getTime()),
            item.getNameStatus(),
            values.get(Fields.TOTAL_COST).getValue(),
            values.get(Fields.SHIPPING_COST).getValue(),
            values.get(Fields.AUCTION_STATUS).getValue(),
            String.valueOf(item.getTotalBid()),
            values.get(Fields.POSITIVE_FEEDBACK_PERCENT) != null ? values.get(Fields.POSITIVE_FEEDBACK_PERCENT).getValue() : "-",
            values.get(Fields.FEEDBACK_SCORE) != null ? values.get(Fields.FEEDBACK_SCORE).getValue() : "-",
            values.get(Fields.SHIP_TO_LOCATION) != null ? values.get(Fields.SHIP_TO_LOCATION).getValue() : "-",
            values.get(Fields.HANDLING_TIME) != null ? values.get(Fields.HANDLING_TIME).getValue() : "-"
            });
        }
        StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw);
        writer.writeAll(datas);
        File file = new File(Calendar.getInstance().getTimeInMillis()+".csv");
        FileUtil.save(file, sw.toString());
        data.getText().append("\nFile was saved successful part : " + file.getAbsolutePath());
    }
}
