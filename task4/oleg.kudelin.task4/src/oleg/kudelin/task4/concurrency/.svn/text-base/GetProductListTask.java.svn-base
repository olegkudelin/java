/*
* @(#)GetProductListTask
*
* @version 1.0 01.09.2013
*
* Copyright notice
*/

package oleg.kudelin.task4.concurrency;

import java.util.List;

import oleg.kudelin.task4.orm.Product;
import oleg.kudelin.task4.queries.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * потоковый загрузчик продуктов 
 * 
 * @author Куделин Олег
 *
 */
public class GetProductListTask extends Task<ObservableList<Product>> {
	private Queries query;
	private String filterString;
	
	public GetProductListTask(Queries query, String filterString) {
		this.query = query;
		this.filterString = filterString;
	}
	
	/**
	 * Загружает и возвращает список продуктов
	 * 
	 */			
    @Override protected ObservableList<Product> call() throws Exception {
    	List<Product> productObList = query.getFiltredProductListByStringMask(filterString);
        return FXCollections.observableList(productObList);
    }
}
