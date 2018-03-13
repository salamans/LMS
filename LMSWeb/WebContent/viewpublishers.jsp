<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html"%>
<%
	AdminService adminService = new AdminService();
	List<Publisher> pubs = new ArrayList<>();
	if (request.getAttribute("pubs") != null) {
		pubs = (List<Publisher>) request.getAttribute("pubs");
	} else {
		pubs = adminService.getPublishers(null, 1);
	}
	Integer totalPublishers = adminService.getPublishersCount();
	int pageSize = (int) Math.ceil(totalPublishers / 10 + 1);
%>
<script>
	function searchPubs(){
		$.ajax({
			  method: "POST",
			  url: "searchPubs",
			  data: { "searchString": $('#searchString').val() 
				}
		}).done(function( data ) {
			$('#publishersTable').html(data);
		});
	}

</script>

<div class="jumbotron">
	<h2>List of Publishers in LMS</h2>
	${message}
	<div class="input-group">
			<input type="text" class="form-control" placeholder="Search Publishers" aria-describedby="basic-addon1" id="searchString" oninput="searchPubs()">
	</div>
	<nav aria-label="Page navigation">
		<ul class="pagination">
			<li><a href="#" aria-label="Previous"> <span
					aria-hidden="true">&laquo;</span>
			</a></li>
			<%
				for (int i = 1; i <= pageSize; i++) {
			%>
			<li><a href="pagePublishers?pageNo=<%=i%>"><%=i%></a></li>
			<%
				}
			%>
			<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</ul>
	</nav>
	<table class="table table-striped" id="publishersTable">
		<tr>
			<th>Publisher ID</th>
			<th>Name</th>
			<th>Address</th>
			<th>phone</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		<%
			for (Publisher p : pubs) {
		%>
		<tr>
			<td>
				<%
					out.println(pubs.indexOf(p) + 1);
				%>
			</td>
			<td><%=p.getPublisherName()%></td>
			<td><%=p.getPublisherAddress()%></td>
			<td><%=p.getPublisherPhone()%></td>
			<td><button class="btn btn-warning"
					href="editpublisher.jsp?publisherId=<%=p.getPublisherId()%>"
					data-toggle="modal" data-target="#myEditPublisherModal">Edit</button></td>
			<td><button class="btn btn-danger"
					onclick="javascript:location.href='deletePublisher?publisherId=<%=p.getPublisherId()%>'">Delete</button></td>
		</tr>
		<%
			}
		%>
	</table>
</div>

<!-- Large modal -->

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" id="myEditPublisherModal">
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