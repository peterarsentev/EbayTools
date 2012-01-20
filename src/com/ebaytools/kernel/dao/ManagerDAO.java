package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.ItemProperties;
import com.ebaytools.util.Fields;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ManagerDAO {
    private static ManagerDAO instance;
    private ProductDAO productDAO;
    private ItemDAO itemDAO;
    private ItemPropetiesDAO itemPropetiesDAO;
    private SystemSettingDAO systemSettingDAO;
    private FilterConditionsDAO filterConditionsDAO;
    private FilterDAO filterDAO;
    private FilterValueDAO filterValueDAO;

    private ManagerDAO() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");
        this.productDAO = (ProductDAO) ctx.getBean("productDAO");
        this.itemDAO = (ItemDAO) ctx.getBean("itemDAO");
        this.itemPropetiesDAO = (ItemPropetiesDAO) ctx.getBean("itemPropetiesDAO");
        this.systemSettingDAO = (SystemSettingDAO) ctx.getBean("systemSettingDAO");
        this.filterDAO = (FilterDAO) ctx.getBean("filterDAO");
        this.filterConditionsDAO = (FilterConditionsDAO) ctx.getBean("filterConditionsDAO");
        this.filterValueDAO = (FilterValueDAO) ctx.getBean("filterValueDAO");
    }

    public static synchronized ManagerDAO getInstance() {
        if (instance == null) {
            instance = new ManagerDAO();
        }
        return instance;
    }

    public FilterConditionsDAO getFilterConditionsDAO() {
        return filterConditionsDAO;
    }

    public FilterDAO getFilterDAO() {
        return filterDAO;
    }

    public FilterValueDAO getFilterValueDAO() {
        return filterValueDAO;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public ItemDAO getItemDAO() {
        return itemDAO;
    }

    public ItemPropetiesDAO getItemPropetiesDAO() {
        return itemPropetiesDAO;
    }

    public SystemSettingDAO getSystemSettingDAO() {
        return systemSettingDAO;
    }

    public static ItemProperties buildItemProperties(Long itemId, Fields name, String value, String type) {
        ItemProperties itemProperties = new ItemProperties();
        itemProperties.setItemId(itemId);
        itemProperties.setName(name.getKey());
        itemProperties.setType(type);
        itemProperties.setValue(value);
        return itemProperties;
    }
}
