/*
* @(#)ShopProduct
*
* @version 1.0 24.08.2013
*
* Copyright notice
*/

package oleg.kudelin.task3.orm;

import java.io.Serializable;
import javax.persistence.*;


/**
 * Класс таблицы shop_product
 * 
 */
@Entity
@Table(schema="simbirsoft", name="shop_product")
public class ShopProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ShopProductPK id;

	@Column(name="product_count")
	private long productCount;

	@Column(name="product_price")
	private long productPrice;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_id", insertable=false, updatable=false)
	private Product product;

	//bi-directional many-to-one association to Shop
	@ManyToOne
	@JoinColumn(name="shop_id", insertable=false, updatable=false)
	private Shop shop;

	public ShopProduct() {
	}

	public ShopProductPK getId() {
		return this.id;
	}

	public void setId(ShopProductPK id) {
		this.id = id;
	}

	public long getProductCount() {
		return this.productCount;
	}

	public void setProductCount(long productCount) {
		this.productCount = productCount;
	}

	public long getProductPrice() {
		return this.productPrice;
	}

	public void setProductPrice(long productPrice) {
		this.productPrice = productPrice;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Shop getShop() {
		return this.shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

}