/*
* @(#)Shop
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
 * Класс таблицы shop
 * 
 */
@Entity
@Table(schema="simbirsoft", name="shop")
@Cacheable(true)
public class Shop implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="shop_id")
	private int shopId;

	@Column(name="shop_name")
	private String shopName;

	@OneToMany(mappedBy="shop", fetch = FetchType.LAZY)
	private List<ShopProduct> shopProducts;

	public Shop() {
	}

	public int getShopId() {
		return this.shopId;
	}

	public void setShopId(int shopId) {
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