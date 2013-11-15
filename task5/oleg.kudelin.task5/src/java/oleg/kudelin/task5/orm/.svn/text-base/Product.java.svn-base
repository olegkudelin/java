/*
* @(#)Product
*
* @version 1.0 24.08.2013
*
* Copyright notice
*/

package oleg.kudelin.task5.orm;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;
import javax.validation.constraints.Digits;


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
	@SequenceGenerator(name="product_kod_generator",schema="simbirsoft", sequenceName="product_kod_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="product_kod_generator")
	@Column(name="product_id")
	private int productId;

	@Column(name="product_name")
	private String productName;

	@Column(name="recommended_price", precision = 10, scale = 2)
	@Digits(integer = 10, fraction = 3) 
        private Float recommendedPrice;

	@OneToMany(mappedBy="product", fetch = FetchType.LAZY)
	private List<ShopProduct> shopProducts;

        @Transient
        private boolean isEditing = false;

	public boolean getIsEditing() {
		return isEditing;
	}

	public void setIsEditing(boolean isEditing) {
		this.isEditing = isEditing;
	}
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