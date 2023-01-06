package liverary.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import liverary.Globals;
import liverary.retrofit.NlRetrofitClient;
import liverary.retrofit.NlService;
import liverary.vo.NlBook;
import liverary.vo.NlBooksResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchBookFromNLModalController implements Initializable {
	@FXML private TableView<NlBook> searchTableView;
	@FXML private Button selectBtn;
	
	private NlBook selectedBook;
	
	private String key;
	private int maxItems;
	private NlRetrofitClient retrofitClient;
	private NlService retrofitService;
	
	public SearchBookFromNLModalController() {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		key = Globals.getNlApiSecret();
		maxItems = Globals.getNlApiMaxItems();
		
		retrofitClient = NlRetrofitClient.getInstance();
		retrofitService = retrofitClient.getRetrofitInterface();
		
		TableColumn<NlBook, String> titleInfoColumn = new TableColumn<>("표제");
		titleInfoColumn.setMinWidth(300);
		titleInfoColumn.setCellValueFactory(new PropertyValueFactory<>("titleInfo"));
		
		TableColumn<NlBook, String> authorInfoColumn = new TableColumn<>("주저자");
		authorInfoColumn.setMinWidth(150);
		authorInfoColumn.setCellValueFactory(new PropertyValueFactory<>("authorInfo"));
		
		TableColumn<NlBook, String> pubInfoColumn = new TableColumn<>("출판사");
		pubInfoColumn.setMinWidth(75);
		pubInfoColumn.setCellValueFactory(new PropertyValueFactory<>("pubInfo"));
		
		TableColumn<NlBook, String> isbnColumn = new TableColumn<>("ISBN");
		isbnColumn.setMinWidth(75);
		isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		
		searchTableView.getColumns().addAll(
				titleInfoColumn, authorInfoColumn, pubInfoColumn, isbnColumn);
		
		searchTableView.setRowFactory(e -> {
			TableRow<NlBook> row = new TableRow<>();
			
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty()) {
					NlBook account = row.getItem();
					selectedBook = account;
				}
			});
			return row;
		});
	}
	
	public void setDataAndRender(String keyword) {
		selectedBook = null;
		
		retrofitService.getNlBooksResult(key, maxItems, keyword).enqueue(new Callback<NlBooksResult>() {
			@Override
			public void onResponse(Call<NlBooksResult> call, Response<NlBooksResult> response) {
				NlBooksResult result = response.body();
				ArrayList<NlBook> arrayList = (ArrayList<NlBook>) result.getResult();
				
				ObservableList<NlBook> list = FXCollections.observableArrayList();
				for (NlBook item : arrayList) {
					String regexp = "<[^>]*>?";
					
					item.setTitleInfo(item.getTitleInfo().replaceAll(regexp, ""));
					item.setAuthorInfo(item.getAuthorInfo().replaceAll(regexp, ""));
					item.setPubInfo(item.getPubInfo().replaceAll(regexp, ""));
					item.setIsbn(item.getIsbn().replaceAll(regexp, ""));
					
					list.add(item);
				}
				
				Platform.runLater(() -> {
					searchTableView.setItems(list);
				});
			}
			
			@Override
			public void onFailure(Call<NlBooksResult> call, Throwable t) {
				System.err.println("Request Failed");
			}
			
		});
	}
	
	@FXML
	private void handleSelectBtn(ActionEvent e) {
		if (selectedBook == null) {
			(new Alert(
					AlertType.WARNING, "선택한 자료 정보가 없습니다.")).showAndWait();
			return;
		}
		
	    Node node = (Node) e.getSource();
	    Stage thisStage = (Stage) node.getScene().getWindow();
	    thisStage.close();
	}
	
	public NlBook getSelectedBook() {
		return selectedBook;
	}
}
