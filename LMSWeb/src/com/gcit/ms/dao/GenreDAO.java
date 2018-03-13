/**
 * 
 */
package com.gcit.ms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Genre;

public class GenreDAO extends BaseDAO<Genre>{
	
	public GenreDAO(Connection conn) {
		super(conn);
	}

	public void addGenre(Genre genre) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_genre (genre_name) VALUES (?)", new Object[] {genre.getGenreName()});
	}
	
	public Integer addGenreGetPK(Genre genre) throws SQLException, ClassNotFoundException {
		return saveWithID("INSERT INTO tbl_genre (genre_name) VALUES (?)", new Object[] {genre.getGenreName()});
	}

	public void updateGenre(Genre genre) throws SQLException, ClassNotFoundException  {
		save("UPDATE tbl_genre SET genre_name = ? WHERE genre_id = ? ", new Object[] {genre.getGenreName(), genre.getGenreId()});
	}

	public void deleteGenre(Genre genre) throws SQLException, ClassNotFoundException  {
		save("DELETE FROM tbl_genre WHERE genre_id = ?", new Object[] {genre.getGenreId()});
	}
	
	public List<Genre> readAllGenres(Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return read("SELECT * FROM tbl_genre", null);
	}
	
	public List<Genre> readGenresByName(String genreName) throws ClassNotFoundException, SQLException{
		genreName = "%"+genreName+"%";
		return read("SELECT * FROM tbl_genre WHERE genre_name like ?", new Object[]{genreName});
	}
	
	public void addBookGenres(Integer bookId, Integer genreId) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_book_genres (?, ?)", new Object[]{genreId, bookId});
	}
	
	public Integer getGenresCount(String genreName) throws ClassNotFoundException, SQLException{
		return getCount("SELECT COUNT(*) COUNT FROM tbl_genre", null);
	}
	
	public Genre readGenreByPK(Integer genreId) throws ClassNotFoundException, SQLException{
		List<Genre> genres =  read("SELECT * FROM tbl_genre WHERE genre_id = ?", new Object[]{genreId});
		if(genres!=null){
			return genres.get(0);
		}
		return null;
	}
	
	
	
	public List<Genre> extractData(ResultSet rs) throws SQLException, ClassNotFoundException{
		List<Genre> genres = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while(rs.next()){
			Genre genre = new Genre();
			genre.setGenreId(rs.getInt("genre_id"));
			genre.setGenreName(rs.getString("genre_name"));
			genre.setBooks(bdao.readFirstLevel("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_genres WHERE genre_id = ?)", new Object[]{genre.getGenreId()}));
			genres.add(genre);
		}
		return genres;
	}
	
	public List<Genre> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException{
		List<Genre> genres = new ArrayList<>();
		while(rs.next()){
			Genre genre = new Genre();
			genre.setGenreId(rs.getInt("genre_id"));
			genre.setGenreName(rs.getString("genre_name"));
			genres.add(genre);
		}
		return genres;
	}
}
