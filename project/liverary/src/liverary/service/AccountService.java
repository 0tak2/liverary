package liverary.service;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import liverary.dao.AccountDAO;
import liverary.dao.LoanDAO;
import liverary.mybatis.MyBatisConnectionFactory;
import liverary.util.DateHelper;
import liverary.vo.AccountVO;
import liverary.vo.LoanOptionVO;
import liverary.vo.LoanVO;

public class AccountService {

	public AccountVO selectAccountbyUsername(String username, boolean includeDisabled, boolean isStaff) {
		AccountVO account = null;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		AccountDAO dao = new AccountDAO(session);
		
		AccountVO targetAccount = new AccountVO();
		targetAccount.setAusername(username);
		if (isStaff) {
			targetAccount.setAlevel(1);
		}
		
		try {
			if (includeDisabled) {
				account = dao.selectOneIncludeDisabled(targetAccount);			
			} else {
				account = dao.selectOne(targetAccount);	
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return account;
	}
	
	public AccountVO selectAccountbyUsername(String username) {
		return selectAccountbyUsername(username, false, false);
	}

	public AccountVO selectByUsernameAndPassword(String username, String password) {
		AccountVO account = null;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		AccountDAO dao = new AccountDAO(session);
		
		AccountVO targetAccount = new AccountVO();
		targetAccount.setAusername(username);
		targetAccount.setApassword(password);
		
		try {
			account = dao.selectOneIncludeDisabled(targetAccount);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return account;
	}

	public AccountVO selectAccountbyNO(int no) {
		AccountVO account = null;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		AccountDAO dao = new AccountDAO(session);
		
		AccountVO targetAccount = new AccountVO();
		targetAccount.setAno(no);
		
		try {
			account = dao.selectOne(targetAccount);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return account;
	}

	public ObservableList<AccountVO> selectAccountsByName(String name, boolean isStaff) {
		List<AccountVO> list = null;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		AccountDAO dao = new AccountDAO(session);
		
		AccountVO targetAccount = new AccountVO();
		targetAccount.setAname(name);
		
		if (isStaff) {
			targetAccount.setAlevel(1);			
		}
		
		try {
			list = dao.selectList(targetAccount);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}		
		
		ObservableList<AccountVO> obList = FXCollections.observableArrayList();
		for (AccountVO account : list) {
			obList.add(account);
		}
		
		return obList;
	}

	public ObservableList<AccountVO> selectAccountsByName(String name) {
		return selectAccountsByName(name, false);
	}
	
	public boolean insertNewAccount(AccountVO account) {
		boolean result = false;
		int affectedRows = 0;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		AccountDAO dao = new AccountDAO(session);
		
		try {
			affectedRows = dao.insert(account);
		} catch (Exception e) {
			session.rollback();
			session.close();
			e.printStackTrace();
			result = false;
		} finally {
			if (affectedRows == 1) {
				session.commit();
				result = true;
			} else {
				session.rollback();
				result = false;
			}
			session.close();
		}
		
		return result;
	}
	
	public boolean updateAccount(AccountVO account) {
		boolean result = false;
		int affectedRows = 0;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		AccountDAO dao = new AccountDAO(session);
		
		try {
			affectedRows = dao.update(account);			
		} catch (Exception e) {
			result = false;
			session.rollback();
			session.close();
			e.printStackTrace();
		} finally {
			if (affectedRows == 1) {
				result = true;
				session.commit();
				session.close();
			} else {
				result = false;
				session.rollback();
				session.close();
			}
		}
		
		return result;
	}

	/*
	 * @return 성공시 1, 대출도서가 있어서 불가시 11
	 **/
	public int updateAccountToEmptyRow(int ano, String username) {
		int affectedRows = 0;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		LoanDAO loanDao = new LoanDAO(session);
		
		LoanOptionVO target = new LoanOptionVO();
		target.setAno(ano);
		
		List<LoanVO> list = loanDao.select(target);

		for (LoanVO row : list) {
			if (row.getLno() == 0) {
				continue;
			}
			
			if(row.getLreturnedAt() == null) {
				session.close();
				return 11;
			}
		}
		
		AccountDAO accountDao = new AccountDAO(session);
		AccountVO account = new AccountVO(ano, "", "", LocalDate.of(1970, 1, 1), LocalDate.of(1970, 1, 1),
											"", "", "", -1, -1, username, "");
		account.setAdisabled(true);
		account.setAdisabledAt(DateHelper.todayDate());
		
		try {
			affectedRows = accountDao.update(account);			
		} catch (Exception e) {
			session.rollback();
			session.close();
			e.printStackTrace();
		} finally {
			if (affectedRows == 1) {
				session.commit();
				session.close();
			} else {
				session.rollback();
				session.close();
			}
		}
		
		return affectedRows;
	}

}
