package liverary.view;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager {
	private Stage stage;
	private Scene scene;
	private HashMap<LayoutsEnum, URL> fxmlMap;
	private HashMap<LayoutsEnum, Parent> parentMap;
	private static StageManager instance;
	
	public StageManager() {
		fxmlMap = new HashMap<LayoutsEnum, URL>();
		parentMap = new HashMap<LayoutsEnum, Parent>();
		initFXMLs();
	}
	
	public StageManager(Stage stage) {
		this();		
		this.stage = stage;
	}
	
	public static StageManager getInstance() {
		
		if (instance == null) {
			instance = new StageManager();
		}

		return instance;
	}
	
	public static StageManager getInstance(Stage stage) {
		
		if (instance == null) {
			instance = new StageManager(stage);
		}

		return instance;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	private void putFXMLUrl(LayoutsEnum key, URL url) {
		this.fxmlMap.put(key, url);
	}
	
	private URL getFXMLUrl(LayoutsEnum key) {
		return this.fxmlMap.get(key);
	}
	
	private void initFXMLs() {
		putFXMLUrl(LayoutsEnum.LoginLayout, getClass().getResource("loginLayoutFXML.fxml"));
		putFXMLUrl(LayoutsEnum.MainLayout, getClass().getResource("mainLayoutFXML.fxml"));
		putFXMLUrl(LayoutsEnum.MainTestingLayout, getClass().getResource("mainTestingLayoutFXML.fxml"));
		System.out.println(getClass().getResource("mainTestingLayoutFXML.fxml"));
	}
	
	public void switchTo(LayoutsEnum key) throws Exception {
		Parent root = this.parentMap.get(key);
		if (root == null) {
			URL fxmlURL = getFXMLUrl(key);
			try {
				root = FXMLLoader.load(fxmlURL);
				this.parentMap.put(key, root);
			} catch (IOException e) {
				throw new IOException("지정한 FXML 파일을 찾을 수 없습니다. URL을 확인해보십시오.");
			}
		}
		
		try {			
			if (scene == null) {
				scene = new Scene(root);
				stage.setScene(scene);
			} else {
				scene.setRoot(root);
			}
		} catch (NullPointerException e) {
			throw new NullPointerException("stage가 null입니다. 아직 Stage의 객체가 주입되지 않았습니다.");
		}
	}

}
