/**
 * 
 */
package com.gcit.ms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;

/**
 * @book ppradhan
 *
 */
public class BookDAO extends BaseDAO<Book>{
	
	public BookDAO(Connection conn) {
		super(conn);
	}

	public void addBook(Book book) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_book (title, pubId) VALUES (?,?)", new Object[] {book.getTitle(), new Integer(15789)});
	}
	
	public Integer addBookGetPK(Book book) throws SQLException, ClassNotFoundException {
		return saveWithID("INSERT INTO tbl_book (title, pubId) VALUES (?,?)", new Object[] {book.getTitle(), new Integer(15789)});
	}

	public void updateBook(Book book) throws SQLException, ClassNotFoundException  {
		save("UPDATE tbl_book SET title = ? WHERE bookId = ? ", new Object[] {book.getTitle(), book.getBookId()});
	}

	public void deleteBook(Book book) throws SQLException, ClassNotFoundException  {
		save("DELETE FROM tbl_book WHERE bookId = ?", new Object[] {book.getBookId()});
	}
	
	public List<Book> readAllBooks(Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return read("SELECT * FROM tbl_book", null);
	}
	
	public List<Book> extractData(ResultSet rs) throws SQLException, ClassNotFoundException{
		AuthorDAO adao = new AuthorDAO(conn);
		PublisherDAO pdao = new PublisherDAO(conn);
		List<Book> books = new ArrayList<>();
		while(rs.next()){
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setAuthors(adao.readFirstLevel("SELECT * FROM tbl_author WHERE authorId IN (SELECT authorId FROM tbl_book_authors WHERE bookId = ?)", new Object[]{book.getBookId()}));
			book.setPublisher(pdao.readFirstLevel("SELECT * FROM tbl_publisher WHERE publisherId IN(SELECT pubId FROM tbl_book WHERE bookId = ?)", new Object[]{(book.getBookId())}).get(0));
			books.add(book);
		}
		return books;
	}
	
	public List<Book> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException{
		List<Book> books = new ArrayList<>();
		while(rs.next()){
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			books.add(book);
		}
		return books;
	}

	public Integer getBooksCount(Object object) throws ClassNotFoundException, SQLException {
		return getCount("SELECT COUNT(*) COUNT FROM tbl_book", null);
	}

	public Book readBookByPK(Integer bookId) throws ClassNotFoundException, SQLException {
		List<Book> books =  read("SELECT * FROM tbl_book WHERE bookId = ?", new Object[]{bookId});
		if(books!=null){
			return books.get(0);
		}
		return null;
	}

	public List<Book> readBookByBranch(Integer branchId) throws ClassNotFoundException, SQLException {
		List<Book> books =  read("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId from tbl_book_copies WHERE branchId = ? AND noOfCopies > 0)", new Object[]{branchId});
		if(books!=null){
			return books;
		}
		return null;
	}

	public List<Book> readBookByBranchNotId(Integer branchId, Integer cardNo) throws ClassNotFoundException, SQLException {
		List<Book> books =  read("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId from tbl_book_copies WHERE branchId = ? AND noOfCopies > 0 "
				+ "AND bookId NOT IN(SELECT bookId FROM tbl_book_loans WHERE cardNo = ?)) ", new Object[]{branchId, cardNo});
		if(books!=null){
			return books;
		}
		return null;
	}
}
