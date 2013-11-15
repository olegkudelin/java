/*
 * @(#)CurrentShopList
 *
 * @version 1.0 08.09.2013
 *
 * Copyright notice
 */
package oleg.kudelin.task5.lists;

import oleg.kudelin.task5.filter.ShopNameFilter;
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

import oleg.kudelin.task5.orm.Shop;

/**
 * Обслуживает список магазинов в текущей сессии
 *
 * @author Куделин Олег
 *
 */
@Named("currentshoplist")
@SessionScoped
public class CurrentShopList extends CurrentList<Shop> implements Serializable {

    /**
     *
     */
    private static ResourceBundle res = ResourceBundle.getBundle("locale");
    private static final Logger Log = Logger.getLogger(CurrentShopList.class.getName());
    @Inject
    private ShopNameFilter shopNameFilter;

    @PostConstruct
    public void initialization() {
        filtredList = new CopyOnWriteArrayList(dataBaseObjectList.getShopList());
    }

    /**
     * Запускает фильтрацию списков по введенной маске
     *
     */
    public void filtredShop() {
        shopNameFilter.filter(filtredList, dataBaseObjectList.getShopList());
        filtred();
    }

    @Override
    public void delete(Shop shop) {
        filtredList.remove(shop);
        dataBaseObjectList.deleteShop(shop);
    }

    @Override
    public void add(Shop shop) {
        filtredList.add(filtredList.indexOf(shop), new Shop());
    }

    @Override
    public void edit(ValueChangeEvent event) {
        Collection<Shop> listShop = getOutputList();
        for (Shop shop : listShop) {
            if (shop.getIsEditing() && (event.getOldValue() == shop.getShopName())) {
                shop.setShopName(event.getNewValue().toString());
                dataBaseObjectList.saveOrUpdateShop(shop);
                return;
            }
        }
        Log.log(Level.WARNING, res.getString("shopNotFound"));
    }
}
