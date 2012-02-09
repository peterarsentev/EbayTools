package com.ebaytools.kernel.dao;

import com.ebay.services.finding.SearchItem;
import com.ebay.services.finding.SellerInfo;
import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.ItemProperties;
import com.ebaytools.kernel.entity.Product;
import com.ebaytools.util.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.*;

public class ProductDAOImpl extends HibernateDaoSupport implements ProductDAO {
    @Override
    public Long create(Product product) {
        return (Long) getHibernateTemplate().save(product);
    }

    @Override
    public void update(Product product) {
        getHibernateTemplate().update(product);
    }

    @Override
    public void delete(Long id) {
        getHibernateTemplate().delete(find(id));
    }

    @Override
    public Product find(Long id) {
        return getHibernateTemplate().get(Product.class, id);
    }

    @Override
    public synchronized List<Product> getAllProduct() {
        return getHibernateTemplate().find("from com.ebaytools.kernel.entity.Product");
    }

    @Override
    public List<Object[]> getProducts() {
        return getHibernateTemplate().find("select p.id, p.referenceId, p.name from com.ebaytools.kernel.entity.Product as p");
    }

    @Override
    public Product findProductByReferenceId(String referenceId) {
        List<Product> products = getHibernateTemplate().find("from com.ebaytools.kernel.entity.Product as p where p.referenceId=?", referenceId);
        if (products.isEmpty()) {
            return null;
        } else if (products.size() > 1) {
            throw new IllegalMonitorStateException("System has multi products with refereceId = " + referenceId);
        }  else {
            return products.get(0);
        }
    }

    /**
     * This method creates product object. To set synchronized because system uses this method in schedule job as separete thread
     * @param map result
     */
    //TODO need to rewrite this awful method
    @Override
    public synchronized void create(Map<Pair, Map<SearchItem, Boolean>> map) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        for (Map.Entry<Pair, Map<SearchItem, Boolean>> entry : map.entrySet()) {
            boolean isItems = entry.getValue() != null && !entry.getValue().isEmpty();
            Product product = findProductByReferenceId(entry.getKey().getKey());
            Long productId;
            if (product == null) {
                product = new Product();
                product.setReferenceId(entry.getKey().getKey());
                product.setName(SearchUtil.getInstance().getTitle(entry.getKey().getKey()));
                productId = (Long) session.save(product);

            } else {
                productId = product.getId();
            }
            Map<String, Item> itemIds = ManagerDAO.getInstance().getItemDAO().getItemEbayIdByProductId(productId);
            Calendar createTime = Calendar.getInstance();
            if (isItems) {
                for (Map.Entry<SearchItem, Boolean> innerEntry : entry.getValue().entrySet()) {
                    SearchItem searchItem = innerEntry.getKey();
                    if (!itemIds.containsKey(searchItem.getItemId())) {
                        Item item = new Item();
                        item.setProductId(productId);
                        item.setCreateDate(createTime);
                        item.setCloseDate(searchItem.getListingInfo().getEndTime());
                        item.setEbayItemId(searchItem.getItemId());
                        Integer totalBid = searchItem.getSellingStatus().getBidCount();
                        item.setTotalBid(totalBid != null ? totalBid : 0);
                        boolean closeAuction = "COMPLETED".equals(searchItem.getSellingStatus().getSellingState());
                        item.setCloseAuction(closeAuction);
                        item.setGolden(innerEntry.getValue());
                        Long itemId = (Long) session.save(item);
                        if (closeAuction) {
                            session.save(ManagerDAO.buildItemProperties(itemId, Fields.AUCTION_PRICE, FormatterText.getPrice(searchItem.getSellingStatus().getCurrentPrice()), FormatterText.getCurrency(searchItem.getSellingStatus().getCurrentPrice())));
                        } else {
                            session.save(ManagerDAO.buildItemProperties(itemId, Fields.AUCTION_PRICE, null, null));
                        }
                        session.save(ManagerDAO.buildItemProperties(itemId, Fields.SHIPPING_COST, FormatterText.getPrice(searchItem.getShippingInfo().getShippingServiceCost()), FormatterText.getCurrency(searchItem.getShippingInfo().getShippingServiceCost())));
                        session.save(ManagerDAO.buildItemProperties(itemId, Fields.TOTAL_COST, FormatterText.addAmount(searchItem.getShippingInfo().getShippingServiceCost(), searchItem.getSellingStatus().getCurrentPrice()), FormatterText.getCurrency(searchItem.getShippingInfo().getShippingServiceCost())));
                        session.save(ManagerDAO.buildItemProperties(itemId, Fields.AUCTION_STATUS, searchItem.getSellingStatus().getSellingState(), null));
                        session.save(ManagerDAO.buildItemProperties(itemId, Fields.CONDITIONS, String.valueOf(searchItem.getCondition().getConditionId()), null));
                        session.save(ManagerDAO.buildItemProperties(itemId, Fields.TOTAL_BID, String.valueOf(searchItem.getSellingStatus().getBidCount()), null));
                        SellerInfo sellerInfo = searchItem.getSellerInfo();
                        if (sellerInfo != null) {
                            session.save(ManagerDAO.buildItemProperties(itemId, Fields.POSITIVE_FEEDBACK_PERCENT, String.valueOf(sellerInfo.getPositiveFeedbackPercent()), null));
                            session.save(ManagerDAO.buildItemProperties(itemId, Fields.FEEDBACK_SCORE, String.valueOf(sellerInfo.getFeedbackScore()), null));
                            session.save(ManagerDAO.buildItemProperties(itemId, Fields.TOP_RATED_SELLER, String.valueOf(sellerInfo.isTopRatedSeller()), null));
                        }
                        session.save(ManagerDAO.buildItemProperties(itemId, Fields.SHIP_TO_LOCATION, String.valueOf(searchItem.getShippingInfo().getShipToLocations()), null));
                        session.save(ManagerDAO.buildItemProperties(itemId, Fields.HANDLING_TIME, String.valueOf(searchItem.getShippingInfo().getHandlingTime()), null));
                        session.save(ManagerDAO.buildItemProperties(itemId, Fields.LISTING_TYPE, String.valueOf(searchItem.getListingInfo().getListingType()), null));
                    }
                }
            }
        }
        tx.commit();
        session.close();
    }
}
