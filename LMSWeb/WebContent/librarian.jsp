<%@include file="include.html"%>
<div class="jumbotron">
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Hello Librarian: Pick a Service</h2>
	<div class="row">
		<div class="col-md-2">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">View/edit Library Branches</h3>
				</div>
				<div class="panel-body">
					<button type="button" class="btn btn-info"
						onclick="location.href='viewBranches.jsp';">Select Action</button>
				</div>
			</div>
		</div>
		<div class="col-md-2">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Edit copies in branch</h3>
				</div>
				<div class="panel-body">
					<button type="button" class="btn btn-info"
						onclick="location.href='changecopies.jsp';">Select Action</button>
				</div>
			</div>
		</div>
	</div>
</div>
