package liverary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import liverary.vo.AccountVO;

public class AccountDAO {
	
	private SqlSessionFactory factory;
	
	public AccountDAO() {
	}
	
	public AccountDAO(SqlSessionFactory factory) {
		super();
		this.factory = factory;
	}

	public AccountVO selectOneIncludedDisabled(AccountVO targetAccount) {
		AccountVO foundAccount = null;
		
		SqlSession session = factory.openSession(); // 팩토리 객체로부터 세션을 얻음
		
		try {
			foundAccount = session.selectOne("liverary.account.selectOneIncldeDisabled", targetAccount);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return foundAccount;
	}

	public AccountVO selectOne(AccountVO targetAccount) {
		AccountVO foundAccount = null;
		
		SqlSession session = factory.openSession();
		
		try {
			foundAccount = session.selectOne("liverary.account.selectOne", targetAccount);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return foundAccount;
	}
}
