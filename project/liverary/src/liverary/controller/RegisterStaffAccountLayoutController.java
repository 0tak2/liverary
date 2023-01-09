package liverary.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import liverary.Globals;
import liverary.service.AccountService;
import liverary.util.DateHelper;
import liverary.view.LayoutsEnum;
import liverary.view.StageManager;
import liverary.vo.AccountVO;

public class RegisterStaffAccountLayoutController implements Initializable {

	@FXML private VBox rootVBox;
	
	@FXML private Label greetingLabel;
	@FXML private Label additionalInfoLabel;
	@FXML private Hyperlink logoutLink;
	
	@FXML private TextField nameTextField;
	@FXML private TextField yearTextField;
	@FXML private TextField monthsTextField;
	@FXML private TextField dayTextField;
	@FXML private TextField phoneTextField;
	@FXML private TextField emailTextField;
	@FXML private TextField addrTextField;
	@FXML private TextField enterYearTextField;
	@FXML private TextField enterMonthsTextField;
	@FXML private TextField enterDayTextField;
	@FXML private TextField departmentTextField;
	@FXML private TextField usernameTextField;
	@FXML private TextField levelTextField;
	@FXML private PasswordField passwordTextField;
	@FXML private PasswordField passwordConfirmTextField;
	
	@FXML private Button addBtn;
	
	private Node menuComponent; 
	
	private boolean verified;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		// 상단 메뉴 추가
		try {
			menuComponent = FXMLLoader.load(getClass().getResource("../view/menuComponentFXML.fxml"));
			rootVBox.getChildren().add(0, menuComponent);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// 상단 위젯 초기화
		greetingLabel.setText(Globals.getCurrentSessionName() + "(" + Globals.getCurrentSessionUsername() + ")님 반갑습니다.");
		additionalInfoLabel.setText(Globals.getCurrentSessionDepartment() + " | 권한" + Globals.getCurrentSessionLevel());
		
		levelTextField.setText("1");
		String[] todayStr = DateHelper.todayDateStr().split("-");
		enterYearTextField.setText(todayStr[0]);
		enterMonthsTextField.setText(todayStr[1]);
		enterDayTextField.setText(todayStr[2]);
	}
	
	@FXML
	private void handleLogout() {
		Globals.setCurrentSession(null);
		StageManager manager = StageManager.getInstance();
		try {
			manager.switchTo(LayoutsEnum.LoginLayout);
			manager.freeAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleVerifyBtn() {
		String username = usernameTextField.getText();
		
		AccountService service = new AccountService();
		AccountVO account = service.selectAccountbyUsername(username, true, false);
		
		boolean available = false;
		if (account == null) {
			available = true;
		}
		
		if (available) {
			(new Alert(
					AlertType.INFORMATION, username + "은(는) 아이디로 사용 가능합니다.")).showAndWait();
			verified = true;
		} else {
			(new Alert(
					AlertType.ERROR, username + "은(는) 아이디로 사용할 수 없습니다. 다른 아이디를 입력해주세요.")).showAndWait();
			verified = false;
		}
	}
	
	@FXML
	private void handleAddBtn() {
		if (!verified) {
			(new Alert(
					AlertType.INFORMATION, "먼저 아이디 중복검사를 진행해주십시오.")).showAndWait();
			return;
		}
		
		String name = nameTextField.getText();
		String year = yearTextField.getText();
		String months = monthsTextField.getText();
		String day = dayTextField.getText();
		String enterYear = yearTextField.getText();
		String enterMonths = monthsTextField.getText();
		String enterDay = dayTextField.getText();
		String department = departmentTextField.getText();
		String phone = phoneTextField.getText();
		String email = emailTextField.getText();
		String addr = addrTextField.getText();
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		String passwordConfirm = passwordConfirmTextField.getText();
		String levelStr = levelTextField.getText();
		
		String birth = year + "-" + months + "-" + day;
		String enteredDate = enterYear + "-" + enterMonths + "-" + enterDay;
		int level = 1;
		try {
			level = Integer.parseInt(levelTextField.getText());			
		} catch (NumberFormatException e) {
			(new Alert(
					AlertType.ERROR, "권한은 1 또는 2만 지정할 수 있습니다.")).showAndWait();
			return;
		}
		
		if ("".equals(name) || "".equals(year) || "".equals(months) ||
				"".equals(day) || 	"".equals(phone) || "".equals(email) ||
				"".equals(addr) || "".equals(username) || "".equals(password) || "".equals(passwordConfirm) ||
				"".equals(enterYear) || "".equals(enterMonths) || "".equals(enterDay) ||
				"".equals(department) || "".equals(levelStr)) {
			(new Alert(
					AlertType.ERROR, "모든 내용을 빠짐없이 입력해주세요.")).showAndWait();
			return;
		}
		
		if (level < 1 || level > 2) {
			(new Alert(
					AlertType.ERROR, "권한은 1 또는 2만 지정할 수 있습니다.")).showAndWait();
			return;
		}
		
		if (!password.equals(passwordConfirm)) {
			(new Alert(
					AlertType.ERROR, "비밀번호란과 비밀번호 확인란의 입력 내용이 다릅니다. 다시 한 번 확인해주세요.")).showAndWait();
			return;
		}
		
		AccountVO newAccount = new AccountVO(name, birth, enteredDate, phone,
												email, addr, department, 0, level, username, password);
		
		AccountService service = new AccountService();
		boolean success = service.insertNewAccount(newAccount);
		
		if (success) {
			(new Alert(
					AlertType.INFORMATION, "계정 등록에 성공했습니다.")).showAndWait();
			nameTextField.clear();
			yearTextField.clear();
			monthsTextField.clear();
			dayTextField.clear();
			phoneTextField.clear();
			emailTextField.clear();
			addrTextField.clear();
			enterYearTextField.clear();
			enterMonthsTextField.clear();
			enterDayTextField.clear();
			departmentTextField.clear();
			usernameTextField.clear();
			levelTextField.clear();
			passwordTextField.clear();
			passwordConfirmTextField.clear();
		} else {
			(new Alert(
					AlertType.ERROR, "계정 등록 중 문제가 발생했습니다. 오류가 지속되면 관리자에게 문의하십시오.")).showAndWait();
		}
	}
}
