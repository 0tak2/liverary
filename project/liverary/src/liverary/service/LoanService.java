package liverary.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import liverary.Globals;
import liverary.dao.AccountDAO;
import liverary.dao.LoanDAO;
import liverary.mybatis.MyBatisConnectionFactory;
import liverary.util.DateHelper;
import liverary.vo.AccountVO;
import liverary.vo.LoanByAccountVO;
import liverary.vo.LoanOptionVO;
import liverary.vo.LoanVO;

public class LoanService {

	public List<LoanVO> selectRecentLoanRecordsByISBN(String isbn) {
		List<LoanVO> list = null;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		LoanDAO dao = new LoanDAO(session);
		LoanVO target = new LoanVO();
		target.setBisbn(isbn);
		
		try {
			list = dao.selectRecent(target);				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		for (LoanVO row : list) {
			String available_kor = "대출불가";
			if (row.isAvailable()) {
				available_kor = "대출가능";
			}
			row.setAvailable_kor(available_kor);
		}
		
		return list;
	}

	public List<LoanVO> selectRecentLoanRecordsByKeyword(String keyword) {
		List<LoanVO> list = null;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		LoanDAO dao = new LoanDAO(session);
		LoanVO target = new LoanVO();
		target.setBtitle(keyword);
		
		try {
			list = dao.selectRecent(target);				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		for (LoanVO row : list) {
			String available_kor = "대출불가";
			if (row.isAvailable()) {
				available_kor = "대출가능";
			}
			row.setAvailable_kor(available_kor);
		}
		
		return list;
	}

	public List<LoanByAccountVO> selectLoanWithAccountInfoOfAccount(int ano) {
		List<LoanByAccountVO> list = null;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		LoanDAO dao = new LoanDAO(session);
		LoanByAccountVO target = new LoanByAccountVO();
		target.setAno(ano);
		
		try {
			list = dao.selectWithAccount(target);				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return list;
	}

	public HashMap<String, Integer> selectLoanStatusOfAccount(int ano) {
		List<LoanByAccountVO> list = selectLoanWithAccountInfoOfAccount(ano);
		
		int total = 0;
		int penalty = 0;
		int normal = 0;
		if (list != null) {
			for (LoanByAccountVO row : list) {
				if (row.getLcreatedAt() != null && row.getLreturnedAt() == null) { // 대출 기록이 있지만 반납되지 않은 경우
					total++;
					LocalDate dueDate = LocalDate.parse(row.getLdueDate(), DateTimeFormatter.ISO_DATE);
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

	public List<LoanVO> selectLoanBookRowsOfAccount(int ano) {
		List<LoanVO> list = null;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		LoanDAO dao = new LoanDAO(session);
		LoanOptionVO target = new LoanOptionVO();
		target.setAno(ano);
		
		try {
			list = dao.select(target);				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		for (LoanVO row : list) {
			if (row.getAvailable_kor().equals("반납완료")) {
				LocalDate returnedDate = row.getLreturnedAt();
				LocalDate dueDate = row.getLdueDate();
				if (DateHelper.getDifferenceBetween(returnedDate, dueDate) > 0) {
					row.setAvailable_kor("반납완료 (연체)");
				} else {
					row.setAvailable_kor("반납완료 (정상)");
				}
			}
		}
		
		return list;
	}
	
	public List<LoanVO> selectLoanBookRowsByKeywordWithDates(String keyword, String startDateStr, String endDateStr) {		
		List<LoanVO> list = null;
		
		LocalDate startDate = DateHelper.ConvertStrToLocalDate(startDateStr);
		LocalDate endDate = DateHelper.ConvertStrToLocalDate(endDateStr);
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		LoanDAO dao = new LoanDAO(session);
		LoanOptionVO target = new LoanOptionVO();
		target.setBtitle(keyword);
		target.setLcreatedAtStart(startDate);
		target.setLcreatedAtEnd(endDate);
		
		try {
			list = dao.select(target);				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		for (LoanVO row : list) {
			if (row.getAvailable_kor().equals("반납완료")) {
				LocalDate returnedDate = row.getLreturnedAt();
				LocalDate dueDate = row.getLdueDate();
				if (DateHelper.getDifferenceBetween(returnedDate, dueDate) > 0) {
					row.setAvailable_kor("반납완료 (연체)");
				} else {
					row.setAvailable_kor("반납완료 (정상)");
				}
			}
		}
		
		return list;
	}

	public List<LoanVO> selectLoanBookRowsByISBNWithDates(String isbn, String startDateStr, String endDateStr) {
		List<LoanVO> list = null;
		
		LocalDate startDate = DateHelper.ConvertStrToLocalDate(startDateStr);
		LocalDate endDate = DateHelper.ConvertStrToLocalDate(endDateStr);
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		LoanDAO dao = new LoanDAO(session);
		LoanOptionVO target = new LoanOptionVO();
		target.setBisbn(isbn);
		target.setLcreatedAtStart(startDate);
		target.setLcreatedAtEnd(endDate);
		
		try {
			list = dao.select(target);				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		for (LoanVO row : list) {
			if (row.getAvailable_kor().equals("반납완료")) {
				LocalDate returnedDate = row.getLreturnedAt();
				LocalDate dueDate = row.getLdueDate();
				if (DateHelper.getDifferenceBetween(returnedDate, dueDate) > 0) {
					row.setAvailable_kor("반납완료 (연체)");
				} else {
					row.setAvailable_kor("반납완료 (정상)");
				}
			}
		}
		
		return list;
	}

	public List<LoanVO> selectNoReturnedBookRowsByKeywordWithDates(String keyword, String startDateStr,
			String endDateStr) {
		List<LoanVO> list = null;
		
		LocalDate startDate = DateHelper.ConvertStrToLocalDate(startDateStr);
		LocalDate endDate = DateHelper.ConvertStrToLocalDate(endDateStr);
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		LoanDAO dao = new LoanDAO(session);
		LoanOptionVO target = new LoanOptionVO();
		target.setBtitle(keyword);
		target.setLcreatedAtStart(startDate);
		target.setLcreatedAtEnd(endDate);
		
		try {
			list = dao.select(target);				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		for (LoanVO row : list) {
			if (!row.isAvailable()) {
				row.setAvailable_kor("대출중");			
			}
		}
		
		return list;
	}
	
	public List<LoanVO> selectNoReturnedBookRowsByISBNWithDates(String isbn, String startDateStr,
			String endDateStr) {
		List<LoanVO> list = null;
		
		LocalDate startDate = DateHelper.ConvertStrToLocalDate(startDateStr);
		LocalDate endDate = DateHelper.ConvertStrToLocalDate(endDateStr);
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		LoanDAO dao = new LoanDAO(session);
		LoanOptionVO target = new LoanOptionVO();
		target.setBisbn(isbn);
		target.setLcreatedAtStart(startDate);
		target.setLcreatedAtEnd(endDate);
		
		try {
			list = dao.select(target);				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		for (LoanVO row : list) {
			if (!row.isAvailable()) {
				row.setAvailable_kor("대출중");			
			}
		}
		
		return list;
	}

	public boolean getIsNeededPenalty(LoanVO row) {
		boolean penalty = false;
		LocalDate dueDate = row.getLdueDate();
	
		if (DateHelper.getDifferenceByToday(dueDate) > 0) {
			penalty = true;
		}
		return penalty;
	}

	/* 대출 이력을 기록한다.
	 * @return 1: 대출처리 성공
	 * @return 11: 최대 대출 가능 한도를 넘어 대출할 수 없음.
	 * @return 12: 연체 도서가 있어 대출할 수 없음.
	 **/
	public int insertLoanRecord(LoanVO row) {
		List<LoanByAccountVO> list = null;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		LoanDAO dao = new LoanDAO(session);
		
		// 대출 현황 확인
		LoanByAccountVO target = new LoanByAccountVO();
		target.setAno(row.getAno());
		
		try {
			list = dao.selectWithAccount(target);				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int total = 0;
		int penalty = 0;
		if (list != null) {
			for (LoanByAccountVO item : list) {
				if (item.getLcreatedAt() != null && item.getLreturnedAt() == null) {
					total++;
					LocalDate dueDate = LocalDate.parse(item.getLdueDate(), DateTimeFormatter.ISO_DATE);
					if (DateHelper.getDifferenceByToday(dueDate) > 0) {
						penalty++;
					}
				}
			}
		}
		
		if (total >= Globals.getMaxLoanBooksAmount()) {
			session.rollback();
			session.close();
			return 11; // 최대 대출 가능 한도를 넘어 대출 불가
		} else if (penalty > 0) {
			session.rollback();
			session.close();
			return 12; // 연체 도서가 있어 대출 불가
		}

		// 대출 처리
		int affectedRows = 0;
		
		try {
			affectedRows = dao.insert(row);
		} catch (Exception e) {
			session.rollback();
			session.close();
			e.printStackTrace();
		} finally {
			if (affectedRows == 1) {
				session.commit();
			} else {
				session.rollback();
			}
			session.close();
		}
		
		return affectedRows;
	}

	public Boolean updateLoanRecord(LoanVO row) {
		row.setLreturnedAt(DateHelper.todayDate());
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		// 연체도서 확인
		boolean penalty = false;
		LocalDate dueDate = row.getLdueDate();
		if (DateHelper.getDifferenceByToday(dueDate) > 0) {
			penalty = true;
		}
		
		// 반납 처리
		boolean sucess = false;
		
		LoanDAO loanDao = new LoanDAO(session);
		
		// 포인트 처리
		AccountDAO accountDao = new AccountDAO(session);
		AccountVO target = new AccountVO();
		target.setAno(row.getAno());
		AccountVO account = accountDao.selectOne(target);
		
		if (!penalty) { // 정상 반납
			int plusPointAmount = Globals.getPointPlusAmount();
			account.setApoint(account.getApoint() + plusPointAmount);	
		} else { // 연체 반납
			int miunsPointAmount = Globals.getPointMinusAmount();
			account.setApoint(account.getApoint() - miunsPointAmount);
		}
		
		int updateLoanAffectedRows = 0;
		int updateAccountAffectedRows = 0;
		
		try {
			updateLoanAffectedRows = loanDao.update(row);
			updateAccountAffectedRows = accountDao.update(account);
		} catch (Exception e) {
			session.rollback();
			session.close();
			e.printStackTrace();
		} finally {
			if (updateLoanAffectedRows == 1 && updateAccountAffectedRows == 1) {
				sucess = true;
				session.commit();
			} else {
				sucess = false;
				session.rollback();
			}
			session.close();
		}

		return sucess;
	}
}
