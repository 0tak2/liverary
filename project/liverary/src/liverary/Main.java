package liverary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import liverary.view.LayoutsEnum;

public class Main extends Application {
	
	@Override
	public void init() throws Exception {
		super.init();
		
		Parent parent = FXMLLoader.load(getClass().getResource("view/loginLayoutFXML.fxml"));
		Shared.getLayoutMap().put(LayoutsEnum.LoginLayout, parent);
		
		parent = FXMLLoader.load(getClass().getResource("view/mainLayoutFXML.fxml"));
		Shared.getLayoutMap().put(LayoutsEnum.MainLayout, parent);
		
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Shared.setStage(primaryStage);
		
		Scene loginScene = new Scene(Shared.getLayoutMap().get(LayoutsEnum.LoginLayout));
		primaryStage.setScene(loginScene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
}
