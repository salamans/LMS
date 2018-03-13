<%@include file="include.html"%>
<div class="jumbotron">
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Hello Borrower: Pick a Service</h2>
	<div class="row">
		<div class="col-md-2">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Checkout Book</h3>
				</div>
				<div class="panel-body">
					<button type="button" class="btn btn-info"
						onclick="location.href='checkout.jsp?borrowerId=<%=request.getParameter("borrowerId")%>';">Select Action</button>
				</div>
			</div>
		</div>
		<div class="col-md-2">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Return Book</h3>
				</div>
				<div class="panel-body">
					<button type="button" class="btn btn-info"
						onclick="location.href='return.jsp?borrowerId=<%=request.getParameter("borrowerId")%>';">Select Action</button>
				</div>
			</div>
		</div>
	</div>
</div>
