package liverary;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

import javafx.application.Application;
import javafx.stage.Stage;
import liverary.dao.DBCPConnectionPool;
import liverary.view.LayoutsEnum;
import liverary.view.StageManager;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setOnCloseRequest(e -> {
			try {
				((BasicDataSource)(DBCPConnectionPool.getDataSource())).close();
				System.out.println("Connection pool closed");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		StageManager manager = StageManager.getInstance(primaryStage);
		
		manager.switchTo(LayoutsEnum.LoginLayout);
		primaryStage.show();
	}
	
	public static void initProperties(String location) {
		Properties p = new Properties();
		try {
			p.load(new FileReader(location));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String pointPlusAmount_str = p.getProperty("point.plusAmount", "100");
		String pointMinusAmount_str = p.getProperty("point.minusAmount", "100");
		String loanDays_str = p.getProperty("loanService.loanDays", "7");
		String maxLoanBooksAmount_str = p.getProperty("loanService.maxBookAmount", "5");
		String nlApiSecret = p.getProperty("nlApi.secret", "");
		String nlApiMaxItems_str = p.getProperty("nlApi.maxItems", "50");
		
		Globals.setPointPlusAmount(Integer.parseInt(pointPlusAmount_str));
		Globals.setPointMinusAmount(Integer.parseInt(pointMinusAmount_str));
		Globals.setLoanDays(Integer.parseInt(loanDays_str));
		Globals.setMaxLoanBooksAmount(Integer.parseInt(maxLoanBooksAmount_str));
		Globals.setNlApiSecret(nlApiSecret);
		Globals.setNlApiMaxItems(Integer.parseInt(nlApiMaxItems_str));
	}
	
	public static void main(String[] args) {
		initProperties("src/liverary/preferences.properties");
		launch();
	}
}
