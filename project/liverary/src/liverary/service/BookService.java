package liverary.service;

import java.sql.Connection;
import java.sql.SQLException;

import javafx.collections.ObservableList;
import liverary.dao.AccountDAO;
import liverary.dao.BookDAO;
import liverary.dao.DBCPConnectionPool;
import liverary.vo.BookVO;
import liverary.vo.LoanVO;

public class BookService {

	public ObservableList<BookVO> selectBooksByISBN(String isbn) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
			// con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		BookDAO dao = new BookDAO(con);
		ObservableList<BookVO> list = dao.selectByISBN(isbn);
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ObservableList<BookVO> selectBooksByKeyword(String keyword) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
			// con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		BookDAO dao = new BookDAO(con);
		ObservableList<BookVO> list = dao.selectByKeyword(keyword);
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public boolean insertNewBook(BookVO book) {
		boolean result = false;
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		BookDAO dao = new BookDAO(con);
		int affectedRows = dao.insert(book);
		
		try {
			if (affectedRows == 1) {
				result = true;
				con.commit();
			} else {
				result = false;
				con.rollback();
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
