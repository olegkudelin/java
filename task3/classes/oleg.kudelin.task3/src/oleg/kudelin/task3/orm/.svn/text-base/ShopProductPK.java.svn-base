/*
* @(#)ShopProductPK
*
* @version 1.0 24.08.2013
*
* Copyright notice
*/

package oleg.kudelin.task3.orm;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Ïåğâè÷íûé êëş÷ òàáëèöû shop_product
 * 
 */
@Embeddable
public class ShopProductPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="shop_id")
	private long shopId;

	@Column(name="product_id")
	private long productId;

	public ShopProductPK() {
	}
	public long getShopId() {
		return this.shopId;
	}
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	public long getProductId() {
		return this.productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}

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

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.shopId ^ (this.shopId >>> 32)));
		hash = hash * prime + ((int) (this.productId ^ (this.productId >>> 32)));
		
		return hash;
	}
}