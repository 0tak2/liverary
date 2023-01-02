package liverary.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import liverary.Globals;

public class MainComponent implements Initializable {

	@FXML private Label todayLabel;
	@FXML private Label dueDateLabel;
	@FXML private Label greetingLabel;
	@FXML private Label additionalInfoLabel;
	@FXML private Hyperlink logoutLink;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		LocalDate date = LocalDate.now();
		LocalDate dueDate = date.plusDays(7);
		todayLabel.setText(date.toString());
		dueDateLabel.setText("반납예정일: " + dueDate.toString());
	}
	
	@FXML
	private void handleLogout() {
		Globals.setCurrentSession(null);
		StageManager manager = StageManager.getInstance();
		try {
			manager.switchTo(LayoutsEnum.LoginLayout);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
