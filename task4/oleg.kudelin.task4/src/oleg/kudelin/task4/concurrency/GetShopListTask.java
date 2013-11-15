/*
* @(#)GetShopListTask
*
* @version 1.0 01.09.2013
*
* Copyright notice
*/

package oleg.kudelin.task4.concurrency;

import java.util.List;

import oleg.kudelin.task4.orm.Shop;
import oleg.kudelin.task4.queries.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;


/**
 * потоковый загрузчик магазинов 
 * 
 * @author Куделин Олег
 *
 */
public class GetShopListTask extends Task<ObservableList<Shop>> {
	
	private Queries query;
	private String filterString;
	
	public GetShopListTask(Queries query, String filterString) {
		this.query = query;
		this.filterString = filterString;
	}

	/**
	 * Загружает и возвращает список магазинов
	 * 
	 */		
    @Override protected ObservableList<Shop> call() throws Exception {
    	List<Shop> productObList = query.getFiltredShopListByStringMask(filterString);
        return FXCollections.observableList(productObList);
    }
}