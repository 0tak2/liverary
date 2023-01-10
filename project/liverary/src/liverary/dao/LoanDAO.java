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

	public List<LoanVO> selectRecent(LoanVO target) {
		List<LoanVO> list = null;
		
		try {
			list = session.selectList("liverary.loan.selectRecent", target);
		} catch (Exception e) {
			throw e;
		}
		
		return list;
	}
	
	public LoanVO selectOne(LoanOptionVO target) {
		LoanVO row = null;
		
		try {
			row = session.selectOne("liverary.loan.select", target);			
		} catch (Exception e) {
			throw e;
		}
		
		return row;
	}
	
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

	public List<LoanByAccountVO> selectWithAccount(LoanByAccountVO target) {
		List<LoanByAccountVO> list = null;
		
		try {
			list = session.selectList("liverary.loan.selectWithAccount", target);			
		} catch (Exception e) {
			throw e;
		}
		
		return list;
	}

	public int insert(LoanVO row) {
		int affectedRows = 0;
		
		try {
			affectedRows = session.insert("liverary.loan.insert", row);
		} catch (Exception e) {
			throw e;
		}
		
		return affectedRows;
	}

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
