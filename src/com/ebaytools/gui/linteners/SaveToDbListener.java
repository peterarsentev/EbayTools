package com.ebaytools.gui.linteners;

import com.ebay.services.finding.SearchItem;
import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.ItemProperties;
import com.ebaytools.kernel.entity.Product;
import com.ebaytools.util.Fields;
import com.ebaytools.util.FormatterText;
import com.ebaytools.util.Pair;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class SaveToDbListener implements ActionListener {
    private Data data;
    private JFrame main;

    public SaveToDbListener(JFrame main, Data data) {
        this.data = data;
        this.main = main;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Map<Pair, List<SearchItem>> result = data.getSaveData();
        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(main, "Data is empty!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            for (Map.Entry<Pair, List<SearchItem>> entry : result.entrySet()) {
                Product product = ManagerDAO.getInstance().getProductDAO().findProductByReferenceId(entry.getKey().getKey());
                Long productId;
                if (product == null) {
                    product = new Product();
                    product.setReferenceId(entry.getKey().getKey());
                    productId = ManagerDAO.getInstance().getProductDAO().create(product);
                } else {
                    productId = product.getId();
                }
                List<String> itemIds = ManagerDAO.getInstance().getItemDAO().getItemEbayIdByProductId(productId);
                Calendar createTime = Calendar.getInstance();
                if (entry.getValue() != null) {
                    for (SearchItem searchItem : entry.getValue()) {
                        if (!itemIds.contains(searchItem.getItemId())) {
                            Item item = new Item();
                            item.setProductId(productId);
                            item.setCreateDate(createTime);
                            item.setEbayItemId(searchItem.getItemId());
                            Long itemId = ManagerDAO.getInstance().getItemDAO().create(item);
                            buildItemProperties(itemId, Fields.AUCTION_CLOSE_TIME, String.valueOf(searchItem.getListingInfo().getEndTime().getTime().getTime()));
                            buildItemProperties(itemId, Fields.AUCTION_PRICE, FormatterText.buildPrice(searchItem.getSellingStatus().getCurrentPrice()));
                            buildItemProperties(itemId, Fields.IS_GOLDEN, String.valueOf(data.getGoldenSearch().isSelected()));
                            buildItemProperties(itemId, Fields.SHIPPING_COST, FormatterText.buildPrice(searchItem.getShippingInfo().getShippingServiceCost()));
                            buildItemProperties(itemId, Fields.TOTAL_COST, FormatterText.addAmount(searchItem.getShippingInfo().getShippingServiceCost(), searchItem.getSellingStatus().getCurrentPrice()));
                        }
                    }
                }
            }
            data.getRefreshAction().actionPerformed(null);
        }
    }

    public static Long buildItemProperties(Long itemId, Fields name, String value) {
        ItemProperties itemProperties = new ItemProperties();
        itemProperties.setItemId(itemId);
        itemProperties.setName(name.getKey());
        itemProperties.setValue(value);
        return ManagerDAO.getInstance().getItemPropetiesDAO().create(itemProperties);
    }
}
