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
        ManagerDAO.lock.writeLock().lock();
        try {
            return (Long) getHibernateTemplate().save(item);
        } finally {
            ManagerDAO.lock.writeLock().unlock();
        }
    }

    @Override
    public void update(Item item) {
        ManagerDAO.lock.writeLock().lock();
        try {
            getHibernateTemplate().update(item);
        } finally {
            ManagerDAO.lock.writeLock().unlock();
        }
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
    public List<Item> getItemsByProductId(Long productId) {
        ManagerDAO.lock.readLock().lock();
        try {
            return getHibernateTemplate().find("from "+Item.class.getName()+" as item where item.productId=? order by item.closeDate", productId);
        } finally {
            ManagerDAO.lock.readLock().unlock();
        }
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
    public List<Item> getProductByFilter(Filter filter, List<Long> productId) {
        ManagerDAO.lock.readLock().lock();
        try {
            Map<Fields, String> conditions = FilterDataImpl.buildConditions(filter.getConditions());
            List<Object> params = new ArrayList<Object>();
            List<String> names = new ArrayList<String>();
            StringBuilder query = new StringBuilder(" from com.ebaytools.kernel.entity.Item as item, com.ebaytools.kernel.entity.ItemProperties as prs where item.id = prs.itemId ");
            if (productId != null && !productId.isEmpty()) {
                query.append(" and item.productId in (:prsids)");
                names.add("prsids");
                params.add(productId);
            }
            if (TextUtil.isNotNull(conditions.get(Fields.CONDITIONS))) {
                String[] values = conditions.get(Fields.CONDITIONS).split(";");
                if (TextUtil.isNotNull(values[0])) {
                    query.append(" and (prs.name=:condName");
                    names.add("condName");
                    params.add(Fields.CONDITIONS.getKey());
                    if (values.length > 1) {
                        query.append(" and (");
                        Iterator<String> it = new ArrayList<String>(Arrays.asList(values)).iterator();
                        int inx = 0;
                        while (it.hasNext()) {
                            String data = it.next();
                            query.append(" prs.value=:condValue").append(inx);
                            names.add("condValue"+inx);
                            params.add(data);
                            ++inx;
                            if (it.hasNext()) {
                                query.append(" or ");
                            }
                        }
                        query.append(") ");
                    } else {
                        query.append(" and prs.value=:condValue");
                        names.add("condValue");
                        params.add(values[0]);
                    }
                    query.append(") ");
                }
            }

            if (TextUtil.isNotNull(conditions.get(Fields.TIME_OF_DAY))) {
                String[] values = conditions.get(Fields.TIME_OF_DAY).split(";");
                if (values.length > 0) {
                    String value = values[0];
                    query.append(" and item.closeDate<:closeDate");
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.HOUR, Integer.valueOf(value));
                    names.add("closeDate");
                    params.add(calendar);
                }
            }

            if (TextUtil.isNotNull(conditions.get(Fields.IS_GOLDEN_FILTER_FIELD))) {
                String[] values = conditions.get(Fields.IS_GOLDEN_FILTER_FIELD).split(";");
                if (values.length > 0) {
                    Boolean value = "true".equals(values[0]) || "false".equals(values[0]) ? Boolean.valueOf(values[0]) : null;
                    if (value != null) {
                        query.append(" and item.golden=:golden");
                        params.add(value);
                        names.add("golden");
                    }
                }
            }

            if (TextUtil.isNotNull(conditions.get(Fields.SOLD))) {
                String[] values = conditions.get(Fields.SOLD).split(";");
                if (values.length > 0) {
                    Boolean value = "true".equals(values[0]) || "false".equals(values[0]) ? Boolean.valueOf(values[0]) : null;
                    if (value != null) {
                        query.append(" and item.state=:state");
                        if (value) {
                            params.add(Item.Status.CLOSE.key);
                        } else {
                            params.add(Item.Status.UNSOLD.key);
                        }
                        names.add("state");
                    }
                }
            } else {
                query.append(" and item.closeAuction=:closeAuction");
                names.add("closeAuction");
                params.add(true);
            }

            query.append(" order by item.closeDate");
            List<Object[]> objectArray = getHibernateTemplate().findByNamedParam(query.toString(), names.toArray(new String[names.size()]), params.toArray(new Object[params.size()]));
            List<Item> items = new ArrayList<Item>();
            for (Object[] array : objectArray) {
                Item item = (Item) array[0];
                if (!items.contains(item)) {
                    items.add((Item) array[0]);
                }
            }
            return items;
        } finally {
            ManagerDAO.lock.readLock().unlock();
        }
    }

    @Override
    public List<Item> getAllCloseItems() {
        ManagerDAO.lock.readLock().lock();
        try {
            return getHibernateTemplate().find("from com.ebaytools.kernel.entity.Item as item where item.closeAuction=? and item.state=?", true, 1);
        } finally {
            ManagerDAO.lock.readLock().unlock();
        }
    }

    @Override
    public List<Item> getAllCloseItemsByProductId(Long productId) {
        ManagerDAO.lock.readLock().lock();
        try {
            return getHibernateTemplate().find("from com.ebaytools.kernel.entity.Item as item where item.productId=? and item.closeAuction=?", productId, true);
        } finally {
            ManagerDAO.lock.readLock().unlock();
        }
    }

    /**
     * This method gets items where close date is expared
     * @return list items
     */
    @Override
    public List<Item> getItemsAuctionDateExpare() {
        ManagerDAO.lock.readLock().lock();
        try {
            return getHibernateTemplate().find("from " + Item.class.getName() + " as item where item.closeDate<=? and item.closeAuction!=? and (item.state=? or item.state is null)", Calendar.getInstance(), true, Item.Status.PROCESS_UPDATE.key);
        } finally {
            ManagerDAO.lock.readLock().unlock();
        }
    }

    @Override
    public List<Item> getAllItems() {
        ManagerDAO.lock.readLock().lock();
        try {
            return getHibernateTemplate().find("from " + Item.class.getName() + " as item order by item.productId");
        } finally {
            ManagerDAO.lock.readLock().unlock();
        }
    }
}