/*
* @(#)Product
*
* @version 1.0 24.08.2013
*
* Copyright notice
*/

package oleg.kudelin.task4.orm;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * Класс таблицы product
 * 
 */
@Entity
@Table(schema="simbirsoft", name="product")
@Cacheable(true)
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="product_id")
	private int productId;

	@Column(name="product_name")
	private String productName;

	@Column(name="recommended_price", precision = 10, scale = 2)
	private Float recommendedPrice;

	@OneToMany(mappedBy="product", fetch = FetchType.LAZY)
	private List<ShopProduct> shopProducts;

	public Product() {
	}

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Float getRecommendedPrice() {
		return this.recommendedPrice;
	}

	public void setRecommendedPrice(Float recommendedPrice) {
		this.recommendedPrice = recommendedPrice;
	}

	public List<ShopProduct> getShopProducts() {
		return this.shopProducts;
	}

	public void setShopProducts(List<ShopProduct> shopProducts) {
		this.shopProducts = shopProducts;
	}

}