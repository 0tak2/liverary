package liverary.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import liverary.Shared;
import liverary.controller.GetAccountByUsernameController;
import liverary.vo.AccountVO;

public class LoginLayout implements Initializable {
	
	@FXML private TextField username_textfield;
	@FXML private TextField password_textfield;
	@FXML private Button login_btn;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
	}
	
	@FXML
	private void handleLoginBtn() {
		String inputUsername = username_textfield.getText();
		String inputPassword = password_textfield.getText();
		if (inputUsername.equals("") || inputPassword.equals("")) {
			(new Alert(
					AlertType.ERROR, "로그인에 실패했습니다. 아이디와 비밀번호를 다시 한 번 확인해주십시오.")).showAndWait();
			return;
		}

		GetAccountByUsernameController controller = new GetAccountByUsernameController();
		AccountVO account = controller.exec(inputUsername);
		
		
		if (inputPassword.equals(account.getApassword())) {
			Shared.setCurrentSession(account);
			Scene scene = new Scene(Shared.getLayoutMap().get(LayoutsEnum.MainLayout));
			Shared.getStage().setScene(scene);
		} else {
			(new Alert(
					AlertType.ERROR, "로그인에 실패했습니다. 아이디와 비밀번호를 다시 한 번 확인해주십시오.")).showAndWait();
		}
	}
}
