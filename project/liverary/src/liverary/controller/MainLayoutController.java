package liverary.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import javafx.scene.control.ButtonType;
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
import liverary.service.AccountService;
import liverary.service.LoanService;
import liverary.util.DateHelper;
import liverary.view.LayoutsEnum;
import liverary.view.StageManager;
import liverary.vo.AccountVO;
import liverary.vo.LoanVO;

public class MainLayoutController implements Initializable {

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
	
	private String bookSearchByType;
	
	private SearchAccountsModalController controller;
	private AccountVO selectedAccount;
	private LoanVO selectedBook;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// ?????? ?????? ??????
		try {
			menuComponent = FXMLLoader.load(getClass().getResource("../view/menuComponentFXML.fxml"));
			rootVBox.getChildren().add(0, menuComponent);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// ?????? ?????? ?????????
		// ??????
		todayLabel.setText(DateHelper.todayDateStr());
		dueDateLabel.setText("???????????????: " + String.valueOf(DateHelper.AddDaysToTodayDate(Globals.getLoanDays())));
		// ?????????
		greetingLabel.setText(Globals.getCurrentSessionName() + "(" + Globals.getCurrentSessionUsername() + ")??? ???????????????.");
		additionalInfoLabel.setText(Globals.getCurrentSessionDepartment() + " | ??????" + Globals.getCurrentSessionLevel());
		
		// ???????????? ?????????
		bookSerachByCombobox.getItems().removeAll(bookSerachByCombobox.getItems());
		bookSerachByCombobox.getItems().addAll("ISBN", "??????");
		bookSerachByCombobox.getSelectionModel().select("ISBN");
		bookSearchByType = "ISBN";
		
		// ????????? ??? ?????????
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
				selectedBook = row.getItem();			
			});
			
			return row;
		});
		
		// ?????? ?????? ??? ??? ?????????
		selectedAccount = null;
		selectedBook = null;
	}
	
	@FXML
	private void handleLogout() {
		Globals.setCurrentSession(null);
		StageManager manager = StageManager.getInstance();
		try {
			manager.switchToWithHide(LayoutsEnum.LoginLayout);
			manager.freeAll();
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
					AlertType.WARNING, "????????? ???????????????.")).showAndWait();
			return;
		}
		if (bookSearchByType.equals("ISBN")) {
			LoanService service = new LoanService();
			List<LoanVO> list = service.selectRecentLoanRecordsByISBN(query);
			ObservableList<LoanVO> obList = FXCollections.observableArrayList(list);
			
			bookSearchTableView.setItems(obList);
			if (list.isEmpty()) {
				(new Alert(
						AlertType.WARNING, "????????? ?????? ????????? ?????? ??? ????????????.")).showAndWait();
			}
		} else if (bookSearchByType.equals("??????")) {
			LoanService service = new LoanService();
			List<LoanVO> list = service.selectRecentLoanRecordsByKeyword(query);
			ObservableList<LoanVO> obList = FXCollections.observableArrayList(list);
			
			bookSearchTableView.setItems(obList);
			if (list.isEmpty()) {
				(new Alert(
						AlertType.WARNING, "????????? ?????? ????????? ?????? ??? ????????????.")).showAndWait();
			}
		}
	}
	
	@FXML
	private void handleSearchTextFieldEntered() {
		bookSearchBtn.fire();
	}
	
	@FXML
	private void handleShowSearchAccoutModalBtn() {
		Parent modalRoot = null;
		controller = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/searchAccountsModalFXML.fxml"));
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
		if (selectedBook == null) {
			(new Alert(
					AlertType.ERROR, "?????? ????????? ?????????????????????.")).showAndWait();
			return;
		}
		
		if (selectedAccount == null) {
			(new Alert(
					AlertType.ERROR, "?????? ???????????? ?????????????????????.")).showAndWait();
			return;
		}
		
		if (selectedBook.isAvailable()) {		
			selectedBook.setAno(selectedAccount.getAno());
			selectedBook.setLcreatedAt(DateHelper.todayDate());
			selectedBook.setLdueDate(DateHelper.AddDaysToTodayDate(Globals.getLoanDays()));
			LoanService service = new LoanService();
			int result = service.insertLoanRecord(selectedBook);
			
			if (result == 1) {
				String msg = "?????? ????????? ?????????????????????.\n\n"
						+ "?????? ??????: " + selectedBook.getBtitle() + "(" + selectedBook.getBisbn() + ")\n\n"
								+ "?????????: " + selectedAccount.getAname() + "(" + selectedAccount.getAbirth() + ")";
				(new Alert(	AlertType.INFORMATION, msg)).showAndWait();
				bookSearchTableView.setItems(FXCollections.<LoanVO>observableArrayList());
				selectedBook = null;
			} else if (result == 11) {
				(new Alert(
						AlertType.ERROR, "?????? ?????? ????????? ?????? ????????? ??? ????????????. ?????? ????????????: "
								+ Globals.getMaxLoanBooksAmount())).showAndWait();
			} else if (result == 12) {
				(new Alert(
						AlertType.ERROR, "?????? ???????????? ????????? ?????? ????????? ??? ????????????.")).showAndWait();
			} else {
				(new Alert(
						AlertType.ERROR, "?????? ????????? ?????????????????????. ????????? ???????????? ??????????????? ??????????????????.")).showAndWait();
			}
			
		} else {
			(new Alert(
					AlertType.ERROR, "?????? ????????? ???????????? ???????????????.")).showAndWait();
		}
	}
	
	@FXML
	private void handleReuturnBtn() {
		if (selectedBook == null) {
			(new Alert(
					AlertType.ERROR, "?????? ????????? ?????????????????????.")).showAndWait();
			return;
		}
		
		if (selectedBook.isAvailable()) {
			(new Alert(
					AlertType.ERROR, "?????? ???????????? ?????? ????????? ?????????????????????.")).showAndWait();
			return;
		}
		
		LoanService loanService = new LoanService();
		boolean penalty = loanService.getIsNeededPenalty(selectedBook);
		if (penalty) {
			AccountService service = new AccountService();
			AccountVO returnAccount = service.selectAccountbyNO(selectedBook.getAno());
			
			String msg = "???????????? ??????\n\n"
					+ "?????? ??????: " + selectedBook.getBtitle() + "(" + selectedBook.getBisbn() + ")\n\n"
					+ "?????? ????????????: " + selectedBook.getLdueDate() + "\n\n"
					+ "?????????: " + returnAccount.getAname() + "(" + returnAccount.getAbirth() + ")\n\n"
					+ "????????? ??????: " + returnAccount.getApoint() + "->" + 
											(returnAccount.getApoint() - Globals.getPointMinusAmount());
			Optional<ButtonType> result = new Alert(AlertType.CONFIRMATION, msg).showAndWait();
			if(!result.isPresent()) {
				return;
			} else if(result.get() == ButtonType.OK) {
				//
			} else if(result.get() == ButtonType.CANCEL) {
				return;
			}
			
		}
		
		boolean sucess = loanService.updateLoanRecord(selectedBook);
		
		if (sucess) {
			AccountService accountService = new AccountService();
			AccountVO returnedAccount = accountService.selectAccountbyNO(selectedBook.getAno());
			String msg = "?????? ????????? ?????????????????????.\n\n"
							+ "?????? ??????: " + selectedBook.getBtitle() + "(" + selectedBook.getBisbn() + ")\n\n"
									+ "?????????: " + returnedAccount.getAname() + "(" + returnedAccount.getAbirth() + ")";
			(new Alert(	AlertType.INFORMATION, msg)).showAndWait();
			bookSearchTableView.setItems(FXCollections.<LoanVO>observableArrayList());
			selectedBook = null;
		} else {
			(new Alert(
					AlertType.ERROR, "?????? ????????? ?????????????????????. ????????? ???????????? ??????????????? ??????????????????.")).showAndWait();
		}
	}
}
