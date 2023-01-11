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
	
	/**
	 * accountsTBL에서 한 개의 로우를 조회한다. 이때 탈퇴처리된 계정을 포함한다.
	 * 특정 ausername, aname, ano를 AccountVO에 담아 넘기면, 해당 조건에 맞는 로우를 조회한다.
	 * alevel에 1 이상의 값을 할당하면 관리자 계정을 조회한다.
 	 * 이때 조건이 중복되면 오류가 발생한다.
	 * 
	 * @param targetAccount AccountVO 원하는 조건을 담은 VO
	 * @return AccountVO 조회 결과 AccountVO
	 */
	public AccountVO selectOneIncludeDisabled(AccountVO targetAccount) {
		AccountVO foundAccount = null;
		
		try {
			foundAccount = session.selectOne("liverary.account.selectIncldeDisabled", targetAccount);			
		} catch (Exception e) {
			throw e;
		}
		
		return foundAccount;
	}

	/**
	 * accountsTBL에서 한 개의 로우를 조회한다. 이때 탈퇴처리된 계정을 포함하지 않는다.
	 * 특정 ausername, aname, ano를 AccountVO에 담아 넘기면, 해당 조건에 맞는 로우를 조회한다.
	 * alevel에 1 이상의 값을 할당하면 관리자 계정을 조회한다.
 	 * 이때 조건이 중복되면 오류가 발생한다.
	 * 
	 * @param targetAccount AccountVO 원하는 조건을 담은 VO
	 * @return AccountVO 조회 결과 AccountVO
	 */
	public AccountVO selectOne(AccountVO targetAccount) {
		AccountVO foundAccount = null;
		
		try {
			foundAccount = session.selectOne("liverary.account.select", targetAccount);			
		} catch (Exception e) {
			throw e;
		}
		
		return foundAccount;
	}
	
	/**
	 * accountsTBL에서 조건에 맞는 다수의 로우를 조회한다. 이때 탈퇴처리된 계정을 포함하지 않는다.
	 * 특정 ausername, aname, ano를 AccountVO에 담아 넘기면, 해당 조건에 맞는 로우를 조회한다.
	 * alevel에 1 이상의 값을 할당하면 관리자 계정을 조회한다.
 	 * 이때 조건이 중복되면 오류가 발생한다.
	 * 
	 * @param targetAccount AccountVO 원하는 조건을 담은 VO
	 * @return List<AccountVO> 조회 결과를 List<AccountVO>로 반환
	 */
	public List<AccountVO> selectList(AccountVO targetAccount) {
		List<AccountVO> list = null;
		
		try {
			list = session.selectList("liverary.account.select", targetAccount);			
		} catch (Exception e) {
			throw e;
		}
		
		return list;
	}
	
	/**
	 * accountsTBL에 새로운 값을 추가한다.
	 * 
	 * @param newAccount AccountVO 원하는 값을 모두 담은 AccountVO. 다만 ano는 할당하더라도 반영되지 않는다.
	 * @return int 영향을 받은 행의 개수. 1 이외의 값은 실패.
	 */
	public int insert(AccountVO newAccount) {
		int affectedRows = 0;
		
		try {
			affectedRows = session.insert("liverary.account.insert", newAccount);
		} catch (Exception e) {
			throw e;
		}
		
		return affectedRows;
	}
	
	/**
	 * accountsTBL에서 ano 컬럼이 같은 로우의 값을 수정한다.
	 * 
	 * @param newAccount AccountVO 모든 값을 담은 AccountVO.
	 * @return int 영향을 받은 행의 개수. 1 이외의 값은 실패.
	 */
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
