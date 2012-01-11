package com.ebaytools.kernel.dao;

import com.ebay.services.finding.SearchItem;
import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.ItemProperties;
import com.ebaytools.kernel.entity.Product;
import com.ebaytools.util.Fields;
import com.ebaytools.util.FormatterText;
import com.ebaytools.util.Pair;
import com.ebaytools.util.SearchUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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

    //TODO need to rewrite this awful method
    @Override
    public void create(Map<Pair, List<SearchItem>> map, boolean isGolden) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        for (Map.Entry<Pair, List<SearchItem>> entry : map.entrySet()) {
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
                for (SearchItem searchItem : entry.getValue()) {
                    if (!itemIds.containsKey(searchItem.getItemId())) {
                        Item item = new Item();
                        item.setProductId(productId);
                        item.setCreateDate(createTime);
                        item.setEbayItemId(searchItem.getItemId());
                        Long itemId = (Long) session.save(item);
                        session.save(ManagerDAO.buildItemProperties(itemId, Fields.AUCTION_CLOSE_TIME, String.valueOf(searchItem.getListingInfo().getEndTime().getTime().getTime())));
                        session.save(ManagerDAO.buildItemProperties(itemId, Fields.AUCTION_PRICE, FormatterText.buildPrice(searchItem.getSellingStatus().getCurrentPrice())));
                        session.save(ManagerDAO.buildItemProperties(itemId, Fields.IS_GOLDEN, String.valueOf(isGolden)));
                        session.save(ManagerDAO.buildItemProperties(itemId, Fields.SHIPPING_COST, FormatterText.buildPrice(searchItem.getShippingInfo().getShippingServiceCost())));
                        session.save(ManagerDAO.buildItemProperties(itemId, Fields.TOTAL_COST, FormatterText.addAmount(searchItem.getShippingInfo().getShippingServiceCost(), searchItem.getSellingStatus().getCurrentPrice())));
                        session.save(ManagerDAO.buildItemProperties(itemId, Fields.AUCTION_STATUS, searchItem.getSellingStatus().getSellingState()));
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
                            for (ItemProperties prs : item.getProperties()) {
                                if (Fields.AUCTION_PRICE.getKey().equals(prs.getName())) {
                                    prs.setValue(itemType.getSellingStatus().getCurrentPrice().getValue() + " " + itemType.getSellingStatus().getCurrentPrice().getCurrencyID());
                                }
                                if (Fields.AUCTION_STATUS.getKey().equals(prs.getName())) {
                                    prs.setValue(itemType.getSellingStatus().getListingStatus().name());
                                }
                                session.update(prs);
                            }
                        } else {
                            session.delete(itemIds.get(id));
                        }
                    }
                }
            }
        }
        tx.commit();
        session.close();
    }
}
