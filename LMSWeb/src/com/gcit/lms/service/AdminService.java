/**
 * 
 */
package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopy;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;
import com.gcit.ms.dao.AuthorDAO;
import com.gcit.ms.dao.BookDAO;
import com.gcit.ms.dao.BranchDAO;
import com.gcit.ms.dao.CopiesDAO;
import com.gcit.ms.dao.LibraryBranchDAO;
import com.gcit.ms.dao.PublisherDAO;

/**
 * @author ppradhan
 *
 */
public class AdminService {
	
	public ConnectionUtil connUtil = new ConnectionUtil();
	
	public void saveAuthor(Author author) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			if(author.getAuthorId()!=null && author.getAuthorName()!=null){ 
				adao.updateAuthor(author);
			}else if (author.getAuthorId()!=null){
				adao.deleteAuthor(author);
			}else{
				adao.addAuthor(author);
			}
			//add code to save into tbl_book_authors
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	public void saveBook(Book book) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			if(book.getBookId() != null && book.getTitle() != null){
				bdao.updateBook(book);
			}
			else{
				AuthorDAO adao = new AuthorDAO(conn);
				Integer bookId = bdao.addBookGetPK(book);
				for(Author a: book.getAuthors()){
					adao.addBookAuthors(bookId,a.getAuthorId());
				}
			}
			//same fo genre
//			for(Author a: book.getAuthors()){
//				adao.addBookAuthors(bookId,a.getAuthorId());
//			}
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	public void savePublisher(Publisher pub) throws SQLException {
			Connection conn = null;
			try {
				conn = connUtil.getConnection();
				PublisherDAO pdao = new PublisherDAO(conn);
				if(pub.getPublisherId() != null && pub.getPublisherName() != null){
					pdao.updatePublisher(pub);
				}
				else{
					pdao.addPublisher(pub);
				}
				//same fo genre
	//			for(Author a: book.getAuthors()){
	//				adao.addBookAuthors(bookId,a.getAuthorId());
	//			}
				conn.commit();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				conn.rollback();
			} finally{
				if(conn!=null){
					conn.close();
				}
			}
		}
	
	public boolean updateBookCopies(BookCopy copy) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			CopiesDAO cdao = new CopiesDAO(conn);
			cdao.addBookCopy(copy);
			conn.commit();
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return false;
	}

	public void deleteBook(Book book) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			bdao.deleteBook(book);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}

	public List<Author> getAuthors(String authorName, Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			if(authorName!=null){
				return adao.readAuthorsByName(authorName);
			}else{
				return adao.readAllAuthors(pageNo);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public List<Book> getBooks(String bookTitle, Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			return bdao.readAllBooks(pageNo);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public List<Publisher> getPublishers(String pubName, Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			if(pubName!=null){
				return pdao.readPublisherByName(pubName);
			}else{
				return pdao.readAllPublishers(pageNo);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public List<LibraryBranch> getLibraryBranches(String libName, Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			LibraryBranchDAO bdao = new LibraryBranchDAO(conn);
			if(libName!=null){
				return bdao.readBranchByName(libName);
			}else{
				return bdao.readAllLibraryBranches(pageNo);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public List<BookCopy> getCopiesByBranch(Integer branchId) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			CopiesDAO cdao = new CopiesDAO(conn);
			return cdao.readBookLoansByBranch(branchId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Author getAuthorsByPK(Integer authorId) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.readAuthorByPk(authorId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Book getBooksByPK(Integer bookId) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			return bdao.readBookByPK(bookId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public LibraryBranch getBranchByPK(Integer branchId) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BranchDAO bdao = new BranchDAO(conn);
			return bdao.readBranchByPk(branchId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Publisher getPublishersByPK(Integer pubId) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			return pdao.readPublisherByPK(pubId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Integer getAuthorsCount() throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.getAuthorsCount(null);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Integer getBooksCount() throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			return bdao.getBooksCount(null);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Integer getPublishersCount() throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			return pdao.getPublisherCount();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Integer getLibrariesCount() throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			LibraryBranchDAO bdao = new LibraryBranchDAO(conn);
			return bdao.getLibraryBranchCount();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}

	public void deletePublisher(Publisher pub) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.deletePublisher(pub);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}

	public void updateBranch(LibraryBranch lb) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BranchDAO bdao = new BranchDAO(conn);
			bdao.updateBranch(lb);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
}
