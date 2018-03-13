<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.BookCopy"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html"%>
<%
	AdminService adminService = new AdminService();
	Integer branchId = Integer.valueOf(request.getParameter("branchId"));
	List<BookCopy> copies = new ArrayList<>();
	if (request.getAttribute("books") != null) {
		copies = (List<BookCopy>) request.getAttribute("copies");
	} else {
		copies = adminService.getCopiesByBranch(branchId);
	}
	Integer totalLibraries = adminService.getLibrariesCount();
	int pageSize = (int) Math.ceil(totalLibraries / 10 + 1);
%>
<div class="jumbotron" align="center">
	<h1><span class="label label-default">Book Copy Management</span></h1>
	<br/><br/>
	<div class="row">
        <div class="col-md-6">
          <table class="table table-bordered" >
            <thead>
              <tr>
                <th>Book Id</th>
                <th>Book title</th>
                <th>Number of Copies</th>
                <th>Edit Copies</th>
              </tr>
            </thead>
            <tbody>
            <%
				for (BookCopy b : copies) {
			%>
			<tr>
				<td>
					<%
						out.println(copies.indexOf(b) + 1);
					%>
				</td>
				<td><%=b.getBook().getTitle()%></td>
				<td><%=b.getNoOfCopies()%></td>
			<td><button class="btn btn-primary"
					href="editcopies.jsp?branchId=<%= b.getBranchId() %>&bookId=<%=b.getBookId()%>&noOfCopies=<%=b.getNoOfCopies() %>"
					data-toggle="modal" data-target="#myEditCopiesModal">Edit</button></td>
			</tr>
			<%
				}
			%>
            </tbody>
          </table>
        </div>
      </div>
</div>

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel" id="myEditCopiesModal">
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