package liverary.service;

import java.sql.Connection;
import java.sql.SQLException;

import javafx.collections.ObservableList;
import liverary.dao.AccountDAO;
import liverary.dao.DBCPConnectionPool;
import liverary.dao.LoanDAO;
import liverary.util.DateHelper;
import liverary.vo.AccountVO;
import liverary.vo.LoanVO;

public class AccountService {

	public AccountVO selectAccountbyUsername(String username, boolean includeDisabled) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		AccountDAO dao = new AccountDAO(con);
		AccountVO account = null;
		if (includeDisabled) {
			account = dao.selectByUsernameIncludeDisabled(username); 
		} else {
			account = dao.selectByUsername(username);			
		}
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return account;
	}
	
	public AccountVO selectAccountbyUsername(String username) {
		return selectAccountbyUsername(username, false);
	}

	public AccountVO selectByUsernameAndPassword(String username, String password) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		AccountDAO dao = new AccountDAO(con);
		AccountVO account = dao.selectByUsernameAndPassword(username, password);
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return account;
	}

	public AccountVO selectAccountbyNO(int no) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
			// con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		AccountDAO dao = new AccountDAO(con);
		AccountVO account = dao.selectByNo(no);
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return account;
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
		
		AccountDAO dao = new AccountDAO(con);
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
		
		AccountDAO dao = new AccountDAO(con);
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

	public ObservableList<AccountVO> selectAccountsByName(String name) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
			// con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		AccountDAO dao = new AccountDAO(con);
		ObservableList<AccountVO> list = dao.selectByName(name);
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public AccountVO selectStaffAccountbyUsername(String username) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
			// con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		AccountDAO dao = new AccountDAO(con);
		AccountVO account = dao.selectStaffByUsername(username);
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return account;
	}

	public ObservableList<AccountVO> selectStaffAccountsByName(String name) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
			// con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		AccountDAO dao = new AccountDAO(con);
		ObservableList<AccountVO> list = dao.selectStaffByName(name);
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
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
		
		AccountDAO daoA = new AccountDAO(con);
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
