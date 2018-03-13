<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
AdminService adminService = new AdminService();
Author author = adminService.getAuthorsByPK(Integer.parseInt(request.getParameter("authorId")));
%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="myModalLabel">Edit Author</h4>
</div>
<form action="addAuthor" method="post">
	<div class="modal-body">

		Author Name: <input type="text" name="authorName" maxlength="45"
			value="<%=author.getAuthorName()%>"><br /> 
			<input type="hidden" name="authorId"
			value="<%=author.getAuthorId()%>">

	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary">Save changes</button>
	</div>
</form>
