package liverary.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import liverary.vo.LoanVO;

public class UserMainLayout implements Initializable {
	
	@FXML private Label loanStatusLabel;
	@FXML private Hyperlink detailLink;
	@FXML private Hyperlink editAccountInfoLink;
	
	@FXML private Label greetingLabel;
	@FXML private Label additionalInfoLabel;
	@FXML private Hyperlink logoutLink;
	
	@FXML private ComboBox<String> bookSerachByCombobox;
	@FXML private TextField bookSearchKeywordTextField;
	@FXML private Button bookSearchBtn;
	
	@FXML private TableView<LoanVO> bookSearchTableView;
	

	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		
	}

}
