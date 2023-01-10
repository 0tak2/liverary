package liverary.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import liverary.vo.AccountVO;

public class AccountDAO {
	
	private SqlSession session;
	
	public AccountDAO() {
	}
	
	public AccountDAO(SqlSession session) {
		super();
		this.session = session;
	}

	public AccountVO selectOneIncludeDisabled(AccountVO targetAccount) {
		AccountVO foundAccount = null;
		
		try {
			foundAccount = session.selectOne("liverary.account.selectIncldeDisabled", targetAccount);			
		} catch (Exception e) {
			throw e;
		}
		
		return foundAccount;
	}

	public AccountVO selectOne(AccountVO targetAccount) {
		AccountVO foundAccount = null;
		
		try {
			foundAccount = session.selectOne("liverary.account.select", targetAccount);			
		} catch (Exception e) {
			throw e;
		}
		
		return foundAccount;
	}
	
	public List<AccountVO> selectList(AccountVO targetAccount) {
		List<AccountVO> list = null;
		
		try {
			list = session.selectList("liverary.account.select", targetAccount);			
		} catch (Exception e) {
			throw e;
		}
		
		return list;
	}
	
	public int insert(AccountVO newAccount) {
		int affectedRows = 0;
		
		try {
			affectedRows = session.insert("liverary.account.insert", newAccount);
		} catch (Exception e) {
			throw e;
		}
		
		return affectedRows;
	}
	
	public int update(AccountVO newAccount) {
		int affectedRows = 0;
		
		try {
			affectedRows = session.insert("liverary.account.update", newAccount);
		} catch (Exception e) {
			throw e;
		}
		
		return affectedRows;
	}
}
