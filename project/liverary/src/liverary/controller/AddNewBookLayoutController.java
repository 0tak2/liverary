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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import liverary.Globals;
import liverary.service.BookService;
import liverary.view.LayoutsEnum;
import liverary.view.StageManager;
import liverary.vo.BookVO;

public class AddNewBookLayoutController implements Initializable {

	@FXML private VBox rootVBox;
	
	@FXML private Label greetingLabel;
	@FXML private Label additionalInfoLabel;
	@FXML private Hyperlink logoutLink;
	
	@FXML private TextField isbnTextField;
	@FXML private TextField titleTextField;
	@FXML private TextField yearTextField;
	@FXML private TextField monthTextField;
	@FXML private TextField pageTextField;
	@FXML private TextField priceTextField;
	@FXML private TextField authorTextField;
	@FXML private TextField translatorTextField;
	@FXML private TextArea supplementTextField;
	@FXML private TextField publisherTextField;
	
	@FXML private Button addBtn;
	
	private Node menuComponent; 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
	private void handleAddBtn() {
		String isbn = isbnTextField.getText();      
		String title = titleTextField.getText();
		String year = yearTextField.getText();
		String month = monthTextField.getText();
		String page_str = pageTextField.getText();
		String price_str = priceTextField.getText();
		String author = authorTextField.getText();
		String translator = translatorTextField.getText();
		String supplement = supplementTextField.getText();
		String publisher = publisherTextField.getText();
		
		if ("".equals(isbn) || "".equals(title) || "".equals(year) ||
				 "".equals(month) || "".equals(page_str) || "".equals(price_str) ||
				 "".equals(author) || "".equals(publisher)) {
			(new Alert(
					AlertType.ERROR, "항목을 빠짐없이 입력해주세요. 역자와 딸림자료만 공란으로 남길 수 있습니다.")).showAndWait();
			return;
		}
		
		int page = Integer.parseInt(page_str);
		int price = Integer.parseInt(price_str);
			
		BookVO newBook = new BookVO(isbn, title, price, author, translator, publisher,
				(year + "년 " + month + "월"), page, supplement);
		
		BookService service = new BookService();
		boolean success = service.insertNewBook(newBook);
		
		if (success) {
			(new Alert(
					AlertType.INFORMATION, "새로운 자료 정보를 성공적으로 등록했습니다.")).showAndWait();
			
			isbnTextField.clear();
			titleTextField.clear();
			yearTextField.clear();
			monthTextField.clear();
			pageTextField.clear();
			priceTextField.clear();
			authorTextField.clear();
			translatorTextField.clear();
			supplementTextField.clear();
			publisherTextField.clear();
		} else {
			(new Alert(
					AlertType.ERROR, "자료 등록 중 문제가 발생했습니다. 오류가 지속되면 관리자에게 문의하십시오.")).showAndWait();
		}
	}
}
