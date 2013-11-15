/*
* @(#)Queries
*
* @version 1.0 31.08.2013
*
* Copyright notice
*/

package oleg.kudelin.task4.window;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import oleg.kudelin.task4.concurrency.GetProductListTask;
import oleg.kudelin.task4.concurrency.GetShopListTask;
import oleg.kudelin.task4.editcell.EditCellProductName;
import oleg.kudelin.task4.editcell.EditCellProductPrice;
import oleg.kudelin.task4.editcell.EditCellShop;
import oleg.kudelin.task4.orm.Product;
import oleg.kudelin.task4.orm.Shop;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.scene.layout.Region;
import oleg.kudelin.task4.queries.Queries;

/**
 * Контроллер основного окна 
 * Подключается к базе и загружает данные посредством потоков
 * 
 * @author Куделин Олег
 *
 */

public class MainWindowController implements Initializable {

	@FXML
	private ListView<Shop> shopListView;
	
	@FXML
	private TableView<Product> productTableView;
	
	@FXML
	private TableColumn<Product, String> nameProductColumn;
	
	@FXML
	private TableColumn<Product, Float> priceProductColumn;

	@FXML
	private ProgressIndicator progressShopIndicator;
	
	@FXML
	private Region veilShopRegion;
	
	@FXML
	private TextField findShopTextField;

	@FXML
	private ProgressIndicator progressProductIndicator;
	
	@FXML 
	private Region veilProductRegion;
	
	@FXML 
	private TextField findProductTextField;
	
	private Queries query;
	private ResourceBundle res;
	private static Logger Log = Logger.getLogger(Queries.class.getName());	
	
	/**
	 * Инициализация окна
	 * 
	 */	
	
	@Override
	public void initialize(URL arg0, ResourceBundle bundle) {
		query = new  Queries();
		pripearShopTab();
		pripearProductTab();
		shopFilterStart(null);
		productFilterStart(null);	
		res = bundle;
	}

	private void pripearShopTab() {
		Callback<ListView<Shop>, ListCell<Shop>> cellFactory = new Callback<ListView<Shop>, ListCell<Shop>>() {
			@Override
			public ListCell<Shop> call(ListView<Shop> param) {
				return new EditCellShop();
			}
		};
		shopListView.setCellFactory(cellFactory);
		shopListView.setEditable(true);
		shopListView.setOnEditCommit(new EventHandler<ListView.EditEvent<Shop>>() {
			
			@Override
			public void handle(EditEvent<Shop> arg0) {
				query.saveShop(arg0.getNewValue());
			}
		});
	}
	
	private void pripearProductTab() {
		nameProductColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
		priceProductColumn.setCellValueFactory(new PropertyValueFactory<Product, Float>("recommendedPrice"));
		
		Callback<TableColumn<Product, String>, TableCell<Product, String>> cellProductNameFactory = new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
			public TableCell<Product, String> call(TableColumn<Product, String> p) {
				return new EditCellProductName();
			}
		};
		Callback<TableColumn<Product, Float>, TableCell<Product, Float>> cellProductPriceFactory = new Callback<TableColumn<Product, Float>, TableCell<Product, Float>>() {
			public TableCell<Product, Float> call(TableColumn<Product, Float> p) {
				return new EditCellProductPrice();
			}
		};	
		nameProductColumn.setCellFactory(cellProductNameFactory);
		priceProductColumn.setCellFactory(cellProductPriceFactory);
		nameProductColumn.setSortable(false);
		priceProductColumn.setSortable(false);
		productTableView.setEditable(true);
		
		nameProductColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Product,String>>() {
			@Override
			public void handle(CellEditEvent<Product,String> cellEditEvent) {
				cellEditEvent.getRowValue().setProductName(cellEditEvent.getNewValue());
				query.saveProduct(cellEditEvent.getRowValue());
			}			
		});
		
		priceProductColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Product,Float>>() {
			@Override
			public void handle(CellEditEvent<Product,Float> cellEditEvent) {
				cellEditEvent.getRowValue().setRecommendedPrice(cellEditEvent.getNewValue());
				query.saveProduct(cellEditEvent.getRowValue());
			}
		});
	}
	
	@FXML
	protected void shopFilterButtonClick() {
		shopFilterStart(findShopTextField.getText());
	}

	@FXML
	protected void deleteShop() {
		if (shopListView.getSelectionModel().getSelectedItem() != null) {
			query.deleteShop(new ArrayList<Shop>(Arrays.asList(shopListView.getSelectionModel().getSelectedItem())));
			shopListView.getItems().remove(shopListView.getSelectionModel().getSelectedItem());
		} else {
			Log.log(Level.WARNING, res.getString("itemNotIsSelected"));
		}
	}

	@FXML
	protected void addShop() {
		int i = shopListView.getSelectionModel().getSelectedIndex() + 1; // под выделенной или в начало списка, если ничего не выделено
		Shop shop = new Shop();
		shopListView.getItems().add(i, shop);
		shopListView.scrollTo(i);
		shopListView.edit(i);
	}
	
	@FXML
	protected void productFilterButtonClick() {
		productFilterStart(findProductTextField.getText());	
	}

	@FXML
	protected void deleteProduct() {
		if (productTableView.getSelectionModel().getSelectedItem() != null) {
			query.deleteProduct(new ArrayList<Product>(Arrays.asList(productTableView.getSelectionModel().getSelectedItem())));
			productTableView.getItems().remove(productTableView.getSelectionModel().getSelectedItem());
		} else {
			Log.log(Level.WARNING, res.getString("itemNotIsSelected"));
		}
	}

	@FXML
	protected void addProduct() {
		int i = productTableView.getSelectionModel().getSelectedIndex() + 1; // под выделенной или в начало списка, если ничего не выделено
		Product product = new Product();
		product.setProductName("");
		product.setRecommendedPrice(new Float(0));
		productTableView.getItems().add(i, product);
		productTableView.scrollTo(i);
	}
	
	private void shopFilterStart(String shopFilterString) {
        Task<ObservableList<Shop>> task = new GetShopListTask(query, shopFilterString);
        veilShopRegion.visibleProperty().bind(task.runningProperty());
        progressShopIndicator.visibleProperty().bind(task.runningProperty());
        shopListView.itemsProperty().bind(task.valueProperty());
        Thread thread = new Thread(task);
        thread.start();	
	}

	private void productFilterStart(String productFilterString) {
        Task<ObservableList<Product>> task = new GetProductListTask(query, productFilterString);
        veilProductRegion.visibleProperty().bind(task.runningProperty());
        progressProductIndicator.visibleProperty().bind(task.runningProperty());
        productTableView.itemsProperty().bind(task.valueProperty());
        Thread thread = new Thread(task);
        thread.start();			
	}
	
}
