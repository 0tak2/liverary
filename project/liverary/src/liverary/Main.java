package liverary;

import javafx.application.Application;
import javafx.stage.Stage;
import liverary.view.LayoutsEnum;
import liverary.view.StageManager;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		StageManager manager = StageManager.getInstance(primaryStage);
		
		manager.switchTo(LayoutsEnum.LoginLayout);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}
}
