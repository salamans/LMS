<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%
AdminService adminService = new AdminService();
Publisher pub = adminService.getPublishersByPK(Integer.parseInt(request.getParameter("publisherId")));
%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<h4 class="modal-title" id="myModalLabel">Edit Publisher</h4>
</div>
<form action="updatePublisher" method="post">
	<div class="modal-body">

		Publisher Name: <input type="text" name="publisherName" maxlength="45"
			value="<%=pub.getPublisherName()%>"><br /> 
			<input type="hidden" name="publisherId"
			value="<%=pub.getPublisherId()%>">
		Publisher Address: <input type="text" name="publisherAddress" maxlength="45" 
			value="<%=pub.getPublisherAddress()%>"><br />
		Publisher Phone: <input type="text" name="publisherPhone" maxlength="45"
			value="<%=pub.getPublisherPhone()%>"><br />
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="submit" class="btn btn-primary">Save changes</button>
	</div>
</form>
