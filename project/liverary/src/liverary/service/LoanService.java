package liverary.service;

import java.sql.Connection;
import java.sql.SQLException;

import javafx.collections.ObservableList;
import liverary.dao.DBCPConnectionPool;
import liverary.dao.LoanDAO;
import liverary.vo.LoanVO;

public class LoanService {

	public ObservableList<LoanVO> selectLoanRecordsByISBN(String isbn) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		LoanDAO dao = new LoanDAO(con);
		ObservableList<LoanVO> list = dao.selectByISBN(isbn);
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ObservableList<LoanVO> selectLoanRecordsByKeyword(String keyword) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		LoanDAO dao = new LoanDAO(con);
		ObservableList<LoanVO> list = dao.selectByKeyword(keyword);
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
