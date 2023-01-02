package liverary.service;

import java.sql.Connection;
import java.sql.SQLException;

import liverary.dao.AccountDAO;
import liverary.dao.DBCPConnectionPool;
import liverary.vo.AccountVO;

public class AccountService {

	public AccountVO getAccountbyUsername(String username) {
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

}
