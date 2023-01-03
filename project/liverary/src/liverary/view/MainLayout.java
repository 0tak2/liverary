package liverary.view;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import liverary.Globals;
import liverary.controller.GetBooksByKeywordController;
import liverary.controller.GetLoanRecordsByISBNController;
import liverary.controller.GetLoanRecordsByKeywordController;
import liverary.vo.AccountVO;
import liverary.vo.BookVO;
import liverary.vo.LoanVO;

public class MainLayout implements Initializable {

	@FXML private VBox rootVBox;
	
	@FXML private Label todayLabel;
	@FXML private Label dueDateLabel;
	@FXML private Label greetingLabel;
	@FXML private Label additionalInfoLabel;
	@FXML private Hyperlink logoutLink;
	
	@FXML private ComboBox<String> bookSerachByCombobox;
	@FXML private TextField bookSearchKeywordTextField;
	@FXML private Button bookSearchBtn;
	
	@FXML private Label currentUserLabel;
	@FXML private Button showSearchAccoutModalBtn;
	
	@FXML private TableView<LoanVO> bookSearchTableView;
	
	@FXML private Button lendBtn;
	@FXML private Button returnBtn;
	
	private Node menuComponent; 
	
	private AccountVO currentSession;
	
	private String bookSearchByType;
	
	private SearchAccountsModal controller;
	private AccountVO selectedAccount;
	private BookVO selectedBook;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// 상단 메뉴 추가
		try {
			menuComponent = FXMLLoader.load(getClass().getResource("menuComponentFXML.fxml"));
			rootVBox.getChildren().add(0, menuComponent);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// 상단 위젯 초기화
		// 왼쪽
		LocalDate date = LocalDate.now();
		LocalDate dueDate = date.plusDays(7);
		todayLabel.setText(date.toString());
		dueDateLabel.setText("반납예정일: " + dueDate.toString());
		// 오른쪽
		currentSession = Globals.getCurrentSession();
		greetingLabel.setText(currentSession.getAname() + "(" + currentSession.getAusername() + ")님 반갑습니다.");
		additionalInfoLabel.setText(currentSession.getAdepartment() + " | 권한" + currentSession.getAlevel());
		
		// 콤보박스 초기화
		bookSerachByCombobox.getItems().removeAll(bookSerachByCombobox.getItems());
		bookSerachByCombobox.getItems().addAll("ISBN", "표제");
		bookSerachByCombobox.getSelectionModel().select("ISBN");
		bookSearchByType = "ISBN";
		
		// 테이블 뷰 초기화
		TableColumn<LoanVO, String> isbnColumn = new TableColumn<>("ISBN");
		isbnColumn.setMinWidth(150);
		isbnColumn.setCellValueFactory(new PropertyValueFactory<>("bisbn"));
		
		TableColumn<LoanVO, String> titleColumn = new TableColumn<>("Title");
		titleColumn.setMinWidth(150);
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("btitle"));
		
		TableColumn<LoanVO, String> authorColumn = new TableColumn<>("Author");
		authorColumn.setMinWidth(150);
		authorColumn.setCellValueFactory(new PropertyValueFactory<>("bauthor"));
		
		TableColumn<LoanVO, String> translatorColumn = new TableColumn<>("Translator");
		translatorColumn.setMinWidth(150);
		translatorColumn.setCellValueFactory(new PropertyValueFactory<>("btranslator"));
		
		TableColumn<LoanVO, String> availableColumn = new TableColumn<>("Available");
		availableColumn.setMinWidth(150);
		availableColumn.setCellValueFactory(new PropertyValueFactory<>("available_kor"));
		
		bookSearchTableView.getColumns().addAll(
				isbnColumn, titleColumn, authorColumn, translatorColumn, availableColumn);
		
		bookSearchTableView.setRowFactory(e -> {
			TableRow<LoanVO> row = new TableRow<>();
			
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) { // 더블클릭
					System.out.println("Double Clicked");
		        } else if (event.getClickCount() == 1 && (!row.isEmpty())) {
		        	System.out.println("Clicked");
		        }				
			});
			
			return row;
		});
		
		// 선택 계정 및 책 초기화
		selectedAccount = null;
		selectedBook = null;
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
	
	@FXML
	private void hadleCombobox() {
		bookSearchByType = bookSerachByCombobox.getSelectionModel().getSelectedItem();
	}
	
	@FXML
	private void handleSearchBtn() {
		String query = bookSearchKeywordTextField.getText();
		if (query.equals("")) {
			(new Alert(
					AlertType.WARNING, "내용을 입력하세요.")).showAndWait();
			return;
		}
		if (bookSearchByType.equals("ISBN")) {
			GetLoanRecordsByISBNController controller = new GetLoanRecordsByISBNController();
			ObservableList<LoanVO> list = controller.exec(query);
			bookSearchTableView.setItems(list);
			if (list.isEmpty()) {
				(new Alert(
						AlertType.WARNING, "조건에 맞는 자료를 찾을 수 없습니다.")).showAndWait();
			}
		} else if (bookSearchByType.equals("표제")) {
			GetLoanRecordsByKeywordController controller = new GetLoanRecordsByKeywordController();
			ObservableList<LoanVO> list = controller.exec(query);
			bookSearchTableView.setItems(list);
			if (list.isEmpty()) {
				(new Alert(
						AlertType.WARNING, "조건에 맞는 자료를 찾을 수 없습니다.")).showAndWait();
			}
		}
	}
	
	@FXML
	private void handleSearchTextFieldEntered() {
		handleSearchBtn();
	}
	
	@FXML
	private void handleShowSearchAccoutModalBtn() {
		Parent modalRoot = null;
		controller = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("searchAccountsModalFXML.fxml"));
			modalRoot = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.setScene(new Scene(modalRoot));
		Platform.runLater(() -> {
			dialog.showAndWait();
			if (controller.getSelectedAccount() != null) {
				selectedAccount = controller.getSelectedAccount();
				currentUserLabel.setText(selectedAccount.getAname() + "(" + selectedAccount.getAbirth() +")");				
			}
		});
	}
	
	@FXML
	private void handleLendBtn() {
		
	}
	
	@FXML
	private void handleReuturnBtn() {
		
	}
}
