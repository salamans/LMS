/**
 * 
 */
package com.gcit.ms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.BookLoan;


public class LoanDAO extends BaseDAO<BookLoan>{
	
	public LoanDAO(Connection conn) {
		super(conn);
	}

	public void addBookLoan(BookLoan bookLoan) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_book_loans VALUES (?,?,?,CURDATE(),CURDATE()+7,null)", new Object[] {bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo()});
	}
	
	public Integer addBookLoanGetPK(BookLoan bookLoan) throws SQLException, ClassNotFoundException {
		return saveWithID("INSERT INTO tbl_book_loans VALUES (?,?,?,CURDATE(),CURDATE()+7,null)", new Object[] {bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo()});
	}

	public void returnBookLoan(BookLoan bookLoan) throws SQLException, ClassNotFoundException  {
		save("UPDATE tbl_book_loans SET dateIn = CURDATE() WHERE branchId=? AND bookId=? AND cardNo=? ", new Object[] {bookLoan.getBranchId(), bookLoan.getBookId(), bookLoan.getCardNo()});
	}

	public void deleteBookLoan(BookLoan bookLoan) throws SQLException, ClassNotFoundException  {
		save("DELETE FROM tbl_book_loans WHERE branchId=? AND bookId=? AND cardNo=?", new Object[] {bookLoan.getBranchId(), bookLoan.getBookId(), bookLoan.getCardNo()});
	}
	
	public List<BookLoan> readAllBookLoans(Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return read("SELECT * FROM tbl_book_loans", null);
	}
	
	
	public Integer getLoansCount(String bookLoanName) throws ClassNotFoundException, SQLException{
		return getCount("SELECT COUNT(*) COUNT FROM tbl_book_loans", null);
	}
	
	public BookLoan readBookLoanByPk(Integer branchId, Integer bookId, Integer cardNo) throws ClassNotFoundException, SQLException{
		List<BookLoan> bookLoans =  read("SELECT * FROM tbl_book_loans WHERE bookLoanId = ?", new Object[]{branchId, bookId, cardNo});
		if(bookLoans!=null){
			return bookLoans.get(0);
		}
		return null;
	}
	
	public List<BookLoan> readBookLoansByCardNo(Integer cardNo) throws ClassNotFoundException, SQLException{
		List<BookLoan> bookLoans =  read("SELECT * FROM tbl_book_loans WHERE cardNo = ? AND ISNULL(dateIn)", new Object[]{cardNo});
		if(bookLoans!=null){
			return bookLoans;
		}
		return null;
	}
	
	public List<BookLoan> extractData(ResultSet rs) throws SQLException, ClassNotFoundException{
		List<BookLoan> bookLoans = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while(rs.next()){
			BookLoan bookLoan = new BookLoan();
			bookLoan.setBranchId(rs.getInt("branchId"));
			bookLoan.setBookId(rs.getInt("bookId"));
			bookLoan.setCardNo(rs.getInt("cardNo"));
			Timestamp dateIn = rs.getTimestamp("dateIn");
			bookLoan.setDateIn(dateIn == null ? null : dateIn.toLocalDateTime());
			Timestamp dateOut = rs.getTimestamp("dateOut");
			bookLoan.setDateOut(dateOut == null ? null : dateOut.toLocalDateTime());
			Timestamp dueDate = rs.getTimestamp("dueDate");
			bookLoan.setDueDate(dueDate == null ? null : dueDate.toLocalDateTime());
			bookLoan.setBook(bdao.readFirstLevel("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_loans "
					+ "WHERE branchId=? AND bookId=? AND cardNo=?)", new Object[] {bookLoan.getBranchId(), bookLoan.getBookId(), bookLoan.getCardNo()}).get(0));
			bookLoans.add(bookLoan);
		}
		return bookLoans;
	}
	
	public List<BookLoan> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException{
		List<BookLoan> bookLoans = new ArrayList<>();
		while(rs.next()){
			BookLoan bookLoan = new BookLoan();
			bookLoan.setBranchId(rs.getInt("branchId"));
			bookLoan.setBookId(rs.getInt("bookId"));
			bookLoan.setCardNo(rs.getInt("cardNo"));
			LocalDateTime dateIn = rs.getTimestamp("dateIn").toLocalDateTime();
			bookLoan.setDateIn(dateIn == null ? null : dateIn);
			LocalDateTime dateOut = rs.getTimestamp("dateOut").toLocalDateTime();
			bookLoan.setDateOut(dateOut == null ? null : dateOut);
			LocalDateTime dueDate = rs.getTimestamp("dueDate").toLocalDateTime();
			bookLoan.setDueDate(dueDate == null ? null : dueDate);
			bookLoans.add(bookLoan);
		}
		return bookLoans;
	}
}