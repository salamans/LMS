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

/**
 * @author ppradhan
 *
 */
public class AuthorDAO extends BaseDAO<Author>{
	
	public AuthorDAO(Connection conn) {
		super(conn);
	}

	public void addAuthor(Author author) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_author (authorName) VALUES (?)", new Object[] {author.getAuthorName()});
	}
	
	public Integer addAuthorGetPK(Author author) throws SQLException, ClassNotFoundException {
		return saveWithID("INSERT INTO tbl_author (authorName) VALUES (?)", new Object[] {author.getAuthorName()});
	}

	public void updateAuthor(Author author) throws SQLException, ClassNotFoundException  {
		save("UPDATE tbl_author SET authorName = ? WHERE authorId = ? ", new Object[] {author.getAuthorName(), author.getAuthorId()});
	}

	public void deleteAuthor(Author author) throws SQLException, ClassNotFoundException  {
		save("DELETE FROM tbl_author WHERE authorId = ?", new Object[] {author.getAuthorId()});
	}
	
	public List<Author> readAllAuthors(Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return read("SELECT * FROM tbl_author", null);
	}
	
	public List<Author> readAuthorsByName(String authorName) throws ClassNotFoundException, SQLException{
		authorName = "%"+authorName+"%";
		return read("SELECT * FROM tbl_author WHERE authorName like ?", new Object[]{authorName});
	}
	
	public void addBookAuthors(Integer bookId, Integer authorId) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_book_authors VALUES (?, ?)", new Object[]{bookId, authorId});
	}
	
	public Integer getAuthorsCount(String authorName) throws ClassNotFoundException, SQLException{
		return getCount("SELECT COUNT(*) COUNT FROM tbl_author", null);
	}
	
	public Author readAuthorByPk(Integer authorId) throws ClassNotFoundException, SQLException{
		List<Author> authors =  read("SELECT * FROM tbl_author WHERE authorId = ?", new Object[]{authorId});
		if(authors!=null){
			return authors.get(0);
		}
		return null;
	}
	
	
	
	public List<Author> extractData(ResultSet rs) throws SQLException, ClassNotFoundException{
		List<Author> authors = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while(rs.next()){
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			author.setBooks(bdao.readFirstLevel("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_authors WHERE authorId = ?)", new Object[]{author.getAuthorId()}));
			authors.add(author);
		}
		return authors;
	}
	
	public List<Author> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException{
		List<Author> authors = new ArrayList<>();
		while(rs.next()){
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			authors.add(author);
		}
		return authors;
	}
}
