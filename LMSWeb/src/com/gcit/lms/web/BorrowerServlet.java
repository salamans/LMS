package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.BorrowerService;

@WebServlet({ "/borrowerLogin", "/checkoutBook", "/returnBook"})
public class BorrowerServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BorrowerService borrowerService = new BorrowerService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		Integer authorId = null;
		String forwardPath = "/viewauthors.jsp";
		switch (reqUrl) {
		case "/checkoutBook":
			try {
				Integer branchId = Integer.valueOf(request.getParameter("branchId"));
				Integer cardNo = Integer.valueOf(request.getParameter("borrowerId"));
				Integer bookId = Integer.valueOf(request.getParameter("bookId"));
				borrowerService.checkOutBook(branchId, cardNo, bookId);
				forwardPath="/borrower.jsp?borrowerId=" + cardNo;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			break;
		case "/returnBook":
			try {
				Integer branchId = Integer.valueOf(request.getParameter("branchId"));
				Integer cardNo = Integer.valueOf(request.getParameter("borrowerId"));
				Integer bookId = Integer.valueOf(request.getParameter("bookId"));
				borrowerService.returnBook(branchId, bookId, cardNo);
				forwardPath="/borrower.jsp?borrowerId=" + cardNo;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			break;
		default:
			break;
		}
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		Boolean isAJAX = Boolean.FALSE;
		String forwardPath = "/viewauthors.jsp";
		switch (reqUrl) {
		case "/borrowerLogin":
			try {
				Integer cardNo = Integer.valueOf(request.getParameter("cardNo"));
				if(borrowerService.login(cardNo)){
					forwardPath = "borrower.jsp?borrowerId="+cardNo;
				}
				else{
					forwardPath = "borrowerLogin.jsp";
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		if (!isAJAX) {
			RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
			rd.forward(request, response);
		}
	}


}
