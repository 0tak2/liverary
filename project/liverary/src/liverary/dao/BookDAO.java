package liverary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import liverary.vo.BookVO;

public class BookDAO {
	private Connection con;
	
	public BookDAO() {
	}
	
	public BookDAO(Connection con) {
		this.con = con;
	}

	public ObservableList<BookVO> selectByISBN(String isbn) {
		ObservableList<BookVO> list = null;
		
		try {
			String sql = "SELECT bisbn, btitle, bauthor, bprice "
					+ "FROM booksTBL "
					+ "WHERE bisbn = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			ResultSet rs = pstmt.executeQuery();
			list = FXCollections.observableArrayList();
			while(rs.next()) {
				BookVO book = new BookVO(rs.getString("bisbn"), rs.getString("btitle"),
						rs.getString("bauthor"), rs.getInt("bprice"));
				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ObservableList<BookVO> selectByKeyword(String keyword) {
ObservableList<BookVO> list = null;
		
		try {
			String sql = "SELECT bisbn, btitle, bauthor, bprice "
					+ "FROM booksTBL "
					+ "WHERE btitle LIKE ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();
			list = FXCollections.observableArrayList();
			while(rs.next()) {
				BookVO book = new BookVO(rs.getString("bisbn"), rs.getString("btitle"),
						rs.getString("bauthor"), rs.getInt("bprice"));
				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
