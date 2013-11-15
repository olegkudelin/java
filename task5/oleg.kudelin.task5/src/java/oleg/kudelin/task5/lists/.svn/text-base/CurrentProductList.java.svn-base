/*
 * @(#)CurrentProductList
 *
 * @version 1.0 08.09.2013
 *
 * Copyright notice
 */
package oleg.kudelin.task5.lists;

import oleg.kudelin.task5.filter.ProductNameFilter;
import oleg.kudelin.task5.filter.RecommendedPriceFilter;
import java.io.Serializable;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import oleg.kudelin.task5.orm.Product;

/**
 * Обслуживает список товаров в текущей сессии
 *
 * @author Куделин Олег
 *
 */
@Named("CurrentProductList")
@SessionScoped
public class CurrentProductList extends CurrentList<Product> implements Serializable {

    /**
     *
     */
    private static ResourceBundle res = ResourceBundle.getBundle("locale");
    private static final Logger Log = Logger.getLogger(CurrentProductList.class.getName());
    @Inject
    private ProductNameFilter productNameFilter;
    @Inject
    private RecommendedPriceFilter recommendedPriceFilter;
    private static final long serialVersionUID = -5068402090424426269L;

    @PostConstruct
    public void initialization() {
        filtredList = new CopyOnWriteArrayList(dataBaseObjectList.getProductList());
    }

    /**
     * Запускает фильтрацию списков по введенной маске
     *
     */
    public void filtered() {
        productNameFilter.filter(filtredList, dataBaseObjectList.getProductList());
        if (!recommendedPriceFilter.getPatternString().isEmpty()) {
            recommendedPriceFilter.filter(filtredList, new CopyOnWriteArrayList(filtredList));
        }
        filtred();
    }

    @Override
    public void delete(Product product) {
        filtredList.remove(product);
        dataBaseObjectList.deleteProduct(product);
    }

    @Override
    public void add(Product product) {
        Product newProduct = new Product();
        newProduct.setRecommendedPrice(new Float(0));
        filtredList.add(filtredList.indexOf(product), newProduct);
    }

    @Override
    public void edit(ValueChangeEvent event) {
        if (res.getString("nameProductInputTextID").compareTo(event.getComponent().getId()) == 0) {
            editProductName(event);
        } else if (res.getString("recommendedPriceProductInputTextID").compareTo(event.getComponent().getId()) == 0) {
            editPrice(event);
        } else {
            Log.log(Level.WARNING, res.getString("unknownSource"));
        }
    }

    /**
     * Изменяет цену товара у элемента, который редактируется в текущий момент
     *
     * @param event Событие завершения редактирования
     */
    public void editPrice(ValueChangeEvent event) {
        Collection<Product> listProduct = getOutputList();
        for (Product product : listProduct) {
            if (product.getIsEditing() && (event.getOldValue() == product.getRecommendedPrice().toString())) {
                product.setRecommendedPrice(Float.valueOf(event.getNewValue().toString()));
                dataBaseObjectList.saveOrUpdateProduct(product);
                return;
            }
        }
        Log.log(Level.WARNING, res.getString("productByPriceNotFound"));
    }

    /**
     * Изменяет название товара у элемента, который редактируется в текущий
     * момент
     *
     * @param event Событие завершения редактирования
     */
    public void editProductName(ValueChangeEvent event) {
        Collection<Product> listProduct = getOutputList();
        for (Product product : listProduct) {
            if (product.getIsEditing() && (event.getOldValue() == product.getProductName())) {
                product.setProductName(event.getNewValue().toString());
                dataBaseObjectList.saveOrUpdateProduct(product);
                return;
            }
        }
        Log.log(Level.WARNING, res.getString("productByNameNotFound"));
    }
}
