/*
 * @(#)DataBaseObjectList
 *
 * @version 1.0 08.09.2013
 *
 * Copyright notice
 */
package oleg.kudelin.task5.lists;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import oleg.kudelin.task5.orm.Product;
import oleg.kudelin.task5.orm.Shop;

/**
 * Инкапсулирует взаимодействие с базой данных
 *
 * @author Куделин Олег
 *
 */
@Named("databaseobjectlist")
@ApplicationScoped
@ManagedBean(eager = true)
public class DataBaseObjectList implements Serializable {

    private static ResourceBundle res = ResourceBundle.getBundle("locale");
    private static final Logger Log = Logger.getLogger(DataBaseObjectList.class.getName());
    private static final long serialVersionUID = 6607449623411289953L;
    private List<Shop> shopList;
    private List<Product> productList;
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void initialization() {
        loadShopList();
        loadProductList();
    }

    /**
     * Возвращает текущий список продуктов
     *
     * @return список
     */
    public List<Product> getProductList() {
        return productList;
    }

    /**
     * Устанавливает список продуктов
     *
     * @return список
     */
    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    /**
     * Возвращает текущий список магазинов
     *
     * @return список
     */
    public List<Shop> getShopList() {
        return shopList;
    }

    /**
     * Обновляет из базы текущий список магазинов
     *
     */
    public void reloadShopList() {
        loadShopList();
    }

    /**
     * Обновляет из базы текущий список продуктов
     *
     */
    public void reloadProductList() {
        loadProductList();
    }

    /**
     * Обновляет или добавляет магазин в базу
     *
     * @param shop Магазин
     */
    public synchronized void saveOrUpdateShop(Shop shop) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Shop shopInEntity = entityManager.find(Shop.class, shop.getShopId());
            if (shopInEntity != null) {
                entityManager.getTransaction().begin();
                shopInEntity.setShopName(shop.getShopName());
                entityManager.getTransaction().commit();
            } else {
                entityManager.getTransaction().begin();
                entityManager.persist(shop);
                entityManager.getTransaction().commit();
                shopList.add(shop);
            }
        } finally {
            entityManager.close();
        }
    }

    /**
     * Обновляет или добавляет продукт в базу
     *
     * @param product Товар
     */
    public synchronized void saveOrUpdateProduct(Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Product productInEntity = entityManager.find(Product.class, product.getProductId());
            if (productInEntity != null) {
                entityManager.getTransaction().begin();
                productInEntity.setProductName(product.getProductName());
                productInEntity.setRecommendedPrice(product.getRecommendedPrice());
                entityManager.getTransaction().commit();
            } else {
                entityManager.getTransaction().begin();
                entityManager.persist(product);
                entityManager.getTransaction().commit();
                productList.add(product);
            }
        } finally {
            entityManager.close();
        }
    }

    /**
     * Удаляет магазин из базы
     *
     * @param shop Магазин
     */
    public synchronized void deleteShop(Shop shop) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Shop shopInEntity = entityManager.find(Shop.class, shop.getShopId());
            if (shopInEntity != null) {
                entityManager.getTransaction().begin();
                entityManager.remove(shopInEntity);
                entityManager.getTransaction().commit();
                shopList.remove(shop);
            } else {
                Log.log(Level.WARNING, res.getString("shopNotFound"));
            }
        } finally {
            entityManager.close();
        }
    }

    /**
     * Удаляет продукты из базы
     *
     * @param product Товар
     */
    public synchronized void deleteProduct(Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Product productInEntity = entityManager.find(Product.class, product.getProductId());
            if (productInEntity != null) {
                entityManager.getTransaction().begin();
                entityManager.remove(productInEntity);
                entityManager.getTransaction().commit();
                productList.remove(product);
            } else {
                Log.log(Level.WARNING, res.getString("task3.productNotFound"));
            }
        } finally {
            entityManager.close();
        }
    }

    private synchronized void loadShopList() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaQuery<Shop> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Shop.class);
            Root<Shop> shop = criteriaQuery.from(Shop.class);
            criteriaQuery.select(shop);
            criteriaQuery.orderBy(entityManager.getCriteriaBuilder().asc(shop.get("shopName")));
            shopList = entityManager.createQuery(criteriaQuery).getResultList();
        } finally {
            entityManager.close();
        }
    }

    private synchronized void loadProductList() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaQuery<Product> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Product.class);
            Root<Product> shop = criteriaQuery.from(Product.class);
            criteriaQuery.select(shop);
            criteriaQuery.orderBy(entityManager.getCriteriaBuilder().asc(shop.get("productName")));
            productList = entityManager.createQuery(criteriaQuery).getResultList();
        } finally {
            entityManager.close();
        }
    }
}
