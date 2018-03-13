<%@include file="include.html"%>
<div class="jumbotron">
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Hello Borrower: Pick a Service</h2>
	<table>
		<tr>
			<td><a href="checkout.jsp?borrowerId=<%=request.getParameter("borrowerId")%>">Check out book</a></td>
		</tr>
		<tr>
			<td><a href="return.jsp?borrowerId=<%=request.getParameter("borrowerId")%>">Return a book</a></td>
		</tr>
	</table>
</div>
