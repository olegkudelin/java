/*
 * @(#)DataBaseObjectList
 *
 * @version 1.0 08.09.2013
 *
 * Copyright notice
 */
package oleg.kudelin.task5.tab;

import java.io.Serializable;

import javax.inject.Named; 
import javax.enterprise.context.SessionScoped; 
import javax.faces.event.ActionEvent;

@Named("tp")
@SessionScoped
public class TabbedPane implements Serializable {
   private int index;
   private static final int SHOP_INDEX = 0;
   private static final int PRODUCT_INDEX = 1; 


   public TabbedPane() {
      index = SHOP_INDEX;
   }

   public void shopAction(ActionEvent e) { index = SHOP_INDEX;  }
   public void productAction(ActionEvent e) { index = PRODUCT_INDEX;  }

   // CSS styles

   public String getShopStyle() { return getCSS(SHOP_INDEX);  }
   public String getProductStyle() { return getCSS(PRODUCT_INDEX);  }

   private String getCSS(int forIndex) {
      return forIndex == index ? "tabbedPaneTextSelected" : "tabbedPaneText"; 
   }

   public boolean isShopCurrent() { return index == SHOP_INDEX;  }
   public boolean isProductCurrent() { return index == PRODUCT_INDEX;  }
}