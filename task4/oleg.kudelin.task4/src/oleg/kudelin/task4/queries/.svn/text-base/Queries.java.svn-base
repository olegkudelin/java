/*
* @(#)Queries
*
* @version 1.0 24.08.2013
*
* Copyright notice
*/

package oleg.kudelin.task4.queries;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import oleg.kudelin.task4.connection.Sessions;
import oleg.kudelin.task4.orm.Product;
import oleg.kudelin.task4.orm.Shop;
import oleg.kudelin.task4.orm.ShopProduct;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Получение информации из базы данных
 * 
 * @author Куделин Олег
 *
 */

public class Queries {

	private static ResourceBundle res = ResourceBundle.getBundle("locale");
	private static Logger Log = Logger.getLogger(Queries.class.getName());
	
	/**
	 * Получение списка магазинов, продающих определенный товар
	 * 
	 * @param productName - названия товара
	 * 
	 * @return список названий магазинов или null если была ошибка
	 */	
	@SuppressWarnings("unchecked")
	public Collection<String> getShopListByProductName(String productName) {
		if ((productName == null)) {
			Log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
			return null;
		}
		
		Criteria criteria = getCriteriaForAllShopListByProductName(productName);
		criteria.addOrder(Order.asc("shop.shopName"));
		return getShopNameList(criteria.list());		
	}

	/**
	 * Получение списка магазинов, продающих товар в два раза дороже
	 * 
	 * @param productName - названия товара
	 * 
	 * @return список названий магазинов или null если была ошибка
	 */	
	public Collection<String> getExpensiveShopListByProductName(String productName) {
		if ((productName == null)) {
			Log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
			return null;
		}
		
		Float recommendedPrice = getRecommendPriceForProduct(productName);
		if (recommendedPrice != null) {
			return getShopListByProductNameAndPrice(productName, recommendedPrice * 2); // в два раза дороже
		} else {
			Log.log(Level.WARNING, res.getString("task3.recommendedPriceIsEmpty"));
			return null;
		}
	}

	/**
	 * Получение списка магазинов, продающих товар дешевле всех
	 * 
	 * @param productName - названия товара
	 * 
	 * @return список названий магазинов или null если была ошибка
	 */		
	public Collection<String> getShopListByProductNameWithMinimalPrice(String productName) {
		if ((productName == null)) {
			Log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
			return null;
		}
		
		Float minimalPrice = getMinimalPriceForProduct(productName);
		if (minimalPrice != null) {
			return getShopListByProductNameAndPrice(productName, minimalPrice);
		} else {
			Log.log(Level.WARNING, res.getString("task3.recommendedPriceIsEmpty"));
			return null;
		}
	}	
	
	/**
	 * Получение списка магазинов, содержащих в своем названии подстроку
	 * 
	 * @param filterMaskShopName - подстрока, входящая в название магазина или null если нужны все
	 * 
	 * @return список найденных магазинов
	 */		
	public List<Shop> getFiltredShopListByStringMask(String filterMaskShopName) {
		Session session = Sessions.openSession();
		Criteria criteria = session.createCriteria(Shop.class);
		if ((filterMaskShopName!=null) && (!filterMaskShopName.isEmpty())) {
			criteria.add(Restrictions.like("shopName", "%" + filterMaskShopName + "%"));
		}
		criteria.addOrder(Order.asc("shopName"));
		@SuppressWarnings("unchecked")
		List<Shop> shopList = (List<Shop>) criteria.list();
		session.close();
		return shopList;
	}
	
	/**
	 * Получение списка продуктов, содержащих в своем названии подстроку
	 * 
	 * @param filterMaskShopName - подстрока, входящая в название продукта или null если нужны все
	 * 
	 * @return список найденных продуктов
	 */	
	public List<Product> getFiltredProductListByStringMask(String filterMaskProductName) {
		Session session = Sessions.openSession();
		Criteria criteria = session.createCriteria(Product.class);
		if ((filterMaskProductName!=null) && (!filterMaskProductName.isEmpty())) {
			criteria.add(Restrictions.like("productName", "%" + filterMaskProductName + "%"));
		}
		criteria.addOrder(Order.asc("productName"));
		@SuppressWarnings("unchecked")
		List<Product> productList = (List<Product>) criteria.list();
		session.close();
		return productList;
	}

