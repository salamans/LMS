<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
AdminService adminService = new AdminService();
Book book = adminService.getBooksByPK(Integer.parseInt(request.getParameter("bookId")));
%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="myModalLabel">Edit Book</h4>
</div>
<form action="updateBook" method="post">
	<div class="modal-body">

		Book Name: <input type="text" name="bookName" maxlength="45"
			value="<%=book.getTitle()%>"><br /> 
			<input type="hidden" name="bookId"
			value="<%=book.getBookId()%>">

	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary">Save changes</button>
	</div>
</form>
