package liverary.controller;

import java.net.URL;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import liverary.Globals;
import liverary.service.AccountService;
import liverary.util.DateHelper;
import liverary.view.LayoutsEnum;
import liverary.view.StageManager;
import liverary.vo.AccountVO;

public class EditStaffAccountModalController implements Initializable {
	
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
	
	@FXML private Button editBtn;
	@FXML private Button deleteBtn;
	
	private int ano;
	private int point;
	private String username;
	private String originalPassword;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
	}
	
	public void getDataAndSetTableView(int ano) {
		AccountService service = new AccountService();
		AccountVO account = service.selectAccountbyNO(ano);
		
		String birthYear = String.valueOf(account.getAbirth().getYear());
		String birthMonth = String.valueOf(account.getAbirth().getMonthValue());
		String birthDay = String.valueOf(account.getAbirth().getDayOfMonth());
		
		String enterYear = String.valueOf(account.getAcreatedAt().getYear());
		String enterMonth = String.valueOf(account.getAcreatedAt().getMonthValue());
		String enterDay = String.valueOf(account.getAcreatedAt().getDayOfMonth());
		
		nameTextField.setText(account.getAname());
		yearTextField.setText(birthYear);
		monthsTextField.setText(birthMonth);
		dayTextField.setText(birthDay);
		phoneTextField.setText(account.getAphone());
		emailTextField.setText(account.getAemail());
		addrTextField.setText(account.getAaddr());
		enterYearTextField.setText(enterYear);
		enterMonthsTextField.setText(enterMonth);
		enterDayTextField.setText(enterDay);
		departmentTextField.setText(account.getAdepartment());
		usernameTextField.setText(account.getAusername());
		levelTextField.setText(String.valueOf(account.getAlevel()));
		
		this.ano = ano;
		point = account.getApoint();
		username = account.getAusername();
		originalPassword = account.getApassword();
	}
	
	@FXML
	private void handleEditBtn(ActionEvent e) {
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
		String password = passwordTextField.getText();
		String passwordConfirm = passwordConfirmTextField.getText();
		String levelStr = levelTextField.getText();
		
		String birth = year + "-" + months + "-" + day;
		String enteredDate = enterYear + "-" + enterMonths + "-" + enterDay;
		int level = 1;
		try {
			level = Integer.parseInt(levelTextField.getText());			
		} catch (NumberFormatException exception) {
			(new Alert(
					AlertType.ERROR, "????????? 1 ?????? 2??? ????????? ??? ????????????.")).showAndWait();
			return;
		}
		
		if ("".equals(name) || "".equals(year) || "".equals(months) ||
				"".equals(day) || 	"".equals(phone) || "".equals(email) ||
				"".equals(addr) || "".equals(username) || 
				"".equals(enterYear) || "".equals(enterMonths) || "".equals(enterDay) ||
				"".equals(department) || "".equals(levelStr)) {
			(new Alert(
					AlertType.ERROR, "?????? ????????? ???????????? ??????????????????.")).showAndWait();
			return;
		}
		
		if (level < 1 || level > 2) {
			(new Alert(
					AlertType.ERROR, "????????? 1 ?????? 2??? ????????? ??? ????????????.")).showAndWait();
			return;
		}
		
		if (password.equals("") && password.equals("")) {
			password = originalPassword;
		} else if (!password.equals(passwordConfirm)) {
			(new Alert(
					AlertType.ERROR, "?????????????????? ???????????? ???????????? ?????? ????????? ????????????. ?????? ??? ??? ??????????????????.")).showAndWait();
			return;
		}

		AccountVO newAccount = new AccountVO(ano, name, department,
												DateHelper.ConvertStrToLocalDate(birth),
												DateHelper.ConvertStrToLocalDate(enteredDate),
												phone, email, addr, point, level, username, password);
		
		AccountService service = new AccountService();
		boolean success = service.updateAccount(newAccount);
		
		if (success) {
			(new Alert(
					AlertType.INFORMATION, "?????? ?????? ????????? ??????????????????.")).showAndWait();
			
			Node node = (Node) e.getSource();
		    Stage thisStage = (Stage) node.getScene().getWindow();
		    thisStage.close();
		    
		    if(ano == Globals.getCurrentSessionNo()) {
				(new Alert(
						AlertType.INFORMATION, "?????? ???????????? ????????? ????????? ???????????? ?????????????????????.")).showAndWait();
		    	Globals.setCurrentSession(null);
		    	StageManager manager = StageManager.getInstance();
		    	try {
					manager.switchToWithHide(LayoutsEnum.LoginLayout);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		    }
		} else {
			(new Alert(
					AlertType.ERROR, "?????? ?????? ?????? ??? ????????? ??????????????????. ????????? ???????????? ??????????????? ??????????????????.")).showAndWait();
		}
	}
	
	@FXML
	private void handleDeleteBtn(ActionEvent e) {
		Alert alert = 
		        new Alert(AlertType.WARNING, 
		            "????????? ??? ????????? ????????????????????????? ??? ????????? ????????? ??? ????????????.",
		             ButtonType.OK, 
		             ButtonType.CANCEL);
		alert.setTitle("?????? ?????? ??????");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.CANCEL) {
		    return;
		}
		
		AccountService service = new AccountService();
		int success = service.updateAccountToEmptyRow(ano, username);
		
		if (success == 1) {
			(new Alert(
					AlertType.INFORMATION, "??????????????? ????????? ??????????????????.")).showAndWait();
			
			Node node = (Node) e.getSource();
		    Stage thisStage = (Stage) node.getScene().getWindow();
		    thisStage.close();
		    
		    if(ano == Globals.getCurrentSessionNo()) {
				(new Alert(
						AlertType.INFORMATION, "?????? ???????????? ????????? ????????? ???????????? ?????????????????????.")).showAndWait();
		    	Globals.setCurrentSession(null);
		    	StageManager manager = StageManager.getInstance();
		    	try {
					manager.switchToWithHide(LayoutsEnum.LoginLayout);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		    }
		} else if (success == 11) {
			(new Alert(
					AlertType.ERROR, "??????????????? ?????? ????????? ??? ????????????.")).showAndWait();
		} else {
			(new Alert(
					AlertType.ERROR, "?????? ?????? ??? ????????? ??????????????????. ????????? ???????????? ??????????????? ??????????????????.")).showAndWait();
		}
	}
}
