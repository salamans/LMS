/**
 * 
 */
package com.gcit.lms.entity;

import java.io.Serializable;
import java.time.LocalDateTime;


public class BookLoan implements Serializable{
	private static final long serialVersionUID = 9151170513668626160L;
	
	private Integer branchId;
	private Integer bookId;
	private Integer cardNo;
	private LocalDateTime dateOut;
	private LocalDateTime dateIn;
	private LocalDateTime dueDate;
	private Book book;
	
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public Integer getCardNo() {
		return cardNo;
	}
	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}
	public LocalDateTime getDateOut() {
		return dateOut;
	}
	public void setDateOut(LocalDateTime dateOut) {
		this.dateOut = dateOut;
	}
	public LocalDateTime getDateIn() {
		return dateIn;
	}
	public void setDateIn(LocalDateTime dateIn) {
		this.dateIn = dateIn;
	}
	public LocalDateTime getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}

}