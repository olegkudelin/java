/*
 * @(#)ShopNameFilter
 *
 * @version 1.0 08.09.2013
 *
 * Copyright notice
 */
package oleg.kudelin.task5.filter;

import java.io.Serializable;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import oleg.kudelin.task5.orm.Shop;

/**
 * Реализация фильтра для имен магазинов
 *
 * @author Куделин Олег
 *
 */
@Named("ShopNameFilter")
@SessionScoped
public class ShopNameFilter extends Filter<Shop> implements Serializable {

    private static ResourceBundle res = ResourceBundle.getBundle("locale");
    private static final Logger Log = Logger.getLogger(ShopNameFilter.class.getName());
    private static final long serialVersionUID = 1928095276564564552L;

    @Override
    public void filter(Collection<Shop> filtredList, Collection<Shop> sourceList) {
        if ((filtredList != null) && (sourceList != null) && (!patternString.isEmpty())) {
            filtredList.clear();
            for (Shop shop : sourceList) {
                if (shop.getShopName().contains(patternString)) {
                    filtredList.add(shop);
                }
            }
        } else {
            Log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
        }
    }
}
