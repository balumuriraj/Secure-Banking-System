<%@ include file="header.jsp"%>
<h3 style="text-align:center;">Find Internal Users by ID</h3>

<div style="width:75%; margin:0 auto;">
<table id="tfhover" class="tftable" border="1">
	<thead>
		<tr>
			<th>User ID</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Phone</th>
			<th>Email</th>
			<sec:authorize access="hasAnyRole('PRESIDENT', 'VICEPRESIDENT')">
			<th>Action</th>
			</sec:authorize>
		</tr>
	</thead>
	<tbody>
			<tr>
				<td>${userbyid.userid}</td>
				<td>${userbyid.firstname}</td>
				<td>${userbyid.lastname}</td>
				<td>${userbyid.telephone}</td>
				<td>${userbyid.email}</td>
				<sec:authorize access="hasAnyRole('PRESIDENT', 'VICEPRESIDENT')">
				<td><form:form method="post" modelAttribute="userbyid" action="requestpii"><form:hidden path="userid" /><input type="submit" value="Request PII Authorization" class="myButton" /></form:form><br>
				</sec:authorize>
			</tr>
	</tbody>
</table>
</div>
<p style="text-align:center;">Go Back to <a class="myButton" href="/SecureOnlineBanking/externalUserManagement">External User Management</a></p>
<%@ include file="footer.jsp"%>