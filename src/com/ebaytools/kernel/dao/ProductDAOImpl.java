package com.ebaytools.kernel.dao;

import com.ebay.services.finding.SearchItem;
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
    public List<Product> getAllProduct() {
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
            Product product = findProductByReferenceId(entry.getKey().getKey());
            Long productId;
            if (product == null) {
                product = new Product();
                product.setReferenceId(entry.getKey().getKey());
                productId = (Long) session.save(product);
            } else {
                productId = product.getId();
            }
            Map<String, Item> itemIds = ManagerDAO.getInstance().getItemDAO().getItemEbayIdByProductId(productId);
            Calendar createTime = Calendar.getInstance();
            if (entry.getValue() != null) {
                List<String> useIds = new ArrayList<String>();
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
                    }
                    useIds.add(searchItem.getItemId());
                }

                List<String> endedAuction = new ArrayList<String>();
                for (String id : itemIds.keySet()) {
                    if (!useIds.contains(id)) {
                        boolean notEndedYet = true;
                        for (ItemProperties prs : itemIds.get(id).getProperties()) {
                            if (Fields.AUCTION_STATUS.getKey().equals(prs.getName()) && "COMPLETED".equals(prs.getValue())) {
                                notEndedYet = false;
                            }
                        }
                        if (notEndedYet) {
                            endedAuction.add(id);
                        }
                    }
                }

                for (String id : endedAuction) {
                    ItemType itemType = SearchUtil.getInstance().getProductByItemNumber(id);
                    if (itemType != null) {
                        if (itemType.getSellingStatus().getListingStatus().name().equals("COMPLETED")) {
                            Item item = itemIds.get(id);
                            Map<Fields, ItemProperties> prMap = buildProperties(item.getProperties());

                            ItemProperties prAuction = prMap.get(Fields.AUCTION_PRICE);
                            prAuction.setValue(String.valueOf(itemType.getSellingStatus().getCurrentPrice().getValue()));
                            prAuction.setType(String.valueOf(itemType.getSellingStatus().getCurrentPrice().getCurrencyID()));
                            session.update(prAuction);

                            ItemProperties prStatus = prMap.get(Fields.AUCTION_STATUS);
                            prStatus.setValue(itemType.getSellingStatus().getListingStatus().name());
                            session.update(prStatus);

                            ItemProperties prTotal = prMap.get(Fields.TOTAL_COST);
                            String shippingValue = prMap.get(Fields.SHIPPING_COST).getValue();
                            String priceCost = prMap.get(Fields.AUCTION_PRICE).getValue();
                            float cost = TextUtil.getFloarOrZero(shippingValue) + TextUtil.getFloarOrZero(priceCost);
                            prTotal.setValue(String.valueOf(cost));
                            session.update(prTotal);
                            item.setCloseAuction(true);
                            session.update(item);
                        }
                    } else {
                        session.delete(itemIds.get(id));
                    }
                }
            }
        }
        tx.commit();
        session.close();
    }
    
    public static Map<Fields, ItemProperties> buildProperties(Set<ItemProperties> propertiesSet) {
        Map<Fields, ItemProperties> map = new LinkedHashMap<Fields, ItemProperties>();
        for (ItemProperties properties : propertiesSet) {
            fullingValue(Fields.AUCTION_STATUS, properties, map);
            fullingValue(Fields.AUCTION_PRICE, properties, map);
            fullingValue(Fields.SHIPPING_COST, properties, map);
            fullingValue(Fields.TOTAL_COST, properties, map);
            fullingValue(Fields.CONDITIONS, properties, map);
            fullingValue(Fields.AUCTION_PRICE, properties, map);
        }
        return map;
    }

    private static void fullingValue(Fields fields, ItemProperties properties, Map<Fields, ItemProperties> map) {
        if (fields.getKey().equals(properties.getName())) {
            map.put(fields, properties);
        }
    }
}
