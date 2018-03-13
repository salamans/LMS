package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.ms.dao.AuthorDAO;
import com.gcit.ms.dao.BookDAO;
import com.gcit.ms.dao.BorrowerDAO;
import com.gcit.ms.dao.LibraryBranchDAO;
import com.gcit.ms.dao.LoanDAO;

public class BorrowerService {

	public ConnectionUtil connUtil = new ConnectionUtil();
	
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
	
	public List<LibraryBranch> getBranchesWithBooks(Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			LibraryBranchDAO bdao = new LibraryBranchDAO(conn);
			return bdao.readAllBranchesWithBooks(pageNo);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public List<Book> getBooksAtBranch(Integer branchId) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			return bdao.readBookByBranch(branchId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public List<BookLoan> getBorrowedBooks(Integer cardNo) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			LoanDAO bdao = new LoanDAO(conn);
			return bdao.readBookLoansByCardNo(cardNo);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public List<Book> getBooksAtBranchNotCardNo(Integer branchId, Integer cardNo) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			return bdao.readBookByBranchNotId(branchId, cardNo);
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
	
	public Integer getLibrariesCountWithBooks() throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			LibraryBranchDAO bdao = new LibraryBranchDAO(conn);
			return bdao.getBranchWithBooksCount();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}

	public boolean login(Integer cardNo) throws SQLException {
		Connection conn = null;
		try{
			conn = connUtil.getConnection();
			BorrowerDAO bdao = new BorrowerDAO(conn);
			Borrower b = bdao.readBorrowerByPk(cardNo);
			if(b != null){
				return true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return false;
	}

	public void checkOutBook(Integer branchId, Integer cardNo, Integer bookId) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			LoanDAO bdao = new LoanDAO(conn);
			BookLoan loan = new BookLoan();
			loan.setBranchId(branchId);
			loan.setBookId(bookId);
			loan.setCardNo(cardNo);
			bdao.addBookLoan(loan);
			LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
			ldao.decrementCopies(branchId, bookId);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
	public void returnBook(Integer branchId, Integer bookId, Integer cardNo) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			LoanDAO bdao = new LoanDAO(conn);
			BookLoan loan = new BookLoan();
			loan.setBookId(bookId);
			loan.setCardNo(cardNo);
			loan.setBranchId(branchId);
			bdao.deleteBookLoan(loan);
			LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
			ldao.incrementCopies(branchId, bookId);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
	}

}
