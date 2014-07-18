<%@ include file="header.jsp"%>
<script type="text/javascript">
	window.onload = function() {
		var tfrow = document.getElementById('tfhover').rows.length;
		var tbRow = [];
		for (var i = 1; i < tfrow; i++) {
			tbRow[i] = document.getElementById('tfhover').rows[i];
			tbRow[i].onmouseover = function() {
				this.style.backgroundColor = '#ffffff';
			};
			tbRow[i].onmouseout = function() {
				this.style.backgroundColor = '#d4e3e5';
			};
		}
	};
</script>

<style type="text/css">
table.tftable {
	font-size: 15px;
	color: #333333;
	width: 100%;
	border-width: 1px;
	border-color: #729ea5;
	border-collapse: collapse;
}

table.tftable th {
	font-size: 15px;
	background-color: #acc8cc;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #729ea5;
	text-align: left;
}

table.tftable tr {
	background-color: #d4e3e5;
}

table.tftable td {
	font-size: 15px;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #729ea5;
}
</style>

<div
	style="width: 900px; margin: 0 auto; background: #fff; box-shadow: 0 1px 5px #000;">
	<div style="padding: 10px 30px 10px 30px;">
		<%@ include file="navigation.jsp"%>
		<br> <br>

		<h4>
			Logged in as:
			<sec:authentication property="name" />
			<sec:authentication property="authorities" />
		</h4>

		<sec:authorize access="hasAnyRole('EMPLOYEE')">
			<div style="background: #555; color: white; padding: 10px;">
				<b>Credit Card Requests</b>
			</div>
			<br />

			<table id="tfhover" class="tftable" border="1">
				<thead>
					<tr>
						<th>User ID</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Email</th>
						<th>Account No</th>
						<th>Current Balance</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="ccreq" items="${ccreqs}">
						<tr>
							<td>${ccreq.userid}</td>
							<td>${ccreq.firstname}</td>
							<td>${ccreq.lastname}</td>
							<td>${ccreq.email}</td>
							<td>${ccreq.accountNo}</td>
							<td>${ccreq.currentBalance}</td>
							<td><c:set target="${extuser}" property="accountNo"
									value="${ccreq.accountNo}" /> <form:form method="post"
									modelAttribute="extuser"
									action="transactionManagement/approveccrequest">
									<form:hidden path="accountNo" />
									<input type="submit" value="Approve" class="myButton" />
								</form:form> <br> <form:form method="post" modelAttribute="extuser"
									action="transactionManagement/rejectccrequest">
									<form:hidden path="accountNo" />
									<input type="submit" value="Reject" class="myButton" />
								</form:form></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br />
		</sec:authorize>


		<sec:authorize access="hasAnyRole('EMPLOYEE', 'MANAGER')">
			<div style="background: #555; color: white; padding: 10px;">
				<b>Approved Credit Card Requests</b>
			</div>
			<br />

			<table id="tfhover" class="tftable" border="1">
				<thead>
					<tr>
						<th>Request No</th>
						<th>Account No</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="appccreq" items="${appccreqs}">
						<tr>
							<td>${appccreq.requestNo}</td>
							<td>${appccreq.accountNo}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br />


		</sec:authorize>


		<br />
		<hr>
		<div style="text-align: center;">
			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<form:form method="post" modelAttribute="currentuser"
					action="transactionManagement/displayexternalusers">
					<form:hidden path="deptid" />
					<input type="submit" value="Display Users" class="myButton" />
				</form:form>
			</div>

		</div>

	</div>
</div>

<%@ include file="footer.jsp"%>