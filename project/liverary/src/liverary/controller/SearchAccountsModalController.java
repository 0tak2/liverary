package liverary.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import liverary.service.AccountService;
import liverary.vo.AccountVO;

public class SearchAccountsModalController implements Initializable {
	@FXML private ComboBox<String> searchByComboBox;
	@FXML private TextField searchQueryTextField;
	@FXML private Button searchBtn;
	@FXML private TableView<AccountVO> searchTableView;
	@FXML private Button selectBtn;
	
	private String searchBy;
	private AccountVO selectedAccount;
	
	public SearchAccountsModalController() {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		searchByComboBox.getItems().removeAll(searchByComboBox.getItems());
		searchByComboBox.getItems().addAll("아이디", "이름");
		searchByComboBox.getSelectionModel().select("아이디");
		searchBy = "아이디";
		
		TableColumn<AccountVO, Integer> noColumn = new TableColumn<>("등록번호");
		noColumn.setMinWidth(20);
		noColumn.setCellValueFactory(new PropertyValueFactory<>("ano"));
		
		TableColumn<AccountVO, String> useridColumn = new TableColumn<>("아이디");
		useridColumn.setMinWidth(150);
		useridColumn.setCellValueFactory(new PropertyValueFactory<>("ausername"));
		
		TableColumn<AccountVO, String> nameColumn = new TableColumn<>("이름");
		nameColumn.setMinWidth(150);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("aname"));
		
		TableColumn<AccountVO, String> birthColumn = new TableColumn<>("생년월일");
		birthColumn.setMinWidth(150);
		birthColumn.setCellValueFactory(new PropertyValueFactory<>("abirth"));
		
		TableColumn<AccountVO, String> phoneColumn = new TableColumn<>("연락처");
		phoneColumn.setMinWidth(150);
		phoneColumn.setCellValueFactory(new PropertyValueFactory<>("aphone"));
		
		TableColumn<AccountVO, String> pointColumn = new TableColumn<>("포인트");
		pointColumn.setMinWidth(20);
		pointColumn.setCellValueFactory(new PropertyValueFactory<>("apoint"));
		
		searchTableView.getColumns().addAll(
				noColumn, useridColumn, nameColumn, birthColumn, phoneColumn, pointColumn);
		
		searchTableView.setRowFactory(e -> {
			TableRow<AccountVO> row = new TableRow<>();
			
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty()) {
					AccountVO account = row.getItem();
					selectedAccount = account;
				}
			});
			return row;
		});
		
		selectedAccount = null;
	}
	
	@FXML
	private void hadleCombobox() {
		searchBy = searchByComboBox.getSelectionModel().getSelectedItem();
	}

	@FXML
	private void handleSearchBtn() {
		String query = searchQueryTextField.getText();
		if (searchBy.equals("아이디")) {
			AccountService service = new AccountService();
			AccountVO account = service.selectAccountbyUsername(query);
			
			ObservableList<AccountVO> list = null;
			if (account != null) {
				list = FXCollections.observableArrayList();
				list.add(account);				
			}
			searchTableView.setItems(list);
		} else if (searchBy.equals("이름")) {
			AccountService service = new AccountService();
			ObservableList<AccountVO> list = service.selectAccountsByName(query);
			searchTableView.setItems(list);
		}
	}
	
	@FXML
	private void handleSearchTextFieldEntered() {
		handleSearchBtn();
	}
	
	@FXML
	private void handleSelectBtn(ActionEvent e) {
		if (selectedAccount == null) {
			(new Alert(
					AlertType.WARNING, "선택한 회원정보가 없습니다.")).showAndWait();
			return;
		}
		
		if (selectedAccount.getApoint() < 0) {
			(new Alert(
					AlertType.ERROR, "포인트가 0 미만입니다. 이 사용자는 대출할 수 없습니다.")).showAndWait();
			selectedAccount = null;
			return;
		}
		
	    Node node = (Node) e.getSource();
	    Stage thisStage = (Stage) node.getScene().getWindow();
	    thisStage.close();
	}
	
	public AccountVO getSelectedAccount() {
		return selectedAccount;
	}
}
