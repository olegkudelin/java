/*
* @(#)Queries
*
* @version 1.0 24.08.2013
*
* Copyright notice
*/

package oleg.kudelin.task3.queries;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import oleg.kudelin.task3.connection.Sessions;
import oleg.kudelin.task3.orm.Product;
import oleg.kudelin.task3.orm.ShopProduct;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * ѕолучение информации из базы данных
 * 
 * @author  уделин ќлег
 *
 */

public class Queries implements AutoCloseable {

	private Session session;
	
	private static ResourceBundle res = ResourceBundle.getBundle("locale");
	private static Logger Log = Logger.getLogger(Queries.class.getName());
	
	public Queries() {
		session = Sessions.openSession();
	}

	@Override
	public void close() throws Exception {
		session.close();
	}

	
	/**
	 * ѕолучение списка магазинов, продающих определенный товар
	 * 
	 * @param productName - названи€ товара
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
	 * ѕолучение списка магазинов, продающих товар в два раза дороже
	 * 
	 * @param productName - названи€ товара
	 * 
	 * @return список названий магазинов или null если была ошибка
	 */	
	public Collection<String> getExpensiveShopListByProductName(String productName) {
		if ((productName == null)) {
			Log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
			return null;
		}
		
		Long recommendedPrice = getRecommendPriceForProduct(productName);
		if (recommendedPrice != null) {
			return getShopListByProductNameAndPrice(productName, recommendedPrice * 2); // в два раза дороже
		} else {
			Log.log(Level.WARNING, res.getString("task3.recommendedPriceIsEmpty"));
			return null;
		}
	}

	/**
	 * ѕолучение списка магазинов, продающих товар дешевле всех
	 * 
	 * @param productName - названи€ товара
	 * 
	 * @return список названий магазинов или null если была ошибка
	 */		
	public Collection<String> getShopListByProductNameWithMinimalPrice(String productName) {
		if ((productName == null)) {
			Log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
			return null;
		}
		
		Long minimalPrice = getMinimalPriceForProduct(productName);
		if (minimalPrice != null) {
			return getShopListByProductNameAndPrice(productName, minimalPrice);
		} else {
			Log.log(Level.WARNING, res.getString("task3.recommendedPriceIsEmpty"));
			return null;
		}
	}	
	
	@SuppressWarnings("unchecked")
	private Collection<String> getShopListByProductNameAndPrice(String productName, Long price) {
		if ((productName == null)||(price == null)) {
			Log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
			return null;
		}
		
		Criteria criteria = getCriteriaForAllShopListByProductName(productName);
		criteria.add(Restrictions.eq("productPrice", price));
		return getShopNameList(criteria.list());
	}
	
	private Criteria getCriteriaForAllShopListByProductName(String productName) {
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
	
	private Long getRecommendPriceForProduct(String productName) {
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

	private Long getMinimalPriceForProduct(String productName) {
		Criteria criteria = session.createCriteria(ShopProduct.class);
		criteria.createCriteria("product", "product");
		criteria.add(Restrictions.like("product.productName", productName));
		criteria.setProjection(Projections.min("productPrice"));
		return (Long) criteria.uniqueResult();
	}
}
