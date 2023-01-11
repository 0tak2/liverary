package liverary.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import liverary.vo.BookVO;

public class BookDAO {
	private SqlSession session;
	
	public BookDAO() {
	}
	
	public BookDAO(SqlSession session) {
		super();
		this.session = session;
	}

	/**
	 * booksTBL에서 조건에 맞는 다수의 로우를 조회한다.
	 * 특정 bisbn과 btitle을 BookVO에 담아 넘기면, 해당 조건에 맞는 로우를 조회한다.
 	 * 이때 조건이 중복되면 오류가 발생한다.
 	 * 
	 * @param target   원하는 조건을 담은 VO
	 * @return List<BookVO> 조회 결과를 List<BookVO>로 반환
	 */
	public List<BookVO> select(BookVO target) {
		List<BookVO> list = null;

		try {
			list = session.selectList("liverary.book.select", target);			
		} catch (Exception e) {
			throw e;
		}
		
		return list;
	}

	/**
	 * booksTBL에서 조건에 맞는 한 개의 로우를 조회한다.
	 * 특정 bisbn과 btitle을 BookVO에 담아 넘기면, 해당 조건에 맞는 로우를 조회한다.
 	 * 이때 조건이 중복되면 오류가 발생한다.
	 * 
	 * @param target BookVO 원하는 조건을 담은 VO
	 * @return BookVO 조회 결과를 BookVO로 반환
	 */
	public BookVO selectOne(BookVO target) {
		BookVO book = null;

		try {
			book = session.selectOne("liverary.book.select", target);			
		} catch (Exception e) {
			throw e;
		}
		
		return book;
	}

	/**
	 * booksTBL에 새로운 값을 추가한다.
	 * 
	 * @param book BookVO 원하는 값을 모두 담은 BookVO. bisbn은 기존 데이터와 중복되어서는 안된다.
	 * @return int 영향을 받은 행의 개수. 1 이외의 값은 실패.
	 */
	public int insert(BookVO book) {
		int affectedRows = 0;
		
		try {
			affectedRows = session.insert("liverary.book.insert", book);
		} catch (Exception e) {
			throw e;
		}
		
		return affectedRows;
	}

	/**
	 * booksTBL에서 bsibn 컬럼이 같은 로우의 값을 수정한다.
	 * 
	 * @param book BookVO 원하는 값을 모두 담은 BookVO. bisbn은 지정해도 변경되지 않는다.
	 * @return int 영향을 받은 행의 개수. 1 이외의 값은 실패.
	 */
	public int update(BookVO book) {
		int affectedRows = 0;
		
		try {
			affectedRows = session.update("liverary.book.update", book);
		} catch (Exception e) {
			throw e;
		}
		
		return affectedRows;
	}

	/**
	 * booksTBL에서 특정 bisbn에 해당하는 로우를 삭제한다.
	 * 이때 loanRecordsTBL에 FK로 해당 bisbn이 걸려있는 경우 제약조건에 의해
	 * 삭제되지 않는다.
	 * 
	 * @param isbn String 삭제를 원하는 로우의 bisbn
	 * @return int 영향을 받은 행의 개수. 1 이외의 값은 실패.
	 */
	public int delete(String isbn) {
		int affectedRows = 0;
		
		try {
			affectedRows = session.update("liverary.book.delete", isbn);
		} catch (Exception e) {
			throw e;
		}
		
		return affectedRows;
	}
}
