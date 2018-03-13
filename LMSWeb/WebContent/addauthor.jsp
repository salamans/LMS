<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html"%>
<%
AdminService adminService = new AdminService();
List<Book> books = adminService.getBooks(null, 1);

%>
<div class="jumbotron">
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Hello Admin: Enter details to add a new Author</h2>
	<form action="addAuthor" method="post">
		Author Name: <input type="text" name="authorName" maxlength="45"><br />
		Select Books to Associate Author: <br/>
		<select multiple="multiple" size="5" name="bookIds">
			<%for(Book b: books){ %>
				<option value="<%=b.getBookId()%>"><%=b.getTitle() %></option>
			<%} %>
		</select><br/>
		<button type="submit">Save</button>
	</form>
</div>