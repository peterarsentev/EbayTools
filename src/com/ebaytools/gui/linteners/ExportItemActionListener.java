package com.ebaytools.gui.linteners;

import au.com.bytecode.opencsv.CSVWriter;
import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Filter;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.ItemProperties;
import com.ebaytools.kernel.entity.SystemSetting;
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
        List<Filter> filters = new ArrayList<Filter>();
        List<SystemSetting> settings = ManagerDAO.getInstance().getSystemSettingDAO().getSystemSettingByName(Fields.APPLY_FILTER.getKey());
        for (SystemSetting setting : settings) {
            filters.add(ManagerDAO.getInstance().getFilterDAO().find(Long.valueOf(setting.getValue())));
        }
        if (filters.isEmpty()) {
            Filter sysFilter = new Filter();
            sysFilter.setName("SystemFilter");
            filters.add(sysFilter);
        }
        for (Filter filter : filters) {
//        List<Item> items = ManagerDAO.getInstance().getItemDAO().getItemsAuctionDateExpare();
            List<Item> items = ManagerDAO.getInstance().getItemDAO().getProductByFilter(filter, null);
            List<String[]> datas = new ArrayList<String[]>();
            datas.add(new String[] {"referenceid", "itemid", "golden", "closeDate", "closeAuction", "condition", "listingType", "submitDate",
                    "state", "totalCost", "ShippingCost", "AuctionStatus", "TotalBid", "PositiveFeedBackPercent", "FeedBackScore",
                    "TotalRateSeller", "ShipToLocation", "HandlingTime"});
            List<String> exist = new ArrayList<String>();
            for (Item item : items) {
                if (!exist.contains(item.getEbayItemId())) {
                    Map<Fields, ItemProperties> values = Fields.buildProperties(item.getProperties());
                    datas.add(new String[] {
                            item.getProduct().getReferenceId(),
                            item.getEbayItemId(),
                            String.valueOf(item.getGolden()),
                            FormatterText.dateformatter.format(item.getCloseDate().getTime()),
                            String.valueOf(item.getCloseAuction()),
                            values.get(Fields.CONDITIONS).getValue(),
                            values.get(Fields.LISTING_TYPE) != null ? values.get(Fields.LISTING_TYPE).getValue() : "-",
                            FormatterText.dateformatter.format(item.getCreateDate().getTime()),
                            String.valueOf(item.getNameStatus()),
                            values.get(Fields.TOTAL_COST).getValue(),
                            convertNullValue(values.get(Fields.SHIPPING_COST).getValue()),
                            values.get(Fields.AUCTION_STATUS).getValue(),
                            String.valueOf(item.getTotalBid()),
                            values.get(Fields.POSITIVE_FEEDBACK_PERCENT) != null ? values.get(Fields.POSITIVE_FEEDBACK_PERCENT).getValue() : "-",
                            values.get(Fields.FEEDBACK_SCORE) != null ? values.get(Fields.FEEDBACK_SCORE).getValue() : "-",
                            values.get(Fields.TOP_RATED_SELLER) != null ? values.get(Fields.TOP_RATED_SELLER).getValue() : "-",
                            values.get(Fields.SHIP_TO_LOCATION) != null ? values.get(Fields.SHIP_TO_LOCATION).getValue().replaceAll(","," ") : "-",
                            values.get(Fields.HANDLING_TIME) != null ? values.get(Fields.HANDLING_TIME).getValue() : "-"
                    });
                    exist.add(item.getEbayItemId());
                }
            }
            StringWriter sw = new StringWriter();
            CSVWriter writer = new CSVWriter(sw);
            writer.writeAll(datas);
            File file = new File(filter.getName() + "_" + Calendar.getInstance().getTimeInMillis()+".csv");
            FileUtil.save(file, sw.toString());
            data.getText().setText(data.getText().getText() + "\nFile was saved successful part : " + file.getAbsolutePath());
        }
    }

    private static String convertNullValue(String obj) {
        return obj != null ? obj : "-";
    }
}
