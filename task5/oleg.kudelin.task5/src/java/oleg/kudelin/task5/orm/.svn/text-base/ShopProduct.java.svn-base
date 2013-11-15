/*
* @(#)ShopProduct
*
* @version 1.0 24.08.2013
*
* Copyright notice
*/

package oleg.kudelin.task5.orm;

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
	private int productCount;

	@Column(name="product_price", precision = 10, scale = 2)
	private float productPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product_id", insertable=false, updatable=false)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="shop_id", insertable=false, updatable=false)
	private Shop shop;

	public ShopProduct() {
		this.id = new ShopProductPK();
	}

	public ShopProductPK getId() {
		return this.id;
	}

	public void setId(ShopProductPK id) {
		this.id = id;
	}

	public int getProductCount() {
		return this.productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public float getProductPrice() {
		return this.productPrice;
	}

	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
		this.id.setProductId(product.getProductId());
	}

	public Shop getShop() {
		return this.shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
		this.id.setShopId(shop.getShopId());
	}

}