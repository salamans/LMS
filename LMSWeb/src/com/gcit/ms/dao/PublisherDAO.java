package com.gcit.ms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO<Publisher>{
	
	public PublisherDAO(Connection conn) {
		super(conn);
	}

	public void addPublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
		save("INSERT INTO tbl_publisher (publisherName, publisherAddress, publisherPhone) VALUES (?,?,?)", 
				new Object[] {publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone()});
	}
	
	public Integer addPublisherGetPK(Publisher publisher) throws SQLException, ClassNotFoundException {
		return saveWithID("INSERT INTO tbl_publisher (publisherName, publisherAddress, publisherPhone) VALUES (?,?,?)", 
				new Object[] {publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone()});
	}

	public void updatePublisher(Publisher publisher) throws SQLException, ClassNotFoundException  {
		save("UPDATE tbl_publisher SET publisherName = ?, publisherAddress = ?, publisherPhone = ? WHERE publisherId = ? ", 
				new Object[] {publisher.getPublisherName(), publisher.getPublisherAddress(), 
						publisher.getPublisherPhone(), publisher.getPublisherId()});
	}

	public void deletePublisher(Publisher publisher) throws SQLException, ClassNotFoundException  {
		save("DELETE FROM tbl_publisher WHERE publisherId = ?", new Object[] {publisher.getPublisherId()});
	}
	
	public List<Publisher> readAllPublishers(Integer pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return read("SELECT * FROM tbl_publisher", null);
	}
	
	public List<Publisher> extractData(ResultSet rs) throws SQLException, ClassNotFoundException{
		BookDAO bdao = new BookDAO(conn);
		List<Publisher> publishers = new ArrayList<>();
		while(rs.next()){
			Publisher publisher = new Publisher();
			publisher.setPublisherId(rs.getInt("publisherId"));
			publisher.setPublisherName(rs.getString("publisherName"));
			publisher.setPublisherAddress(rs.getString("publisherAddress"));
			publisher.setPublisherPhone(rs.getString("publisherPhone"));
			publisher.setBooks(bdao.readFirstLevel("SELECT * FROM tbl_book WHERE pubId = ?", 
					new Object[]{publisher.getPublisherId()}));
			publishers.add(publisher);
		}
		return publishers;
	}
	
	public List<Publisher> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException{
		List<Publisher> publishers = new ArrayList<>();
		while(rs.next()){
			Publisher publisher = new Publisher();
			publisher.setPublisherId(rs.getInt("publisherId"));
			publisher.setPublisherName(rs.getString("publisherName"));
			publisher.setPublisherAddress(rs.getString("publisherAddress"));
			publisher.setPublisherPhone(rs.getString("publisherPhone"));
			publishers.add(publisher);
		}
		return publishers;
	}

	public List<Publisher> readPublisherByName(String pubName) throws ClassNotFoundException, SQLException {
		pubName = "%"+pubName+"%";
		return read("SELECT * FROM tbl_publisher WHERE publisherName like ?", new Object[]{pubName});
	}

	public Integer getPublisherCount() throws ClassNotFoundException, SQLException {
		return getCount("SELECT COUNT(*) COUNT FROM tbl_author", null);
	}

	public Publisher readPublisherByPK(Integer pubId) throws ClassNotFoundException, SQLException {
		List<Publisher> pubs =  read("SELECT * FROM tbl_publisher WHERE publisherId = ?", new Object[]{pubId});
		if(pubs!=null){
			return pubs.get(0);
		}
		return null;
	}
}