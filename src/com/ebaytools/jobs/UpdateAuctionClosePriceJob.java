package com.ebaytools.jobs;

import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.dao.ProductDAOImpl;
import com.ebaytools.kernel.entity.FileSearching;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.ItemProperties;
import com.ebaytools.util.Fields;
import com.ebaytools.util.SearchUtil;
import com.ebaytools.util.TextUtil;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class UpdateAuctionClosePriceJob {
    private static final Logger log = Logger.getLogger(UpdateAuctionClosePriceJob.class);

    public static void jobs() {
        new Thread() {
            @Override
            public void run() {
                WorkerPool pool = new WorkerPool(1, 0, 500, 30);
                while (true) {
                    log.debug("start");
                    try {
                        List<Item> items = ManagerDAO.getInstance().getItemDAO().getItemsAuctionDateExpare();
                        for (Item item : items) {
                            int result = pool.process(new ItemWorker(item.getId(), item));
                            if (result == 1) {
                                item.setState(Item.Status.PROCESS_UPDATE.key);
                                ManagerDAO.getInstance().getItemDAO().update(item);
                            }
                        }
                        //Thread.sleep(5 * 60 * 1000); //wait 5 min
                        Thread.sleep(60 * 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("Error", e);
                    }
                    log.debug("finish");
                }
            }
        }.start();
    }

    private static class ItemWorker extends WorkerPool.Worker {
        private Item item;
        private Long id;

        private ItemWorker(Long id, Item item) {
            super(id);
            this.item = item;
        }

        private ItemWorker(Long id) {
            super(id);
        }

        @Override
        public void run() {
            try {
                updateAuctionClosePrice(item);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error", e);
            }
        }
    }

    /**
     * This method updates price for close auction
     * @param item Item
     */
    public static void updateAuctionClosePrice(Item item) {
        log.debug("Get close auction price for " + item);
        ItemType itemType = SearchUtil.getInstance().getProductByItemNumber(item.getEbayItemId());
        Map<Fields, ItemProperties> prMap = Fields.buildProperties(item.getProperties());
        if (itemType != null) {
            Integer totalBid = itemType.getSellingStatus().getBidCount();
            totalBid = totalBid != null ? totalBid : 0;
            String auctionStatus = itemType.getSellingStatus().getListingStatus().name(); 
            if (totalBid > 0 && "COMPLETED".equalsIgnoreCase(auctionStatus)) {

                ItemProperties prAuction = prMap.get(Fields.AUCTION_PRICE);
                prAuction.setValue(String.valueOf(itemType.getSellingStatus().getCurrentPrice().getValue()));
                prAuction.setType(String.valueOf(itemType.getSellingStatus().getCurrentPrice().getCurrencyID()));
                //ManagerDAO.getInstance().getItemPropetiesDAO().update(prAuction);

                ItemProperties prStatus = prMap.get(Fields.AUCTION_STATUS);
                prStatus.setValue(itemType.getSellingStatus().getListingStatus().name());
                //ManagerDAO.getInstance().getItemPropetiesDAO().update(prStatus);

                ItemProperties prTotalCost = prMap.get(Fields.TOTAL_COST);
                String shippingValue = prMap.get(Fields.SHIPPING_COST).getValue();
                String priceCost = prMap.get(Fields.AUCTION_PRICE).getValue();
                float cost = TextUtil.getFloarOrZero(shippingValue) + TextUtil.getFloarOrZero(priceCost);
                prTotalCost.setValue(String.valueOf(cost));
                //ManagerDAO.getInstance().getItemPropetiesDAO().update(prTotalCost);

                item.setCloseAuction(true);
                item.setState(Item.Status.CLOSE.key);
                item.setTotalBid(totalBid);
                
                ManagerDAO.getInstance().getItemDAO().update(item);
                log.debug("Finish close auction price for " + item);
            } else {
                ItemProperties prAuction = prMap.get(Fields.AUCTION_PRICE);
                prAuction.setValue(String.valueOf(itemType.getSellingStatus().getCurrentPrice().getValue()));
                prAuction.setType(String.valueOf(itemType.getSellingStatus().getCurrentPrice().getCurrencyID()));

                item.setState(Item.Status.UNSOLD.key);
                item.setCloseAuction(false);
                ManagerDAO.getInstance().getItemDAO().update(item);
                log.debug("Unsold close auction price for " + item);
            }
        } else {
            item.setState(Item.Status.UNSOLD.key);
            item.setCloseAuction(false);
            ManagerDAO.getInstance().getItemDAO().update(item);
            log.debug("Unsold close auction price for " + item);
        }
    }
}
