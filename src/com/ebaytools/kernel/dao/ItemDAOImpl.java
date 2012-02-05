package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.Filter;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.ItemProperties;
import com.ebaytools.util.Fields;
import com.ebaytools.util.FilterDataImpl;
import com.ebaytools.util.TextUtil;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.*;

public class ItemDAOImpl extends HibernateDaoSupport implements ItemDAO {
    @Override
    public Long create(Item item) {
        return (Long) getHibernateTemplate().save(item);
    }

    @Override
    public void update(Item item) {
        getHibernateTemplate().update(item);
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Item find(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized List<Item> getItemsByProductId(Long productId) {
        return getHibernateTemplate().find("from "+Item.class.getName()+" as item where item.productId=? order by item.closeDate", productId);
    }
    

    @Override
    public Map<String, Item> getItemEbayIdByProductId(Long productId) {
        Map<String, Item> map = new LinkedHashMap<String, Item>();
        for (Item item : getItemsByProductId(productId)) {
            map.put(item.getEbayItemId(), item);
        }
        return map;
    }

    @Override
    public List<Item> getProductByFilter(Filter filter, Long productId) {
        Map<Fields, String> conditions = FilterDataImpl.buildConditions(filter.getConditions());
        List<Object> params = new ArrayList<Object>();
        StringBuilder query = new StringBuilder(" from com.ebaytools.kernel.entity.Item as item, com.ebaytools.kernel.entity.ItemProperties as prs where item.id = prs.itemId ");
        if (productId != null) {
            query.append(" and item.productId=?");
            params.add(productId);
        }
        query.append(" and item.closeAuction=?");
        params.add(true);
        if (TextUtil.isNotNull(conditions.get(Fields.CONDITIONS))) {
            String[] values = conditions.get(Fields.CONDITIONS).split(";");
            if (TextUtil.isNotNull(values[0])) {
                query.append(" and (prs.name=? ");
                params.add(Fields.CONDITIONS.getKey());
                if (values.length > 1) {
                    query.append(" and (");
                    Iterator<String> it = new ArrayList<String>(Arrays.asList(values)).iterator();
                    while (it.hasNext()) {
                        String data = it.next();
                        query.append(" prs.value=? ");
                        params.add(data);
                        if (it.hasNext()) {
                            query.append(" or ");
                        }
                    }
                    query.append(") ");
                } else {
                    query.append(" and prs.value=?");
                    params.add(values[0]);
                }
                query.append(") ");
            }
        }

        if (TextUtil.isNotNull(conditions.get(Fields.TIME_OF_DAY))) {
            String[] values = conditions.get(Fields.TIME_OF_DAY).split(";");
            if (values.length > 0) {
                String value = values[0];
                query.append(" and item.closeDate<?");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR, Integer.valueOf(value));
                params.add(calendar);
            }
        }

        if (TextUtil.isNotNull(conditions.get(Fields.IS_GOLDEN_FILTER_FIELD))) {
            String[] values = conditions.get(Fields.IS_GOLDEN_FILTER_FIELD).split(";");
            if (values.length > 0) {
                String value = values[0];
                query.append(" and item.golden=?");
                params.add(Boolean.valueOf(value));
            }
        }

        query.append(" order by item.closeDate");
        List<Object[]> objectArray = getHibernateTemplate().find(query.toString(), params.toArray(new Object[params.size()]));
        List<Item> items = new ArrayList<Item>();
        for (Object[] array : objectArray) {
            Item item = (Item) array[0];
            if (!items.contains(item)) {
                items.add((Item) array[0]);
            }
        }
        return items;
    }

    @Override
    public synchronized List<Item> getAllCloseItems() {
        return getHibernateTemplate().find("from com.ebaytools.kernel.entity.Item as item where item.closeAuction=?", true);
    }

    @Override
    public synchronized List<Item> getAllCloseItemsByProductId(Long productId) {
        return getHibernateTemplate().find("from com.ebaytools.kernel.entity.Item as item where item.productId=? and item.closeAuction=?", productId, true);
    }

    /**
     * This method gets items where close date is expared
     * @return list items
     */
    @Override
    public synchronized List<Item> getItemsAuctionDateExpare() {
        return getHibernateTemplate().find("from " + Item.class.getName() + " as item where item.closeDate<=? and item.closeAuction=?", Calendar.getInstance(), false);
    }
}
