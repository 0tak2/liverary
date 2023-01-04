package liverary.service;

import java.sql.Connection;
import java.sql.SQLException;

import liverary.dao.AccountDAO;
import liverary.dao.DBCPConnectionPool;
import liverary.vo.AccountVO;

public class AccountService {

	public AccountVO selectAccountbyUsername(String username) {
		Connection con = null;
		try {
			con = DBCPConnectionPool.getDataSource().getConnection();
			// con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		AccountDAO dao = new AccountDAO(con);
		AccountVO account = dao.select(username);
		
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
		AccountVO account = dao.select(no);
		
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

}
