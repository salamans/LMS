<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.entity.BookLoan"%>
<%@include file="include.html"%>
<% 
	Integer borrowerId = Integer.valueOf(request.getParameter("borrowerId"));
	BorrowerService bService = new BorrowerService();
	List<BookLoan> bookLoans = bService.getBorrowedBooks(borrowerId);
%>
<div class="jumbotron">
	<h2>Please select a book to return: </h2><br/>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>Book Id</th>
				<th>Book Title</th>
				<th>Date Out</th>
				<th>Due Date</th>
				<th>return</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (BookLoan bl : bookLoans) {
			%>
			<tr>
				<td>
					<%
						out.println(bookLoans.indexOf(bl) + 1);
					%>
				</td>
				<td><%=bl.getBook().getTitle()%></td>
				<td><%=bl.getDateOut()%></td>
				<td><%=bl.getDueDate()%></td>
				<td><button class="btn btn-warning"
						onclick="javascript:location.href='returnBook?borrowerId=<%=borrowerId%>&bookId=<%= bl.getBookId() %>&branchId=<%=bl.getBranchId() %>'">Return</button></td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>
</div>