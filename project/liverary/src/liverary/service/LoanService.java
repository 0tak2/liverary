package liverary.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import liverary.Globals;
import liverary.dao.AccountDAO;
import liverary.dao.DBCPConnectionPool;
import liverary.dao.LoanDAO;
import liverary.util.DateHelper;
import liverary.vo.AccountVO;
import liverary.vo.LoanByAccountVO;
import liverary.vo.LoanVO;

public class LoanService {

	/* 대출 이력을 기록한다.
	 * @return 1: 대출처리 성공
	 * @return 11: 최대 대출 가능 한도를 넘어 대출할 수 없음.
	 * @return 12: 연체 도서가 있어 대출할 수 없습니다.
	 **/
	public int insertLoanRecord(LoanVO row) {
		
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		LoanDAO dao = new LoanDAO(con);
		
		// 대출 현황
		ObservableList<LoanByAccountVO> list = selectLoanWithAccountInfoOfAccount(row.getAno());
		int total = 0;
		int penalty = 0;
		if (list != null) {
			for (LoanByAccountVO item : list) {
				if (item.getLcreatedat() != null && item.getLreturnedAt() == null) {
					total++;
					LocalDate dueDate = LocalDate.parse(item.getLduedate(), DateTimeFormatter.ISO_DATE);
					if (DateHelper.getDifferenceByToday(dueDate) > 0) {
						penalty++;
					}
				}
			}
		}
		
		if (total >= Globals.getMaxLoanBooksAmount()) {
			return 11; // 최대 대출 가능 한도를 넘어 대출 불가
		} else if (penalty > 0) {
			return 12; // 연체 도서가 있어 대출 불가
		}
		
		int affectedRows = dao.insert(row);
		
		try {
			if (affectedRows == 1) {
				con.commit();
			} else {
				con.rollback();
			}
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return affectedRows;
	}

	public Boolean updateLoanRecord(LoanVO row) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 연체도서 확인
		boolean penalty = false;
		LocalDate dueDate = LocalDate.parse(row.getLduedate(), DateTimeFormatter.ISO_DATE);
		if (DateHelper.getDifferenceByToday(dueDate) > 0) {
			penalty = true;
		}
		
		// 반납처리
		boolean sucess = false;
		
		LoanDAO loanDao = new LoanDAO(con);
		int updateLoanAffectedRows = loanDao.update(row);
		
		AccountDAO accountDao = new AccountDAO(con);
		AccountVO account = accountDao.selectByNo(row.getAno());
	
		int updateAccountAffectedRows = 0;
		if (!penalty) { // 정상 반납
			int plusPointAmount = Globals.getPointPlusAmount();
			updateAccountAffectedRows = accountDao.updatePoint(row.getAno(), 
															account.getApoint() + plusPointAmount);			
		} else {
			int miunsPointAmount = Globals.getPointMinusAmount();
			updateAccountAffectedRows = accountDao.updatePoint(row.getAno(), 
															account.getApoint() - miunsPointAmount);
		}
		
		try {
			if (updateLoanAffectedRows == 1 && updateAccountAffectedRows == 1) {
				sucess = true;
				con.commit();
			} else {
				sucess = false;
				con.rollback();
			}
			
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return sucess;
	}

	public ObservableList<LoanVO> selectRecentLoanRecordsByISBN(String isbn) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		LoanDAO dao = new LoanDAO(con);
		ObservableList<LoanVO> list = dao.selectRecentByISBN(isbn);
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ObservableList<LoanVO> selectRecentLoanRecordsByKeyword(String keyword) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		LoanDAO dao = new LoanDAO(con);
		ObservableList<LoanVO> list = dao.selectRecentByKeyword(keyword);
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ObservableList<LoanByAccountVO> selectLoanWithAccountInfoOfAccount(int ano) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		LoanDAO dao = new LoanDAO(con);
		ObservableList<LoanByAccountVO> list = dao.selectWithAccountByAno(ano);
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public HashMap<String, Integer> selectLoanStatusOfAccount(int ano) {
		ObservableList<LoanByAccountVO> list = selectLoanWithAccountInfoOfAccount(ano);
		
		int total = 0;
		int penalty = 0;
		int normal = 0;
		if (list != null) {
			for (LoanByAccountVO row : list) {
				if (row.getLcreatedat() != null && row.getLreturnedAt() == null) { // 대출 기록이 있지만 반납되지 않은 경우
					total++;
					LocalDate dueDate = LocalDate.parse(row.getLduedate(), DateTimeFormatter.ISO_DATE);
					if (DateHelper.getDifferenceByToday(dueDate) > 0) {
						penalty++;
					}
				}
			}
			normal = total - penalty;
		}
		
		HashMap<String, Integer> result = new HashMap<>();
		result.put("TOTAL", total);
		result.put("PENALTY", penalty);
		result.put("NORMAL", normal);
		
		return result;
	}

	public ObservableList<LoanVO> selectLoanBookRowsOfAccount(int ano) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		LoanDAO dao = new LoanDAO(con);
		ObservableList<LoanVO> listBeforeProcessed = dao.selectByAno(ano);
		ObservableList<LoanVO> list = FXCollections.observableArrayList();
		for (LoanVO row : listBeforeProcessed) {
			if (row.getAvailable_kor().equals("반납완료")) {
				LocalDate returnedDate = LocalDate.parse(row.getLreturnedAt(), DateTimeFormatter.ISO_DATE);
				LocalDate dueDate = LocalDate.parse(row.getLduedate(), DateTimeFormatter.ISO_DATE);
				if (DateHelper.getDifferenceBetween(returnedDate, dueDate) > 0) {
					row.setAvailable_kor("반납완료 (연체)");
				} else {
					row.setAvailable_kor("반납완료 (정상)");
				}
			}
			list.add(row);
		}
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ObservableList<LoanVO> selectLoanBookRowsByKeywordWithDates(String keyword, String startDate, String endDate) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		LoanDAO dao = new LoanDAO(con);
		ObservableList<LoanVO> listBeforeProcessed = dao.selectByKeywordAndDate(keyword, startDate, endDate);
		ObservableList<LoanVO> list = FXCollections.observableArrayList();
		for (LoanVO row : listBeforeProcessed) {
			if (row.getAvailable_kor().equals("반납완료")) {
				LocalDate returnedDate = LocalDate.parse(row.getLreturnedAt(), DateTimeFormatter.ISO_DATE);
				LocalDate dueDate = LocalDate.parse(row.getLduedate(), DateTimeFormatter.ISO_DATE);
				if (DateHelper.getDifferenceBetween(returnedDate, dueDate) > 0) {
					row.setAvailable_kor("반납완료 (연체)");
				} else {
					row.setAvailable_kor("반납완료 (정상)");
				}
			}
			list.add(row);
		}
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ObservableList<LoanVO> selectLoanBookRowsByISBNWithDates(String isbn, String startDate, String endDate) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		LoanDAO dao = new LoanDAO(con);
		ObservableList<LoanVO> listBeforeProcessed = dao.selectByISBNAndDate(isbn, startDate, endDate);
		ObservableList<LoanVO> list = FXCollections.observableArrayList();
		for (LoanVO row : listBeforeProcessed) {
			if (row.getAvailable_kor().equals("반납완료")) {
				LocalDate returnedDate = LocalDate.parse(row.getLreturnedAt(), DateTimeFormatter.ISO_DATE);
				LocalDate dueDate = LocalDate.parse(row.getLduedate(), DateTimeFormatter.ISO_DATE);
				if (DateHelper.getDifferenceBetween(returnedDate, dueDate) > 0) {
					row.setAvailable_kor("반납완료 (연체)");
				} else {
					row.setAvailable_kor("반납완료 (정상)");
				}
			}
			list.add(row);
		}
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ObservableList<LoanVO> selectNoReturnedBookRowsByKeywordWithDates(String keyword, String startDate,
			String endDate) {
		
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		LoanDAO dao = new LoanDAO(con);
		ObservableList<LoanVO> listOfAll = dao.selectByKeywordAndDate(keyword, startDate, endDate);
		ObservableList<LoanVO> list = FXCollections.observableArrayList();
		for (LoanVO row : listOfAll) {
			if (row.getAvailable_kor().equals("대출중")) {
				list.add(row);
			}
		}
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ObservableList<LoanVO> selectNoReturnedBookRowsByISBNWithDates(String keyword, String startDate,
			String endDate) {
		
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		LoanDAO dao = new LoanDAO(con);
		ObservableList<LoanVO> listOfAll = dao.selectByISBNAndDate(keyword, startDate, endDate);
		ObservableList<LoanVO> list = FXCollections.observableArrayList();
		for (LoanVO row : listOfAll) {
			if (row.getAvailable_kor().equals("대출중")) {
				list.add(row);
			}
		}
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public boolean getIsNeededPenalty(LoanVO record) {
		boolean penalty = false;
		LocalDate dueDate = LocalDate.parse(record.getLduedate(), DateTimeFormatter.ISO_DATE);
	
		if (DateHelper.getDifferenceByToday(dueDate) > 0) {
			penalty = true;
		}
		return penalty;
	}

}
