<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList" %>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html"%>
<%
AdminService adminService = new AdminService();
List<Author> authors = new ArrayList<Author>();
List<Publisher> publishers = new ArrayList<Publisher>();
List<Genre> genres = new ArrayList<Genre>();
int totalAuthors = adminService.getAuthorsCount();
int pageSize = (int) Math.ceil(totalAuthors / 10 + 1);
if (request.getAttribute("authors") != null) {
	authors = (List<Author>) request.getAttribute("authors");
} else {
	for(int i = 1; i < pageSize; i++){
		authors.addAll(adminService.getAuthors(null, i));
	}
}
int totalPublishers = adminService.getPublishersCount();
pageSize = (int) Math.ceil(totalPublishers / 10 + 1);
if (request.getAttribute("publishers") != null) {
	publishers = (List<Publisher>) request.getAttribute("publishers");
} else {
	for(int i = 1; i < pageSize; i++){
		publishers.addAll(adminService.getPublishers(null, i));
	}
}
int totalGenres = adminService.getGenreCount();
pageSize = (int) Math.ceil(totalGenres / 10 + 1);
if (request.getAttribute("genres") != null) {
	genres = (List<Genre>) request.getAttribute("genres");
} else {
	for(int i = 1; i < pageSize; i++){
		genres.addAll(adminService.getGenres(null, i));
	}
}
%>
<div class="jumbotron">
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Hello Admin: Enter details to add a new book</h2>
	<form action="addBook" method="post">
		Book Name: <input type="text" name="bookName" maxlength="45"><br /> <br/>
		Select Authors to Associate to Book: <br/>
		<select multiple="multiple" size="5" name="authorIds">
			<%for(Author a: authors){ %>
				<option value="<%=a.getAuthorId()%>"><%=a.getAuthorName()%></option>
			<%} %>
		</select><br/><br/>Select Publisher <br/>
		<select name="publisherId" size="5">
			<%for(Publisher p: publishers){ %>
				<option value="<%=p.getPublisherId()%>"><%=p.getPublisherName()%></option>
			<%} %>
		</select><br/><br/>Select Genre <br/>
		<select multiple="multiple" size="5" name="genreIds">
			<%for(Genre g: genres){ %>
				<option value="<%=g.getGenreId()%>"><%=g.getGenreName()%></option>
			<%} %>
		</select><br/>
		<button type="submit">Save</button>
	</form>
</div>