<%@page import="com.gcit.lms.entity.BookCopy"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
AdminService adminService = new AdminService();
Integer bookId = Integer.parseInt(request.getParameter("bookId"));
Integer branchId = Integer.parseInt(request.getParameter("branchId"));
Integer noOfCopies = Integer.parseInt(request.getParameter("noOfCopies"));
%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="myModalLabel">Edit Book copies</h4>
</div>
<form action="addCopies" method="post">
	<div class="modal-body">

		Number of copies: <input type="text" name="noOfCopies" maxlength="45"
			value="<%=noOfCopies %>"><br /> 
			<input type="hidden" name="bookId"
			value="<%=bookId%>">
			<input type="hidden" name="branchId"
			value="<%=branchId%>">

	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary">Save changes</button>
	</div>
</form>