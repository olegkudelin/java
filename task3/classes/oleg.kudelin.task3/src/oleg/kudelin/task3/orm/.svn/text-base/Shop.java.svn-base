/*
* @(#)Shop
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
 * Класс таблицы shop
 * 
 */
@Entity
@Table(schema="simbirsoft", name="shop")
public class Shop implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="shop_id")
	private long shopId;

	@Column(name="shop_name")
	private String shopName;

	//bi-directional many-to-one association to ShopProduct
	@OneToMany(mappedBy="shop")
	private List<ShopProduct> shopProducts;

	public Shop() {
	}

	public long getShopId() {
		return this.shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public List<ShopProduct> getShopProducts() {
		return this.shopProducts;
	}

	public void setShopProducts(List<ShopProduct> shopProducts) {
		this.shopProducts = shopProducts;
	}

}