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

	public List<BookVO> select(BookVO target) {
		List<BookVO> list = null;

		try {
			list = session.selectList("liverary.book.select", target);			
		} catch (Exception e) {
			throw e;
		}
		
		return list;
	}

	public BookVO selectOne(BookVO target) {
		BookVO book = null;

		try {
			book = session.selectOne("liverary.book.select", target);			
		} catch (Exception e) {
			throw e;
		}
		
		return book;
	}

	public int insert(BookVO book) {
		int affectedRows = 0;
		
		try {
			affectedRows = session.insert("liverary.book.insert", book);
		} catch (Exception e) {
			throw e;
		}
		
		return affectedRows;
	}

	public int update(BookVO book) {
		int affectedRows = 0;
		
		try {
			affectedRows = session.update("liverary.book.update", book);
		} catch (Exception e) {
			throw e;
		}
		
		return affectedRows;
	}

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
