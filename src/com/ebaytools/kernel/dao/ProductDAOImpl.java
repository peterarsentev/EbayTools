package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.Product;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

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
}
