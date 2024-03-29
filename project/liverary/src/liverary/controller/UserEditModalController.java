package liverary.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import liverary.Globals;
import liverary.service.AccountService;
import liverary.view.LayoutsEnum;
import liverary.view.StageManager;
import liverary.vo.AccountVO;

public class UserEditModalController implements Initializable {

	@FXML private TextField nameTextField;
	@FXML private TextField yearTextField;
	@FXML private TextField monthsTextField;
	@FXML private TextField dayTextField;
	@FXML private TextField phoneTextField;
	@FXML private TextField emailTextField;
	@FXML private TextField addrTextField;
	@FXML private TextField usernameTextField;
	@FXML private PasswordField passwordTextField;
	@FXML private PasswordField passwordConfirmTextField;
	
	@FXML private Button editBtn;
	@FXML private Button withdrawBtn;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		LocalDate birth = Globals.getCurrentSessionBirth();
		
		nameTextField.setText(Globals.getCurrentSessionName());
		yearTextField.setText(String.valueOf(birth.getYear()));
		monthsTextField.setText(String.valueOf(birth.getMonthValue()));
		dayTextField.setText(String.valueOf(birth.getDayOfMonth()));
		phoneTextField.setText(Globals.getCurrentSessionPhone());
		emailTextField.setText(Globals.getCurrentSessionEmail());
		addrTextField.setText(Globals.getCurrentSessionAddr());
		usernameTextField.setText(Globals.getCurrentSessionUsername());
	}
	
	@FXML
	private void handleEditBtn() {
		int ano = Globals.getCurrentSessionNo();
		String name = nameTextField.getText();
		String phone = phoneTextField.getText();
		String email = emailTextField.getText();
		String addr = addrTextField.getText();
		String password = passwordTextField.getText();
		String passwordConfirm = passwordConfirmTextField.getText();
		
		if ("".equals(name) || "".equals(phone) || "".equals(email) || "".equals(addr)) {
			(new Alert(
					AlertType.ERROR, "모든 내용을 빠짐없이 입력해주세요.")).showAndWait();
			return;
		}
		
		if (password.equals("") && password.equals("")) {
			password = Globals.getCurrentSessionPassword();
		} else if (!password.equals(passwordConfirm)) {
			(new Alert(
					AlertType.ERROR, "비밀번호란과 비밀번호 확인란의 입력 내용이 다릅니다. 다시 한 번 확인해주세요.")).showAndWait();
			return;
		}

		AccountVO newAccount = new AccountVO(ano, name, null, Globals.getCurrentSessionBirth(),
				Globals.getCurrentSessionCreatedAt(), phone, email, addr,
				Globals.getCurrentSessionPoint(), Globals.getCurrentSessionLevel(),
				Globals.getCurrentSessionUsername(), password);
		
		AccountService service = new AccountService();
		boolean success = service.updateAccount(newAccount);
		
		if (success) {
			(new Alert(
					AlertType.INFORMATION, "회원정보 수정에 성공했습니다. 다시 로그인해주세요.")).showAndWait();
		    Stage stage = (Stage)editBtn.getScene().getWindow();
		    stage.close();
		    StageManager manager = StageManager.getInstance();
		    try {
				manager.switchTo(LayoutsEnum.LoginLayout);
				manager.freeParent(LayoutsEnum.UserMainLayout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			(new Alert(
					AlertType.ERROR, "회원가입 중 문제가 발생했습니다. 오류가 지속되면 관리자에게 문의하십시오.")).showAndWait();
		}
	}
	
	@FXML
	private void handleWithdrawBtn(ActionEvent e) {
		Alert alert = 
		        new Alert(AlertType.WARNING, 
		            "정말로 탈퇴하시겠습니까? 이 작업은 돌이킬 수 없습니다.",
		             ButtonType.OK, 
		             ButtonType.CANCEL);
		alert.setTitle("회원 탈퇴 경고");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.CANCEL) {
		    return;
		}
		
		AccountService service = new AccountService();
		int success = service.updateAccountToEmptyRow(Globals.getCurrentSessionNo(),  Globals.getCurrentSessionUsername());
		
		if (success == 1) {
			(new Alert(
					AlertType.INFORMATION, "성공적으로 항목을 삭제했습니다.")).showAndWait();
			
			Node node = (Node) e.getSource();
		    Stage thisStage = (Stage) node.getScene().getWindow();
		    thisStage.close();

	    	Globals.setCurrentSession(null);
	    	StageManager manager = StageManager.getInstance();
	    	try {
				manager.switchToWithHide(LayoutsEnum.LoginLayout);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (success == 11) {
			(new Alert(
					AlertType.ERROR, "미납도서가 있어 삭제할 수 없습니다.")).showAndWait();
		} else {
			(new Alert(
					AlertType.ERROR, "삭제 작업 중 문제가 발생했습니다. 오류가 지속되면 관리자에게 문의하십시오.")).showAndWait();
		}
	}

}
