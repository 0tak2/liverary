package liverary.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import liverary.dao.AccountDAO;
import liverary.dao.AccountDAOOld;
import liverary.dao.DBCPConnectionPool;
import liverary.dao.LoanDAO;
import liverary.mybatis.MyBatisConnectionFactory;
import liverary.util.DateHelper;
import liverary.vo.AccountVO;
import liverary.vo.LoanVO;

public class AccountService {

	public AccountVO selectAccountbyUsername(String username, boolean includeDisabled, boolean isStaff) {
		AccountVO account = null;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		AccountDAO dao = new AccountDAO(factory);
		
		AccountVO targetAccount = new AccountVO();
		targetAccount.setAusername(username);
		if (isStaff) {
			targetAccount.setAlevel(1);
		}
		
		if (includeDisabled) {
			account = dao.selectOneIncludeDisabled(targetAccount);			
		} else {
			account = dao.selectOne(targetAccount);	
		}
		
		return account;
	}
	
	public AccountVO selectAccountbyUsername(String username) {
		return selectAccountbyUsername(username, false, false);
	}

	public AccountVO selectByUsernameAndPassword(String username, String password) {
		AccountVO account = null;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		AccountDAO dao = new AccountDAO(factory);
		
		AccountVO targetAccount = new AccountVO();
		targetAccount.setAusername(username);
		targetAccount.setApassword(password);

		account = dao.selectOneIncludeDisabled(targetAccount);
		
		return account;
	}

	public AccountVO selectAccountbyNO(int no) {
		AccountVO account = null;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		AccountDAO dao = new AccountDAO(factory);
		
		AccountVO targetAccount = new AccountVO();
		targetAccount.setAno(no);

		account = dao.selectOne(targetAccount);
		
		return account;
	}

	public ObservableList<AccountVO> selectAccountsByName(String name, boolean isStaff) {
		List<AccountVO> list = null;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		AccountDAO dao = new AccountDAO(factory);
		
		AccountVO targetAccount = new AccountVO();
		targetAccount.setAname(name);
		
		if (isStaff) {
			targetAccount.setAlevel(1);			
		}
		
		list = dao.selectList(targetAccount);
		
		ObservableList<AccountVO> obList = FXCollections.observableArrayList();
		for (AccountVO account : list) {
			obList.add(account);
		}
		
		return obList;
	}

	public ObservableList<AccountVO> selectAccountsByName(String name) {
		return selectAccountsByName(name, false);
	}

	public boolean insertNewAccount(AccountVO newAccount) {
		boolean result = false;
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		AccountDAOOld dao = new AccountDAOOld(con);
		int affectedRows = dao.insert(newAccount);
		
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

	public boolean updateAccount(AccountVO account) {
		boolean result = false;
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		AccountDAOOld dao = new AccountDAOOld(con);
		int affectedRows = dao.update(account);
		
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

	/*
	 * @return 성공시 1, 대출도서가 있어서 불가시 11
	 **/
	public int updateAccountToEmptyRow(int ano, String username) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		LoanDAO daoL = new LoanDAO(con);
		ObservableList<LoanVO> list = daoL.selectByAno(ano);

		for (LoanVO row : list) {
			if (row.getLno() == 0) {
				continue;
			}
			
			if(row.getLreturnedAt() == null) {
				return 11;
			}
			
			if(row.getLreturnedAt().equals("")) {
				return 11;
			}
		}
		
		AccountDAOOld daoA = new AccountDAOOld(con);
		AccountVO account = new AccountVO(ano, "", "", "1970-01-01", "1970-01-01",
											"", "", "", -1, -1, username, "");
		account.setAdisabled(true);
		account.setAdisabledAt(DateHelper.todayDateStr());
		
		int affectedRows = daoA.update(account);
		
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

}
