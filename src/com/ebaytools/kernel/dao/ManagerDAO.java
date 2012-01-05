package com.ebaytools.kernel.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ManagerDAO {
    private static ManagerDAO instance;
    private ProductDAO productDAO;
    private ItemDAO itemDAO;
    private ItemPropetiesDAO itemPropetiesDAO;

    private ManagerDAO() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");
        this.productDAO = (ProductDAO) ctx.getBean("productDAO");
        this.itemDAO = (ItemDAO) ctx.getBean("itemDAO");
        this.itemPropetiesDAO = (ItemPropetiesDAO) ctx.getBean("itemPropetiesDAO");
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
}
