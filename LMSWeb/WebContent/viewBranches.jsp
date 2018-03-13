<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html"%>
<%
	AdminService adminService = new AdminService();
	List<LibraryBranch> branches = new ArrayList<>();
	if (request.getAttribute("authors") != null) {
		branches = (List<LibraryBranch>) request.getAttribute("branches");
	} else {
		branches = adminService.getLibraryBranches(null, 1);
	}
	Integer totalBranches = adminService.getLibrariesCount();
	int pageSize = (int) Math.ceil(totalBranches / 10 + 1);
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
	<h2>List of Library Branches in LMS</h2>
	${message}
	<div class="input-group">
			<input type="text" class="form-control" placeholder="Search Branches" aria-describedby="basic-addon1" id="searchString" oninput="searchAuthors()">
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
	<table class="table table-striped" id="branchesTable">
		<tr>
			<th>Branch ID</th>
			<th>Branch Name</th>
			<th>Branch Address</th>
			<th>Edit</th>
		</tr>
		<%
			for (LibraryBranch b : branches) {
		%>
		<tr>
			<td>
				<%
					out.println(branches.indexOf(b) + 1);
				%>
			</td>
			<td><%= b.getBranchName()%></td>
			<td>
				<%=b.getBranchAddress()%>
			</td>
			<td><button class="btn btn-warning"
					href="editbranch.jsp?branchId=<%=b.getBranchId()%>"
					data-toggle="modal" data-target="#myEditBranchModal">Edit</button></td>
		</tr>
		<%
			}
		%>
	</table>
</div>

<!-- Large modal -->

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" id="myEditBranchModal">
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