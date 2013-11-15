package oleg.kudelin.task4.application;
	
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


/**
 * Java FX приложение  
 * 
 * @author Куделин Олег
 *
 */
public class Main extends Application {
	
	private static ResourceBundle res = ResourceBundle.getBundle("locale");
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setResources(res);
			AnchorPane root = (AnchorPane) fxmlLoader.load(getClass().getResource("/oleg/kudelin/task4/window/MainWindow.fxml").openStream());
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("/oleg/kudelin/task4/window/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle(res.getString("APP_TITLE"));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
