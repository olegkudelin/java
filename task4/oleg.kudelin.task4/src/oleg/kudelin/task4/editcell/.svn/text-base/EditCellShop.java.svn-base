/*
* @(#)EditCellShop
*
* @version 1.0 01.08.2013
*
* Copyright notice
*/

package oleg.kudelin.task4.editcell;

import oleg.kudelin.task4.orm.Shop;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Контроллер списка магазинов  
 * Обеспечивает корректное отображение и редактирование данных
 * 
 * @author Куделин Олег
 */

public class EditCellShop extends ListCell<Shop> {

	private TextField textField;
	
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText((String) getItem().getShopName());
        setGraphic(null);
	}

	@Override
	public void startEdit() {
		super.startEdit();
		if (textField == null) {
			createTextField();
		}
		textField.setText(getShop().getShopName());
		setText(null);
		setGraphic(textField);
		textField.selectAll();
	}

	private void createTextField() {
		textField = new TextField(getShop().getShopName());
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
		textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
				if (t.getCode() == KeyCode.ENTER) {
					getItem().setShopName(textField.getText());
					commitEdit(getItem());
				} else if (t.getCode() == KeyCode.ESCAPE) {
					cancelEdit();
				}
			}
		});
	}

	private Shop getShop() {
		if (getItem() == null) {
			setItem(new Shop());
		}
		return getItem();
	}

	@Override
	protected void updateItem(Shop t, boolean bln) {
		super.updateItem(t, bln);
		if (t != null) {
			setText(t.getShopName());
		}
	}

}
