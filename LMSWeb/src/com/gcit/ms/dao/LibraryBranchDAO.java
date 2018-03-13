package com.gcit.ms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO<LibraryBranch>{
	
	public LibraryBranchDAO(Connection conn) {
		super(conn);
	}

	public void addLibraryBranch(LibraryBranch branch) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES (?,?)", 
				new Object[] {branch.getBranchName(), branch.getBranchAddress()});
	}
	
	public Integer addLibraryBranchGetPK(LibraryBranch branch) throws SQLException, ClassNotFoundException {
		return saveWithID("INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES (?,?)", 
				new Object[] {branch.getBranchName(), branch.getBranchAddress()});
	}

	public void updateLibraryBranch(LibraryBranch branch) throws SQLException, ClassNotFoundException  {
		save("UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? WHERE branchId = ? ", 
				new Object[] {branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId()});
	}

	public void deleteLibraryBranch(LibraryBranch branch) throws SQLException, ClassNotFoundException  {
		save("DELETE FROM tbl_library_branch WHERE branchId = ?", new Object[] {branch.getBranchId()});
	}
	
	public List<LibraryBranch> readAllLibraryBranches(Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return read("SELECT * FROM tbl_library_branch", null);
	}
	
	public List<LibraryBranch> readAllBranchesWithBooks(Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return read("SELECT * FROM tbl_library_branch br, tbl_book_copies cp WHERE br.branchId=cp.branchId AND noOfCopies > 0 ", null);
	}
	
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException, ClassNotFoundException{
		BookDAO bdao = new BookDAO(conn);
		List<LibraryBranch> branchs = new ArrayList<>();
		while(rs.next()){
			LibraryBranch branch = new LibraryBranch();
			branch.setBranchId(rs.getInt("branchId"));
			branch.setBranchName(rs.getString("branchName"));
			branch.setBranchAddress(rs.getString("branchAddress"));
			branch.setBooks(bdao.readFirstLevel("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_copies "
					+ "WHERE branchId = ? AND noOfCopies > 0)", 
					new Object[]{branch.getBranchId()}));
			branchs.add(branch);
		}
		return branchs;
	}
	
	public List<LibraryBranch> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException{
		List<LibraryBranch> branchs = new ArrayList<>();
		while(rs.next()){
			LibraryBranch branch = new LibraryBranch();
			branch.setBranchId(rs.getInt("branchId"));
			branch.setBranchName(rs.getString("branchName"));
			branch.setBranchAddress(rs.getString("branchAddress"));
			branchs.add(branch);
		}
		return branchs;
	}

	public List<LibraryBranch> readLibraryBranchByName(String libName) throws ClassNotFoundException, SQLException {
		libName = "%"+libName+"%";
		return read("SELECT * FROM tbl_library_branch WHERE branchName like ?", new Object[]{libName});
	}

	public Integer getLibraryBranchCount() throws ClassNotFoundException, SQLException {
		return getCount("SELECT COUNT(*) COUNT FROM tbl_library_branch", null);
	}
	
	public Integer getBranchWithBooksCount() throws ClassNotFoundException, SQLException {
		return getCount("SELECT COUNT(*) COUNT FROM tbl_library_branch br, tbl_book_copies cp WHERE br.branchId=cp.branchId AND noOfCopies > 0", null);
	}

	public LibraryBranch readLibraryBranchByPK(Integer libId) throws ClassNotFoundException, SQLException {
		List<LibraryBranch> libs =  read("SELECT * FROM tbl_library_branch WHERE branchId = ?", new Object[]{libId});
		if(libs!=null){
			return libs.get(0);
		}
		return null;
	}

	public List<LibraryBranch> readBranchByName(String branchName) throws ClassNotFoundException, SQLException{
		branchName = "%"+branchName+"%";
		return read("SELECT * FROM tbl_library_branch WHERE branchName like ?", new Object[]{branchName});
	}

	public void decrementCopies(Integer branchId, Integer bookId) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_book_copies SET noOfCopies = noOfCopies - 1 WHERE branchId = ? AND bookId= ?", new Object[]{branchId, bookId});
		
	}

	public void incrementCopies(Integer branchId, Integer bookId) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_book_copies SET noOfCopies = noOfCopies + 1 WHERE branchId = ? AND bookId= ?", new Object[]{branchId, bookId});
		
	}
}