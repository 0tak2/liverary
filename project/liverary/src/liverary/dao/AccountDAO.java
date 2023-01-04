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
			
			if (!rs.isBeforeFirst() ) {    
				return null;
			} else {
				rs.next();
				account = new AccountVO(rs.getInt("ano"), rs.getString("aname"), rs.getString("adepartment"), rs.getString("abirth"), rs.getString("acreatedAt"), rs.getString("aphone"),
						rs.getString("aemail"), rs.getString("aaddr"), rs.getInt("apoint"), rs.getInt("alevel"), rs.getString("ausername"), rs.getString("apassword"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return account;
		
	}
	
	public AccountVO select(int ano) {
		String sql = "SELECT * FROM accountsTBL WHERE ano = ?";
		
		AccountVO account = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ano);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			account = new AccountVO(rs.getInt("ano"), rs.getString("aname"), rs.getString("adepartment"), rs.getString("abirth"), rs.getString("acreatedAt"), rs.getString("aphone"),
									rs.getString("aemail"), rs.getString("aaddr"), rs.getInt("apoint"), rs.getInt("alevel"), rs.getString("ausername"), rs.getString("apassword"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return account;
	}

	public int updatePoint(int ano, int newPoint) {
		int affectedRows = 0;
		try {
			String sql = "UPDATE accountsTBL SET apoint = ? WHERE ano = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, newPoint);
			pstmt.setInt(2, ano);
			
			affectedRows = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return affectedRows;
	}

	public int insert(AccountVO account) {
		int affectedRows = 0;
		try {
			String sql = "INSERT INTO `accountsTBL`(aname, adepartment, abirth, acreatedAt, "
					+ "aphone, aemail, aaddr, apoint, alevel, ausername, apassword) "
					+ "VALUES (?, null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, account.getAname());
			pstmt.setString(2, account.getAbirth());
			pstmt.setString(3, account.getAcreatedAt());
			pstmt.setString(4, account.getAphone());
			pstmt.setString(5, account.getAemail());
			pstmt.setString(6, account.getAaddr());
			pstmt.setInt(7, account.getApoint());
			pstmt.setInt(8, account.getAlevel());
			pstmt.setString(9, account.getAusername());
			pstmt.setString(10, account.getApassword());
			
			affectedRows = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return affectedRows;
	}

	public int update(AccountVO account) {
		int affectedRows = 0;
		try {
			String sql = "UPDATE `accountsTBL` "
					+ "SET aname = ?, adepartment = ?, aphone = ?, aemail = ?, aaddr = ?, apassword = ?"
					+ "WHERE ano = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, account.getAname());
			pstmt.setString(2, account.getAdepartment());
			pstmt.setString(3, account.getAphone());
			pstmt.setString(4, account.getAemail());
			pstmt.setString(5, account.getAaddr());
			pstmt.setString(6, account.getApassword());
			pstmt.setInt(7, account.getAno());
			
			affectedRows = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return affectedRows;
	}

}
