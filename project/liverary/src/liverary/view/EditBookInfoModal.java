package liverary.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import liverary.controller.GetABookByISBNController;
import liverary.controller.NewBookController;
import liverary.vo.BookVO;

public class EditBookInfoModal implements Initializable {
	
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
	
	@FXML private Button editBtn;
	@FXML private Button deleteBtn;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
	}
	
	public void getDataAndSetTableView(String targetISBN) {
		GetABookByISBNController controller = new GetABookByISBNController();
		BookVO book = controller.exec(targetISBN);
		
		String date = book.getBdate();
		String year = date.split("년")[0];
		String month = date.split("년")[1].split("월")[0].trim();
		
		isbnTextField.setText(book.getBisbn());
		titleTextField.setText(book.getBtitle());
		yearTextField.setText(year);
		monthTextField.setText(month);
		pageTextField.setText(String.valueOf(book.getBpage()));
		priceTextField.setText(String.valueOf(book.getBprice()));
		authorTextField.setText(book.getBauthor());
		translatorTextField.setText(book.getBtranslator());
		supplementTextField.setText(book.getBsupplement());
		publisherTextField.setText(book.getBpublisher());
	}
	
	@FXML
	private void handleEditBtn() {
		String isbn = isbnTextField.getText();      
		String title = titleTextField.getText();
		String year = yearTextField.getText();
		String month = monthTextField.getText();
		int page = Integer.parseInt(pageTextField.getText());
		int price = Integer.parseInt(priceTextField.getText());
		String author = authorTextField.getText();
		String translator = translatorTextField.getText();
		String supplement = supplementTextField.getText();
		String publisher = publisherTextField.getText();
		
		BookVO newBook = new BookVO(isbn, title, price, author, translator, publisher,
				(year + "년 " + month + "월"), page, supplement);
		
		NewBookController controller = new NewBookController();
		boolean success = controller.exec(newBook);
		
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
	
	@FXML
	private void handleDeleteBtn() {
		System.out.println("Clicked");
	}
}
