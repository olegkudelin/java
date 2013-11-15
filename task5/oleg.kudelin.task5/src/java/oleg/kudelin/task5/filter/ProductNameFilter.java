/*
 * @(#)ProductNameFilter
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
import oleg.kudelin.task5.orm.Product;

/**
 * Реализация фильтра для названий товаров
 *
 * @author Куделин Олег
 *
 */
@Named("ProductNameFilter")
@SessionScoped
public class ProductNameFilter extends Filter<Product> implements Serializable {

    private static ResourceBundle res = ResourceBundle.getBundle("locale");
    private static final Logger Log = Logger.getLogger(ProductNameFilter.class.getName());
    private static final long serialVersionUID = 1928095276564564552L;

    @Override
    public void filter(Collection<Product> filtredList, Collection<Product> sourceList) {
        if ((filtredList != null) && (sourceList != null) && (!patternString.isEmpty())) {
            filtredList.clear();
            for (Product product : sourceList) {
                if (product.getProductName().contains(patternString)) {
                    filtredList.add(product);
                }
            }
        } else {
            Log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
        }
    }
}
