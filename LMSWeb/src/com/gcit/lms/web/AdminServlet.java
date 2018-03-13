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
import com.gcit.lms.entity.BookCopy;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;
import com.gcit.ms.dao.PublisherDAO;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({ "/addAuthor", "/addBook", "/deleteAuthor", "/deleteBook", "/editAuthor", "/pageAuthors", "/addCopies",
	"/updateBook", "/savePublisher", "/updatePublisher", "/searchAuthors", "/deletePublisher", "/updateBranch" })
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public AdminServlet() {
	}

	public AdminService adminService = new AdminService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		Integer authorId = null;
		String forwardPath = "/viewauthors.jsp";
		switch (reqUrl) {
		case "/editAuthor":
			try {
				request.setAttribute("author",
						adminService.getAuthorsByPK(Integer.parseInt(request.getParameter("authorId"))));
				forwardPath = "editauthor.jsp";
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			break;
		case "/deleteAuthor":
			authorId = Integer.parseInt(request.getParameter("authorId"));
			Author author = new Author();
			author.setAuthorId(authorId);
			try {
				adminService.saveAuthor(author);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			forwardPath = "viewauthors.jsp";
			break;
		case "/deleteBook":
			Integer bookId = Integer.parseInt(request.getParameter("bookId"));
			Book book = new Book();
			book.setBookId(bookId);
			try {
				adminService.deleteBook(book);
			} catch(SQLException e){
				e.printStackTrace();
			}
			forwardPath = "viewbook.jsp";
			break;
		case "/deletePublisher":
			Integer pubId = Integer.parseInt(request.getParameter("publisherId"));
			Publisher pub = new Publisher();
			pub.setPublisherId(pubId);
			try{
				adminService.deletePublisher(pub);
			} catch(SQLException e){
				e.printStackTrace();
			}
			forwardPath = "viewpublishers.jsp";
			break;
		case "/pageAuthors":
			Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
			try {
				request.setAttribute("authors", adminService.getAuthors(null, pageNo));
			} catch (SQLException e) {
				request.setAttribute("authors", null);
				e.printStackTrace();
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
		Integer authorId = null;
		String forwardPath = "/viewauthors.jsp";
		Boolean isAJAX = Boolean.FALSE;
		switch (reqUrl) {
		case "/addAuthor":
			Author author = new Author();
			author.setAuthorName(request.getParameter("authorName"));
			if (request.getParameter("authorId") != null) {
				author.setAuthorId(Integer.parseInt(request.getParameter("authorId")));
			}
			// String[] bookIds = request.getParameterValues("bookIds");
			// for(String s: bookIds){
			// System.out.println(s);
			// }
			try {
				adminService.saveAuthor(author);
				request.setAttribute("message", "Author added successfully");
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("message", "Author add failed");
			}
			request.setAttribute("message", "Author added successfully");
			break;
		case "/addBook":
			try {
				Book book = new Book();
				String[] authorIds = request.getParameterValues("authorIds");
				Integer pubId = Integer.valueOf(request.getParameter("publisherId"));
				book.setTitle(request.getParameter("bookName"));
				List<Author> authors = new ArrayList<Author>();
				for(String s : authorIds){
					authors.add(adminService.getAuthorsByPK(Integer.valueOf(s)));
				}
				book.setAuthors(authors);
				book.setPublisher(adminService.getPublishersByPK(pubId));
				adminService.saveBook(book);
			} catch (Exception e1) {
				e1.printStackTrace();
				request.setAttribute("message", "Book add failed");
			}
			request.setAttribute("message", "Book addition successfull");
			forwardPath = "/viewbook.jsp";
			break;
		case "/addCopies":
			try {
				BookCopy copy = new BookCopy();
				copy.setBookId(Integer.valueOf(request.getParameter("bookId")));
				copy.setBranchId(Integer.valueOf(request.getParameter("branchId")));
				copy.setNoOfCopies(Integer.valueOf(request.getParameter("noOfCopies")));
				adminService.updateBookCopies(copy);
			} catch (Exception e){
				e.printStackTrace();
			}
			forwardPath = "/changecopies.jsp";
			break;
		case "/updateBook":
			try {
				Book book = new Book();
				book.setTitle(request.getParameter("bookName"));
				book.setBookId(Integer.valueOf(request.getParameter("bookId")));
				adminService.saveBook(book);
			} catch (Exception e){
				e.printStackTrace();
			}
			break;
		case "/updatePublisher":
			try {
				Publisher pub = new Publisher();
				pub.setPublisherId(Integer.valueOf(request.getParameter("publisherId")));
				pub.setPublisherName(request.getParameter("publisherName"));
				pub.setPublisherAddress(request.getParameter("publisherAddress"));
				pub.setPublisherPhone(request.getParameter("publisherPhone"));
				adminService.savePublisher(pub);
			} catch (Exception e){
				e.printStackTrace();
			}
			break;
		case "/updateBranch":
			try {
				LibraryBranch lb = new LibraryBranch();
				lb.setBranchId(Integer.valueOf(request.getParameter("branchId")));
				lb.setBranchName(request.getParameter("branchName"));
				lb.setBranchAddress(request.getParameter("branchAddress"));
				adminService.updateBranch(lb);
			} catch (Exception e){
				e.printStackTrace();
			}
			forwardPath = "/viewBranches.jsp";
			break;
		case "/deleteBook":
			try {
				if (request.getParameter("authorId") != null) {
					Book book = new Book();
					book.setBookId(Integer.parseInt(request.getParameter("bookId")));
					adminService.deleteBook(book);
				}
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				request.setAttribute("message", "Book delete failed.");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				request.setAttribute("message", "Book delete failed.");
			}
			request.setAttribute("message", "Book delete successful");
			break;
		case "/savePublisher":
			try {
				Publisher pub = new Publisher();
				pub.setPublisherAddress(request.getParameter("publisherAddress"));
				pub.setPublisherName(request.getParameter("publisherName"));
				pub.setPublisherPhone(request.getParameter("publisherPhone"));
				adminService.savePublisher(pub);
			} catch (SQLException e){
				e.printStackTrace();
			}
		case "/searchAuthors":
			String searchString = request.getParameter("searchString");
			try {
				List<Author> authors = adminService.getAuthors(searchString, 1);
				StringBuffer strBuf = new StringBuffer();
				strBuf.append(
						"<table class='table table-striped'><tr><th>Author ID</th><th>Author Name</th><th>Books Written</th><th>Edit</th><th>Delete</th></tr>");
				for (Author a : authors) {
					strBuf.append("<tr><td>" + authors.indexOf(a) + 1 + "</td><td>" + a.getAuthorName() + "</td><td>");
					for (Book b : a.getBooks()) {
						strBuf.append(b.getTitle() + " | ");
					}
					strBuf.append(
							"</td><td><button class='btn btn-warning' href='editauthor.jsp?authorId=" + a.getAuthorId()
									+ "' data-toggle='modal' data-target='#myEditAuthorModal'>Edit</button></td>");
					strBuf.append(
							"<td><button class='btn btn-danger' onclick='javascript:location.href='deleteAuthor?authorId="
									+ a.getAuthorId() + "''>Delete</button></td></tr>");
				}
				strBuf.append("</table>");
				response.getWriter().write(strBuf.toString());
				isAJAX = Boolean.TRUE;
			} catch (SQLException e) {
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
