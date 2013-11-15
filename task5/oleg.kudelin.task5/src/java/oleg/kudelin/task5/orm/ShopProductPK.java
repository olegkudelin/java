/*
* @(#)ShopProductPK
*
* @version 1.0 24.08.2013
*
* Copyright notice
*/

package oleg.kudelin.task5.orm;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Первичный ключ таблицы shop_product
 * 
 */
@Embeddable
public class ShopProductPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="shop_id")
	private int shopId;

	@Column(name="product_id")
	private int productId;

	public ShopProductPK() {
	}
	public int getShopId() {
		return this.shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public int getProductId() {
		return this.productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ShopProductPK)) {
			return false;
		}
		ShopProductPK castOther = (ShopProductPK)other;
		return 
			(this.shopId == castOther.shopId)
			&& (this.productId == castOther.productId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.shopId ^ (this.shopId >>> 32)));
		hash = hash * prime + ((int) (this.productId ^ (this.productId >>> 32)));
		
		return hash;
	}
}