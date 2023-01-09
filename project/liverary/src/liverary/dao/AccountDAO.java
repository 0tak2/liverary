package liverary.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import liverary.vo.AccountVO;

public class AccountDAO {
	
	private SqlSessionFactory factory;
	
	public AccountDAO() {
	}
	
	public AccountDAO(SqlSessionFactory factory) {
		super();
		this.factory = factory;
	}

	public AccountVO selectOneIncludeDisabled(AccountVO targetAccount) {
		AccountVO foundAccount = null;
		
		SqlSession session = factory.openSession(); // 팩토리 객체로부터 세션을 얻음
		
		try {
			foundAccount = session.selectOne("liverary.account.selectIncldeDisabled", targetAccount);			
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
			foundAccount = session.selectOne("liverary.account.select", targetAccount);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return foundAccount;
	}
	
	public List<AccountVO> selectList(AccountVO targetAccount) {
		List<AccountVO> list = null;
		
		SqlSession session = factory.openSession();
		
		try {
			list = session.selectList("liverary.account.select", targetAccount);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return list;
	}
}
