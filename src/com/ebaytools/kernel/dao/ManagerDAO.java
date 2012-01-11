package com.ebaytools.kernel.dao;

import com.ebay.services.finding.SearchItem;
import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.ItemProperties;
import com.ebaytools.kernel.entity.Product;
import com.ebaytools.util.Fields;
import com.ebaytools.util.FormatterText;
import com.ebaytools.util.Pair;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ManagerDAO {
    private static ManagerDAO instance;
    private ProductDAO productDAO;
    private ItemDAO itemDAO;
    private ItemPropetiesDAO itemPropetiesDAO;
    private SystemSettingDAO systemSettingDAO;

    private ManagerDAO() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");
        this.productDAO = (ProductDAO) ctx.getBean("productDAO");
        this.itemDAO = (ItemDAO) ctx.getBean("itemDAO");
        this.itemPropetiesDAO = (ItemPropetiesDAO) ctx.getBean("itemPropetiesDAO");
        this.systemSettingDAO = (SystemSettingDAO) ctx.getBean("systemSettingDAO");
    }

    public static synchronized ManagerDAO getInstance() {
        if (instance == null) {
            instance = new ManagerDAO();
        }
        return instance;
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

    public static ItemProperties buildItemProperties(Long itemId, Fields name, String value) {
        ItemProperties itemProperties = new ItemProperties();
        itemProperties.setItemId(itemId);
        itemProperties.setName(name.getKey());
        itemProperties.setValue(value);
        return itemProperties;
    }
}
