package liverary.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import liverary.dao.BookDAO;
import liverary.mybatis.MyBatisConnectionFactory;
import liverary.vo.BookVO;

public class BookService {

	public ObservableList<BookVO> selectBooksByISBN(String isbn) {
		List<BookVO> list = null;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		BookDAO dao = new BookDAO(session);
		BookVO target = new BookVO();
		target.setBisbn(isbn);
		
		try {
			list = dao.select(target);				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		ObservableList<BookVO> obList = FXCollections.observableArrayList();
		for (BookVO row : list) {
			obList.add(row);
		}
		
		return obList;
	}

	public ObservableList<BookVO> selectBooksByKeyword(String keyword) {
		List<BookVO> list = null;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		BookDAO dao = new BookDAO(session);
		BookVO target = new BookVO();
		target.setBtitle(keyword);
		
		try {
			list = dao.select(target);				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		ObservableList<BookVO> obList = FXCollections.observableArrayList();
		for (BookVO row : list) {
			obList.add(row);
		}
		
		return obList;
	}

	public boolean insertNewBook(BookVO book) {
		boolean result = false;
		int affectedRows = 0;

		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		BookDAO dao = new BookDAO(session);
		
		try {
			affectedRows = dao.insert(book);
		} catch (Exception e) {
			session.rollback();
			session.close();
			e.printStackTrace();
			result = false;
		} finally {
			if (affectedRows == 1) {
				session.commit();
				result = true;
			} else {
				session.rollback();
				result = false;
			}
			session.close();
		}
		
		return result;
	}

	public BookVO selectOneBookByISBN(String isbn) {
		BookVO book = null;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		BookDAO dao = new BookDAO(session);
		
		BookVO target = new BookVO();
		target.setBisbn(isbn);
		
		try {
			book = dao.selectOne(target);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return book;
	}

	public boolean updateBook(BookVO book) {
		boolean result = false;
		int affectedRows = 0;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		BookDAO dao = new BookDAO(session);
		
		try {
			affectedRows = dao.update(book);			
		} catch (Exception e) {
			result = false;
			session.rollback();
			session.close();
			e.printStackTrace();
		} finally {
			if (affectedRows == 1) {
				result = true;
				session.commit();
				session.close();
			} else {
				result = false;
				session.rollback();
				session.close();
			}
		}
		
		return result;
	}

	public boolean deleteBook(String isbn) {
		boolean result = false;
		int affectedRows = 0;
		
		SqlSessionFactory factory = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		BookDAO dao = new BookDAO(session);
		
		try {
			affectedRows = dao.delete(isbn);			
		} catch (Exception e) {
			result = false;
			session.rollback();
			session.close();
			e.printStackTrace();
		} finally {
			if (affectedRows == 1) {
				result = true;
				session.commit();
				session.close();
			} else {
				result = false;
				session.rollback();
				session.close();
			}
		}
		
		return result;
	}

}
