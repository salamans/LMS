<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@include file="include.html"%>
<% 
	Integer branchId = Integer.valueOf(request.getParameter("branchId"));
	Integer borrowerId = Integer.valueOf(request.getParameter("borrowerId"));
	BorrowerService bService = new BorrowerService();
	List<Book> books = bService.getBooksAtBranchNotCardNo(branchId, borrowerId);
%>
<div class="jumbotron">
	<h2>Please select a book to checkout: </h2><br/>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>Book Id</th>
				<th>Book Title</th>
				<th>Book Authors</th>
				<th>Select</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (Book b : books) {
			%>
			<tr>
				<td>
					<%
						out.println(books.indexOf(b) + 1);
					%>
				</td>
				<td><%=b.getTitle()%></td>
				<td><% for(Author a : b.getAuthors()){
					out.println(a.getAuthorName() + " | ");
				}
				%></td>
				<td><button class="btn btn-primary"
						onclick="javascript:location.href='checkoutBook?borrowerId=<%=borrowerId%>&branchId=<%=branchId%>&bookId=<%=b.getBookId()%>'">Checkout</button></td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>
</div>