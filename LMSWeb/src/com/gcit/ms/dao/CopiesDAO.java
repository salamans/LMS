package com.gcit.ms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.BookCopy;

/**
 * @bookLoan ppradhan
 *
 */
public class CopiesDAO extends BaseDAO<BookCopy>{
	
	public CopiesDAO(Connection conn) {
		super(conn);
	}

	public void addBookCopy(BookCopy copy) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_book_copies VALUES (?,?,?) ON DUPLICATE KEY UPDATE noOfCopies = ?", 
				new Object[] {copy.getBookId(), copy.getBranchId(), copy.getNoOfCopies(), copy.getNoOfCopies()});
	}
	
	public List<BookCopy> readAllBookCopies(Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return read("SELECT * FROM tbl_book_copies", null);
	}
	
	public List<BookCopy> readBookLoansByBranch(Integer branchId) throws ClassNotFoundException, SQLException{
		String tempSql = "select bk.bookId, ? as branchId, ifNull(noOfCopies, 0) as noOfCopies from tbl_book bk "
				+ "left join tbl_book_copies cp on bk.bookId = cp.bookId and  branchId = ?";
		List<BookCopy> bookLoans =  read(tempSql, new Object[]{branchId, branchId});
		if(bookLoans!=null){
			return bookLoans;
		}
		return null;
	}
	
	public List<BookCopy> extractData(ResultSet rs) throws SQLException, ClassNotFoundException{
		List<BookCopy> bookCopies = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while(rs.next()){
			BookCopy bookCopy = new BookCopy();
			bookCopy.setBranchId(rs.getInt("branchId"));
			bookCopy.setBookId(rs.getInt("bookId"));
			bookCopy.setNoOfCopies(rs.getInt("noOfCopies"));
			bookCopy.setBook(bdao.readFirstLevel("SELECT * FROM tbl_book WHERE bookId = ?", 
					new Object[] {bookCopy.getBookId()}).get(0));
			bookCopies.add(bookCopy);
		}
		return bookCopies;
	}
	
	public List<BookCopy> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException{
		List<BookCopy> bookCopies = new ArrayList<>();
		while(rs.next()){
			BookCopy bookCopy = new BookCopy();
			bookCopy.setBranchId(rs.getInt("branchId"));
			bookCopy.setBookId(rs.getInt("bookId"));
			bookCopy.setNoOfCopies(rs.getInt("noOfCopies"));
			bookCopies.add(bookCopy);
		}
		return bookCopies;
	}
}