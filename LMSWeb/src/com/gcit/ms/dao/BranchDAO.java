package com.gcit.ms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.LibraryBranch;

public class BranchDAO extends BaseDAO<LibraryBranch>{
	
	public BranchDAO(Connection conn) {
		super(conn);
	}

	public void addBranch(LibraryBranch branch) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_library_branch (name, address) VALUES (?,?)", new Object[] {branch.getBranchName(), 
				branch.getBranchAddress()});
	}
	
	public Integer addBranchGetPK(LibraryBranch branch) throws SQLException, ClassNotFoundException {
		return saveWithID("INSERT INTO tbl_library_branch (name, address) VALUES (?,?)", new Object[] {branch.getBranchName(), 
				branch.getBranchAddress()});
	}

	public void updateBranch(LibraryBranch branch) throws SQLException, ClassNotFoundException  {
		save("UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? WHERE branchId = ? ", new Object[] {branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId()});
	}

	public void deleteBranch(LibraryBranch branch) throws SQLException, ClassNotFoundException  {
		save("DELETE FROM tbl_library_branch WHERE cardNo = ?", new Object[] {branch.getBranchId()});
	}
	
	public List<LibraryBranch> readAllBranchs(Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return read("SELECT * FROM tbl_library_branch", null);
	}
	
	public List<LibraryBranch> readBranchsByName(String branchName) throws ClassNotFoundException, SQLException{
		branchName = "%"+branchName+"%";
		return read("SELECT * FROM tbl_library_branch WHERE name like ?", new Object[]{branchName});
	}
	
	public void addBookCopies(Integer bookId, Integer branchId, Integer numCopies) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_book_loans (?, ?, ?) ON DUPLICATE KEY UPDATE noOfCopies = ?", 
				new Object[]{bookId, branchId, numCopies, numCopies});
	}
	
	public Integer getBranchsCount(String branchName) throws ClassNotFoundException, SQLException{
		return getCount("SELECT COUNT(*) COUNT FROM tbl_library_branch", null);
	}
	
	public LibraryBranch readBranchByPk(Integer branchId) throws ClassNotFoundException, SQLException{
		List<LibraryBranch> branchs =  read("SELECT * FROM tbl_library_branch WHERE branchId = ?", new Object[]{branchId});
		if(branchs!=null){
			return branchs.get(0);
		}
		return null;
	}
	
	
	
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException, ClassNotFoundException{
		List<LibraryBranch> branchs = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while(rs.next()){
			LibraryBranch branch = new LibraryBranch();
			branch.setBranchId(rs.getInt("branchId"));
			branch.setBranchName(rs.getString("branchName"));
			branch.setBranchAddress(rs.getString("branchAddress"));
			branch.setBooks(bdao.readFirstLevel("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_copies WHERE branchId = ?)", 
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
			branch.setBranchName(rs.getString("BranchName"));
			branch.setBranchAddress(rs.getString("branchAddress"));
			branchs.add(branch);
		}
		return branchs;
	}
}