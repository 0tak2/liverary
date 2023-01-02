package liverary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liverary.vo.AccountVO;

public class AccountDAO {
	
	private Connection con;
	
	public AccountDAO() {
	}
	
	public AccountDAO(Connection con) {
		super();
		this.con = con;
	}

	public AccountVO select(String username) {
		String sql = "SELECT * FROM accountsTBL WHERE ausername = ?";
		
		AccountVO account = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			account = new AccountVO(rs.getString("ano"), rs.getString("aname"), rs.getString("adepartment"), rs.getString("abirth"), rs.getString("acreatedAt"), rs.getString("aphone"),
									rs.getString("aemail"), rs.getString("aaddr"), rs.getString("apoint"), rs.getString("alevel"), rs.getString("ausername"), rs.getString("apassword"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return account;
		
	}

}
