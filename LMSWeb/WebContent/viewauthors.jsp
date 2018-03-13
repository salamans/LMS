<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html"%>
<%
	AdminService adminService = new AdminService();
	List<Author> authors = new ArrayList<>();
	if (request.getAttribute("authors") != null) {
		authors = (List<Author>) request.getAttribute("authors");
	} else {
		authors = adminService.getAuthors(null, 1);
	}
	Integer totalAuthors = adminService.getAuthorsCount();
	int pageSize = (int) Math.ceil(totalAuthors / 10 + 1);
%>
<script>
	function searchAuthors(){
		$.ajax({
			  method: "POST",
			  url: "searchAuthors",
			  data: { "searchString": $('#searchString').val() 
				}
		}).done(function( data ) {
			$('#authorsTable').html(data);
		});
	}

</script>

<div class="jumbotron">
	<h2>List of Authors in LMS</h2>
	${message}
	<div class="input-group">
			<input type="text" class="form-control" placeholder="Search Authors" aria-describedby="basic-addon1" id="searchString" oninput="searchAuthors()">
	</div>
	<nav aria-label="Page navigation">
		<ul class="pagination">
			<li><a href="#" aria-label="Previous"> <span
					aria-hidden="true">&laquo;</span>
			</a></li>
			<%
				for (int i = 1; i <= pageSize; i++) {
			%>
			<li><a href="pageAuthors?pageNo=<%=i%>"><%=i%></a></li>
			<%
				}
			%>
			<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</ul>
	</nav>
	<table class="table table-striped" id="authorsTable">
		<tr>
			<th>Author ID</th>
			<th>Author Name</th>
			<th>Books Written</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		<%
			for (Author a : authors) {
		%>
		<tr>
			<td>
				<%
					out.println(authors.indexOf(a) + 1);
				%>
			</td>
			<td><%=a.getAuthorName()%></td>
			<td>
				<%
					for (Book b : a.getBooks()) {
							out.println(b.getTitle() + " | ");
						}
				%>
			</td>
			<td><button class="btn btn-warning"
					href="editauthor.jsp?authorId=<%=a.getAuthorId()%>"
					data-toggle="modal" data-target="#myEditAuthorModal">Edit</button></td>
			<td><button class="btn btn-danger"
					onclick="javascript:location.href='deleteAuthor?authorId=<%=a.getAuthorId()%>'">Delete</button></td>
		</tr>
		<%
			}
		%>
	</table>
</div>

<!-- Large modal -->

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" id="myEditAuthorModal">
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