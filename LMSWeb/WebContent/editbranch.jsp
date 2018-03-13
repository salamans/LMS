<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
AdminService adminService = new AdminService();
LibraryBranch lb = adminService.getBranchByPK(Integer.parseInt(request.getParameter("branchId")));
%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="myModalLabel">Edit Branch</h4>
</div>
<form action="updateBranch" method="post">
	<div class="modal-body">

		Branch Name: <input type="text" name="branchName" maxlength="45"
			value="<%=lb.getBranchName()%>"><br /> 
			<input type="hidden" name="branchId"
			value="<%=lb.getBranchId()%>">
		Branch Address: <input type="text" name="branchAddress" maxlength="45"
			value="<%=lb.getBranchAddress()%>"><br />

	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary">Save changes</button>
	</div>
</form>
