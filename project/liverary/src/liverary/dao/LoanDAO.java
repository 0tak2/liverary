package liverary.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import liverary.vo.LoanByAccountVO;
import liverary.vo.LoanOptionVO;
import liverary.vo.LoanVO;

public class LoanDAO {
	
	private SqlSession session;
	
	public LoanDAO() {
	}
	
	public LoanDAO(SqlSession session) {
		super();
		this.session = session;
	}

	/**
	 * recentLoanRecordByBookVIEW에서 원하는 조건에 맞는 다수의 로우를 조회한다.
	 * 특정 bisbn과 btitle을 LoanVO에 담아 넘기면, 해당 조건에 맞는 로우를 조회한다.
	 * 이때 조건이 중복되면 오류가 발생한다.
	 * 
	 * @param target LoanVO 원하는 조건을 담은 VO
	 * @return List<LoanVO>
	 */
	public List<LoanVO> selectRecent(LoanVO target) {
		List<LoanVO> list = null;
		
		try {
			list = session.selectList("liverary.loan.selectRecent", target);
		} catch (Exception e) {
			throw e;
		}
		
		return list;
	}
	
	/**
	 * loanRecordWithBookInfoVIEW에서 원하는 조건에 해당하는 한 개의 로우를 조회한다.
	 * 특정 ano, bisn, btitle, 혹은 lcreatedAtStart와 lcreatedAtEnd를
	 * LoanOptionVO에 담아 넘기면, 해당 조건에 맞는 로우를 조회한다.
	 * 이때 조건이 중복되면 오류가 발생한다.
	 * 
	 * @param target LoanOptionVO 원하는 조건을 담은 VO (LoanOptionVO)
	 * @return List<LoanVO> 조회 결과를 List<LoanVO>로 반환
	 */
	public LoanVO selectOne(LoanOptionVO target) {
		LoanVO row = null;
		
		try {
			row = session.selectOne("liverary.loan.select", target);			
		} catch (Exception e) {
			throw e;
		}
		
		return row;
	}
	
	/**
	 * loanRecordWithBookInfoVIEW에서 원하는 조건에 해당하는 다수의 로우를 조회한다.
	 * 특정 ano, bisn, btitle, 혹은 lcreatedAtStart와 lcreatedAtEnd를
	 * LoanOptionVO에 담아 넘기면, 해당 조건에 맞는 로우를 조회한다.
	 * 이때 조건이 중복되면 오류가 발생한다.
	 * 
	 * @param target LoanOptionVO 원하는 조건을 담은 VO (LoanOptionVO)
	 * @return List<LoanVO> 조회 결과를 List<LoanVO>로 반환
	 */
	public List<LoanVO> select(LoanOptionVO target) {
		List<LoanVO> list = null;
		
		try {
			list = session.selectList("liverary.loan.select", target);			
		} catch (Exception e) {
			throw e;
		}
		
		for (LoanVO row : list) { // 서비스로 빼야함
			String available_kor = "대출중";
			if (row.isAvailable()) {
				available_kor = "반납완료";
			}
			
			row.setAvailable_kor(available_kor);
		}
		
		return list;
	}
	
	/**
	 * accountsWithLoanRecordsVIEW에서 원하는 조건에 해당하는 다수의 로우를 조회한다.
	 * 조회를 원하는 로우의 ano를 LoanByAccountVO 담아 넘기면, 해당 조건에 맞는 로우를 조회한다.
	 * 
	 * @param target LoanByAccountVO 원하는 조건을 담은 VO
	 * @return List<LoanByAccountVO> 조회 결과를 List<LoanByAccountVO>로 반환
	 */
	public List<LoanByAccountVO> selectWithAccount(LoanByAccountVO target) {
		List<LoanByAccountVO> list = null;
		
		try {
			list = session.selectList("liverary.loan.selectWithAccount", target);			
		} catch (Exception e) {
			throw e;
		}
		
		return list;
	}
	
	/**
	 * loanRecordsTBL에 새로운 값을 추가한다.
	 * 
	 * @param row LoanVO 원하는 값을 모두 담은 LoanVO. 다만 lno는 할당하더라도 반영되지 않는다.
	 * @return int 영향을 받은 행의 개수. 1 이외의 값은 실패.
	 */
	public int insert(LoanVO row) {
		int affectedRows = 0;
		
		try {
			affectedRows = session.insert("liverary.loan.insert", row);
		} catch (Exception e) {
			throw e;
		}
		
		return affectedRows;
	}
	
	/**
	 * loanRecordTBL에서 lno 컬럼이 같은 로우의 값을 수정한다.
	 * 
	 * @param row LoanVO 원하는 값을 모두 담은 LoanVO. 다만 lno는 할당하더라도 반영되지 않는다.
	 * @return int 영향을 받은 행의 개수. 1 이외의 값은 실패.
	 */
	public int update(LoanVO row) {
		int affectedRows = 0;
		
		try {
			affectedRows = session.update("liverary.loan.update", row);
		} catch (Exception e) {
			throw e;
		}
		
		return affectedRows;
	}
}
