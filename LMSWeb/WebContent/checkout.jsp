<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@include file="include.html"%>
<%
	BorrowerService borrowerService = new BorrowerService();
	List<LibraryBranch> libs = new ArrayList<>();
	if (request.getAttribute("books") != null) {
		libs = (List<LibraryBranch>) request.getAttribute("libs");
	} else {
		libs = borrowerService.getBranchesWithBooks(1);
	}
	Integer totalLibraries = borrowerService.getLibrariesCountWithBooks();
	int pageSize = (int) Math.ceil(totalLibraries / 10 + 1);
%>
<div class="jumbotron">
	<h1>Welcome to GCIT Library Management System</h1>
	<h2>Hello Borrower: Pick a library to check out from</h2>
	<div class="row">
        <div class="col-md-6">
          <table class="table table-bordered">
            <thead>
              <tr>
                <th>Library Id</th>
                <th>Library Name</th>
                <th>Library Address</th>
                <th>Select</th>
              </tr>
            </thead>
            <tbody>
            <%
				for (LibraryBranch l : libs) {
			%>
			<tr>
				<td>
					<%
						out.println(libs.indexOf(l) + 1);
					%>
				</td>
				<td><%=l.getBranchName()%></td>
				<td><%=l.getBranchAddress()%></td>
			<td><button class="btn btn-success"
					onclick="javascript:location.href='checkoutbranch.jsp?borrowerId=<%=request.getParameter("borrowerId")%>&branchId=<%=l.getBranchId()%>'">Select</button></td>
			</tr>
			<%
				}
			%>
            </tbody>
          </table>
        </div>
      </div>
</div>