/*
* @(#)EditCellProductPrice
*
* @version 1.0 01.08.2013
*
* Copyright notice
*/

package oleg.kudelin.task4.editcell;

import oleg.kudelin.task4.orm.Product;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

/**
 * Контроллер рекомендованной цены  
 * Обеспечивает корректное отображение и редактирование данных
 * 
 * @author Куделин Олег
 */
public class EditCellProductPrice extends TableCell<Product, Float> {

	private TextField textField;

    @Override public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }
        textField.setText(getString());
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }
    
    @Override public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().toString());
        setGraphic(null);
    }
    
    @Override public void updateItem(Float item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {

    	textField = new TextField(getString()) {
    		//Фильтр на вводимые сммволы, только цифры и точка
        	@Override public void replaceText(int start, int end, String text) {
                if (text.matches("[\\d.]")) {
                    super.replaceText(start, end, text);
                }
            }
         
            @Override public void replaceSelection(String text) {
                if (text.matches("[\\d.]")) {
                    super.replaceSelection(text);
                }
            }
        };
        
        
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {                
            @Override public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(Float.parseFloat(textField.getText()));
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

}