	/**
	 * Сохранение продуктов
	 * 
	 * @param product - продукт
	 */	
	public void saveProduct(Product product) {
		if (product != null) {
			Session session = Sessions.openSession();
			try {
				session.beginTransaction();
				session.saveOrUpdate(product);
				session.getTransaction().commit();
				session.flush();
			} finally {
				session.close();
			}
		} else {
			Log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
		}
	}	

	/**
	 * Сохранение магазина
	 * 
	 * @param product - магазин
	 */	

	public void saveShop(Shop shop) {
		if (shop != null) {
			Session session = Sessions.openSession();
			try {
				session.beginTransaction();
				session.saveOrUpdate(shop);
				session.getTransaction().commit();
				session.flush();
			} finally {
				session.close();
			}
		} else {
			Log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
		}
	}		

	/**
	 * Удаление продуктов
	 * 
	 * @param productList - Список продктов
	 */	
	public void deleteProduct(List<Product> productList) {
		if (productList != null) {
			Session session = Sessions.openSession();
			try {
				session.beginTransaction();
				for (Product product : productList) {
					session.delete(product);
				}
				session.getTransaction().commit();
				session.flush();
			} finally {
				session.close();
			}
		} else {
			Log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
		}
	}	
	
	/**
	 * Удаление магазинов
	 * 
	 * @param productList - Список магазинов
	 */	
	public void deleteShop(List<Shop> shopList) {
		if (shopList != null) {		
			Session session = Sessions.openSession();
			try {
				session.beginTransaction();
				for (Shop shop : shopList) {
					session.delete(shop);
				}
				session.getTransaction().commit();
				session.flush();
			} finally {
				session.close();
			}
		} else {
			Log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
		}
	}		
	
	
	@SuppressWarnings("unchecked")
	private Collection<String> getShopListByProductNameAndPrice(String productName, Float price) {
		if ((productName == null)||(price == null)) {
			Log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
			return null;
		}
		
		Criteria criteria = getCriteriaForAllShopListByProductName(productName);
		criteria.add(Restrictions.eq("productPrice", price));
		return getShopNameList(criteria.list());
	}
	
	private Criteria getCriteriaForAllShopListByProductName(String productName) {
		Session session = Sessions.openSession();
		Criteria criteria = session.createCriteria(ShopProduct.class);
		criteria.createCriteria("product", "product");
		criteria.createCriteria("shop", "shop");
		criteria.add(Restrictions.like("product.productName", productName));
		return criteria;
	}
	
	private Collection<String> getShopNameList(Collection<ShopProduct> shopProductList) {
		if (shopProductList == null) {
			Log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
			return null;
		}
		Collection<String> resultShopList = new ArrayList<String>();  
		for(ShopProduct shopProduct : shopProductList) {
			resultShopList.add(shopProduct.getShop().getShopName());
		}
		return resultShopList;		
	}
	
	private Float getRecommendPriceForProduct(String productName) {
		Session session = Sessions.openSession();
		Criteria criteria = session.createCriteria(Product.class);
		criteria.add(Restrictions.like("productName", productName));
		@SuppressWarnings("unchecked")
		List<Product> productList = criteria.list();
		if ((productList != null)&&(productList.size() == 1)) {
			return productList.get(0).getRecommendedPrice();
		} else {
			Log.log(Level.WARNING, res.getString("task3.productNotFound"));
			return null;
		}
	}

	private Float getMinimalPriceForProduct(String productName) {
		Session session = Sessions.openSession();
		Criteria criteria = session.createCriteria(ShopProduct.class);
		criteria.createCriteria("product", "product");
		criteria.add(Restrictions.like("product.productName", productName));
		criteria.setProjection(Projections.min("productPrice"));
		return (Float) criteria.uniqueResult();
	}
}
