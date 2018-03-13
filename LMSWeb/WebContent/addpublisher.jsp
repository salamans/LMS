<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html"%>
<%
AdminService adminService = new AdminService();
%>
<div class="jumbotron">
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Hello Admin: Enter details to add a new Author</h2>
	<form action="savePublisher" method="post">
		Publisher Name: <input type="text" name="publisherName" maxlength="45"><br />
		Publisher Address: <input type="text" name="publisherAddress" maxlength="45"><br />
		Publisher Phone: <input type="text" name="publisherPhone" maxlength="45"><br />
		<button type="submit">Save</button>
	</form>
</div>