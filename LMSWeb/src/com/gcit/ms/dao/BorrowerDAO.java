/**
 * 
 */
package com.gcit.ms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower>{
	
	public BorrowerDAO(Connection conn) {
		super(conn);
	}

	public void addBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_borrower (name, address, phone) VALUES (?,?,?)", new Object[] {borrower.getName(), 
				borrower.getAddress(), borrower.getPhone()});
	}
	
	public Integer addBorrowerGetPK(Borrower borrower) throws SQLException, ClassNotFoundException {
		return saveWithID("INSERT INTO tbl_borrower (name, address, phone) VALUES (?,?,?)", new Object[] {borrower.getName(), 
				borrower.getAddress(), borrower.getPhone()});
	}

	public void updateBorrower(Borrower borrower) throws SQLException, ClassNotFoundException  {
		save("UPDATE tbl_borrower SET name = ? WHERE cardNo = ? ", new Object[] {borrower.getName(), borrower.getCardNo()});
	}

	public void deleteBorrower(Borrower borrower) throws SQLException, ClassNotFoundException  {
		save("DELETE FROM tbl_borrower WHERE cardNo = ?", new Object[] {borrower.getCardNo()});
	}
	
	public List<Borrower> readAllBorrowers(Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return read("SELECT * FROM tbl_borrower", null);
	}
	
	public List<Borrower> readBorrowersByName(String borrowerName) throws ClassNotFoundException, SQLException{
		borrowerName = "%"+borrowerName+"%";
		return read("SELECT * FROM tbl_borrower WHERE name like ?", new Object[]{borrowerName});
	}
	
	public void addBookLoan(Integer bookId, Integer branchId, Integer borrowerId) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_book_loans (?, ?, ?)", new Object[]{bookId, branchId, borrowerId});
	}
	
	public Integer getBorrowersCount(String borrowerName) throws ClassNotFoundException, SQLException{
		return getCount("SELECT COUNT(*) COUNT FROM tbl_borrower", null);
	}
	
	public Borrower readBorrowerByPk(Integer borrowerId) throws ClassNotFoundException, SQLException{
		List<Borrower> borrowers =  read("SELECT * FROM tbl_borrower WHERE cardNo = ?", new Object[]{borrowerId});
		if(borrowers!=null && borrowers.size() > 0){
			return borrowers.get(0);
		}
		return null;
	}
	
	
	
	public List<Borrower> extractData(ResultSet rs) throws SQLException, ClassNotFoundException{
		List<Borrower> borrowers = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while(rs.next()){
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setAddress(rs.getString("address"));
			borrower.setPhone(rs.getString("phone"));
			borrower.setBooks(bdao.readFirstLevel("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_loans WHERE cardNo = ?)", new Object[]{borrower.getCardNo()}));
			borrowers.add(borrower);
		}
		return borrowers;
	}
	
	public List<Borrower> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException{
		List<Borrower> borrowers = new ArrayList<>();
		while(rs.next()){
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setAddress(rs.getString("address"));
			borrower.setPhone(rs.getString("phone"));
			borrowers.add(borrower);
		}
		return borrowers;
	}
}
