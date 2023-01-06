package liverary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import liverary.util.DateHelper;
import liverary.vo.LoanByAccountVO;
import liverary.vo.LoanVO;

public class LoanDAO {

	private Connection con;
	
	public LoanDAO() {
	}
	
	public LoanDAO(Connection con) {
		this.con = con;
	}

	public int insert(LoanVO row) {
		int affectedRows = 0;
		try {
			String sql = "INSERT INTO loanrecordtbl(bisbn, ano, lcreatedAt, ldueDate, lreturnedAt) "
					+ "VALUES (?, ?, ?, ?, null)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, row.getBisbn());
			pstmt.setInt(2, row.getAno());
			pstmt.setString(3, DateHelper.todayDateStr());
			pstmt.setString(4, DateHelper.AddDaysToTodayDateStr(7));
			
			affectedRows = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return affectedRows;
	}

	public int update(LoanVO row) {
		int affectedRows = 0;
		try {
			String sql = "UPDATE loanRecordTBL SET lreturnedAt = ? WHERE lno = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, DateHelper.todayDateStr());
			pstmt.setInt(2, row.getLno());
			
			affectedRows = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return affectedRows;
	}

	public ObservableList<LoanVO> selectRecentByISBN(String isbn) {
		ObservableList<LoanVO> list = null;
		
		try {
			String sql = "SELECT * FROM recentLoanRecordByBookVIEW "
					+ "WHERE bisbn = ? "
					+ "ORDER BY btitle";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			
			ResultSet rs = pstmt.executeQuery();
			list = FXCollections.observableArrayList();
			
			while(rs.next()) {
				String available_kor = "대출불가";
				if (rs.getBoolean("available")) {
					available_kor = "대출가능";
				}
				
				LoanVO book = new LoanVO(rs.getInt("lno"),
											rs.getString("lcreatedat"),
											rs.getString("lduedate"),
											rs.getString("lreturnedAt"),
											rs.getString("bisbn"),
											rs.getString("btitle"),
											rs.getString("bdate"),
											rs.getInt("bpage"),
											rs.getInt("bprice"),
											rs.getString("bauthor"),
											rs.getString("btranslator"),
											rs.getString("bsupplement"),
											rs.getString("bpublisher"),
											rs.getBoolean("available"),
											rs.getInt("ano"),
											available_kor);
				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ObservableList<LoanVO> selectRecentByKeyword(String keyword) {
		ObservableList<LoanVO> list = null;
		
		try {
			String sql = "SELECT * FROM recentLoanRecordByBookVIEW "
					+ "WHERE btitle LIKE ? "
					+ "ORDER BY btitle";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			
			ResultSet rs = pstmt.executeQuery();
			list = FXCollections.observableArrayList();
			
			while(rs.next()) {
				String available_kor = "대출불가";
				if (rs.getBoolean("available")) {
					available_kor = "대출가능";
				}

				LoanVO book = new LoanVO(rs.getInt("lno"),
											rs.getString("lcreatedat"),
											rs.getString("lduedate"),
											rs.getString("lreturnedAt"),
											rs.getString("bisbn"),
											rs.getString("btitle"),
											rs.getString("bdate"),
											rs.getInt("bpage"),
											rs.getInt("bprice"),
											rs.getString("bauthor"),
											rs.getString("btranslator"),
											rs.getString("bsupplement"),
											rs.getString("bpublisher"),
											rs.getBoolean("available"),
											rs.getInt("ano"),
											available_kor);
				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ObservableList<LoanVO> selectByAno(int ano) {
		ObservableList<LoanVO> list = null;
		
		try {
			String sql = "SELECT * from loanRecordWithBookInfoVIEW "
					+ "WHERE ano = ? "
					+ "ORDER BY lno DESC";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ano);

			ResultSet rs = pstmt.executeQuery();
			list = FXCollections.observableArrayList();
			
			while(rs.next()) {
				String available_kor = "대출중";
				if (rs.getBoolean("available")) {
					available_kor = "반납완료";
				}
				
				LoanVO book = new LoanVO(rs.getInt("lno"),
											rs.getString("lcreatedat"),
											rs.getString("lduedate"),
											rs.getString("lreturnedAt"),
											rs.getString("bisbn"),
											rs.getString("btitle"),
											rs.getString("bdate"),
											rs.getInt("bpage"),
											rs.getInt("bprice"),
											rs.getString("bauthor"),
											rs.getString("btranslator"),
											rs.getString("bsupplement"),
											rs.getString("bpublisher"),
											rs.getBoolean("available"),
											rs.getInt("ano"),
											available_kor);
				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ObservableList<LoanVO> selectByKeywordAndDate(String keyword, String startDate, String endDate) {
		ObservableList<LoanVO> list = null;

		try {
			String sql = "SELECT * from loanRecordWithBookInfoVIEW "
					+ "WHERE btitle LIKE ? "
					+ "AND lcreatedAt BETWEEN ? AND ? "
					+ "ORDER BY L.lno DESC";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, startDate);
			pstmt.setString(3, endDate);
			ResultSet rs = pstmt.executeQuery();
			list = FXCollections.observableArrayList();
			
			while(rs.next()) {
				String available_kor = "대출중";
				if (rs.getBoolean("available")) {
					available_kor = "반납완료";
				}
				
				LoanVO book = new LoanVO(rs.getInt("lno"),
											rs.getString("lcreatedat"),
											rs.getString("lduedate"),
											rs.getString("lreturnedAt"),
											rs.getString("bisbn"),
											rs.getString("btitle"),
											rs.getString("bdate"),
											rs.getInt("bpage"),
											rs.getInt("bprice"),
											rs.getString("bauthor"),
											rs.getString("btranslator"),
											rs.getString("bsupplement"),
											rs.getString("bpublisher"),
											rs.getBoolean("available"),
											rs.getInt("ano"),
											available_kor);
				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ObservableList<LoanVO> selectByISBNAndDate(String bisbn, String startDate, String endDate) {
		ObservableList<LoanVO> list = null;

		try {
			String sql = "SELECT * from loanRecordWithBookInfoVIEW "
					+ "WHERE bisbn = ? "
					+ "AND lcreatedAt BETWEEN ? AND ? "
					+ "ORDER BY L.lno DESC";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bisbn);
			pstmt.setString(2, startDate);
			pstmt.setString(3, endDate);
			ResultSet rs = pstmt.executeQuery();
			list = FXCollections.observableArrayList();
			
			while(rs.next()) {
				String available_kor = "대출중";
				if (rs.getBoolean("available")) {
					available_kor = "반납완료";
				}
				
				LoanVO book = new LoanVO(rs.getInt("lno"),
											rs.getString("lcreatedat"),
											rs.getString("lduedate"),
											rs.getString("lreturnedAt"),
											rs.getString("bisbn"),
											rs.getString("btitle"),
											rs.getString("bdate"),
											rs.getInt("bpage"),
											rs.getInt("bprice"),
											rs.getString("bauthor"),
											rs.getString("btranslator"),
											rs.getString("bsupplement"),
											rs.getString("bpublisher"),
											rs.getBoolean("available"),
											rs.getInt("ano"),
											available_kor);
				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ObservableList<LoanByAccountVO> selectWithAccountByAno(int ano) {
		ObservableList<LoanByAccountVO> list = FXCollections.observableArrayList();
		
		try {
			String sql = "SELECT * FROM accountsWithLoanRecordsVIEW "
					+ "WHERE ano = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ano);
			ResultSet rs = pstmt.executeQuery();
			
			if (!rs.isBeforeFirst() ) {    
				return null;
			} else {
				while (rs.next()) {			
					LoanByAccountVO row = new LoanByAccountVO(rs.getInt("ano"), rs.getString("aname"), rs.getString("abirth"),
							rs.getString("acreatedAt"), rs.getString("aphone"), rs.getString("aemail"),
							rs.getString("aaddr"), rs.getInt("apoint"), rs.getInt("alevel"), rs.getString("ausername"),
							rs.getInt("lno"), rs.getString("bisbn"), rs.getString("lcreatedAt"),
							rs.getString("lduedate"), rs.getString("lreturnedAt"));	
					list.add(row);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
