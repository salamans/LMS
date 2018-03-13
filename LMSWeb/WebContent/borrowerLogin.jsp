<%@include file="include.html"%>
<%
	String mssg = request.getAttribute("message") != null ? request.getAttribute("message").toString() : null;
	if(mssg != null){%>
		<div class="alert alert-danger" role="alert">
        <strong><%=mssg %></strong>
      </div><% 
	}
%>
<form action="borrowerLogin" method="post">
		Borrower card number: <input type="text" name="cardNo" maxlength="45"><br />
		<button type="submit">Login</button>
</form>