<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html"%>
<%
	AdminService adminService = new AdminService();
	List<Book> books = new ArrayList<>();
	if (request.getAttribute("books") != null) {
		books = (List<Book>) request.getAttribute("books");
	} else {
		books = adminService.getBooks(null, 1);
	}
	Integer totalBooks = adminService.getBooksCount();
	int pageSize = (int) Math.ceil(totalBooks / 10.0 );
%>
<script>
	function searchBooks(){
		$.ajax({
			  method: "POST",
			  url: "searchBooks",
			  data: { "searchString": $('#searchString').val() 
				}
		}).done(function( data ) {
			$('#booksTable').html(data);
		});
	}

</script>

<div class="jumbotron">
	<h2>List of Books in LMS</h2>
	${message}
	<div class="input-group">
			<input type="text" class="form-control" placeholder="Search Books" aria-describedby="basic-addon1" id="searchString" oninput="searchBooks()">
	</div>
	<nav aria-label="Page navigation">
		<ul class="pagination">
			<li><a href="#" aria-label="Previous"> <span
					aria-hidden="true">&laquo;</span>
			</a></li>
			<%
				for (int i = 1; i <= pageSize; i++) {
			%>
			<li><a href="pageBooks?pageNo=<%=i%>"><%=i%></a></li>
			<%
				}
			%>
			<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</ul>
	</nav>
	<table class="table table-striped" id="booksTable">
		<tr>
			<th>Book ID</th>
			<th>Title</th>
			<th>Authors</th>
			<th>Publisher</th>
			<th>Genres</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
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
			<td>
				<%
					for (Author a : b.getAuthors()) {
							out.println(a.getAuthorName() + " | ");
						}
				%>
			</td>
			<td><%=b.getPublisher().getPublisherName() %></td>
			<td>
				<%
					for (Genre g : b.getGenres()) {
							out.println(g.getGenreName() + " | ");
						}
				%>
			</td>
			<td><button class="btn btn-warning"
					href="editbook.jsp?bookId=<%=b.getBookId()%>"
					data-toggle="modal" data-target="#myEditBookModal">Edit</button></td>
			<td><button class="btn btn-danger"
					onclick="javascript:location.href='deleteBook?bookId=<%=b.getBookId()%>'">Delete</button></td>
		</tr>
		<%
			}
		%>
	</table>
</div>

<!-- Large modal -->

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" id="myEditBookModal">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content"></div>
	</div>
</div>

<script>
	$(document).ready(function() {

		$('.modal').on('hidden.bs.modal', function(e) {
			$(this).removeData();
		});
	});
</script>