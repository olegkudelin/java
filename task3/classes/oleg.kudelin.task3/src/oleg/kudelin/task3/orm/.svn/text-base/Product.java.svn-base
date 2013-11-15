/*
* @(#)Product
*
* @version 1.0 24.08.2013
*
* Copyright notice
*/

package oleg.kudelin.task3.orm;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * Класс таблицы product
 * 
 */
@Entity
@Table(schema="simbirsoft", name="product")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="product_id")
	private long productId;

	@Column(name="product_name")
	private String productName;

	@Column(name="recommended_price")
	private long recommendedPrice;

	@OneToMany(mappedBy="product")
	private List<ShopProduct> shopProducts;

	public Product() {
	}

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getRecommendedPrice() {
		return this.recommendedPrice;
	}

	public void setRecommendedPrice(long recommendedPrice) {
		this.recommendedPrice = recommendedPrice;
	}

	public List<ShopProduct> getShopProducts() {
		return this.shopProducts;
	}

	public void setShopProducts(List<ShopProduct> shopProducts) {
		this.shopProducts = shopProducts;
	}

}