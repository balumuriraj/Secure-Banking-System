<%@ include file="header.jsp"%>
<h3 style="text-align: center;">List of Internal Users</h3>

<div style="width: 100%; margin: 0 auto;">
	<table id="tfhover" class="tftable" border="1">
		<thead>
			<tr>
				<th>Employee ID</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Phone</th>
				<th>Email</th>
				<th>Deptartment</th>
				<c:choose>
				<c:when test="${currentuser.deptid == 4 }">
				<th>Salary</th>
				</c:when>
				</c:choose>

			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${users}">
			<c:choose>
				<c:when test="${user.deptid == currentuser.deptid || currentuser.deptid == 100 || currentuser.deptid == 4 }">
					<tr>
						<td>${user.employeeId}</td>
						<td>${user.firstname}</td>
						<td>${user.lastname}</td>
						<td>${user.telephone}</td>
						<td>${user.email}</td>
						<td>
							<c:choose>
							<c:when test="${user.deptid == 1 }">
								Sales
							</c:when>
							<c:when test="${user.deptid == 2 }">
								IT & Support
							</c:when>
							<c:when test="${user.deptid == 3 }">
								Transaction Monitoring
							</c:when>
							<c:when test="${user.deptid == 4 }">
								HR
							</c:when>
							<c:when test="${user.deptid == 5 }">
								Company Management
							</c:when>
							<c:when test="${user.deptid == 6 }">
								NOT SET
							</c:when>
							<c:when test="${user.deptid == 100 }">
								Admins
							</c:when>
							</c:choose>
						</td>
						<c:choose>
						<c:when test="${currentuser.deptid == 4 }">
						<td>${user.salary}</td>
						</c:when>
						</c:choose>
					</tr>
				</c:when>
				</c:choose>
			</c:forEach>
		</tbody>
	</table>
</div>
<p style="text-align: center;">
	Go Back to <a class="myButton"
		href="/SecureOnlineBanking/welcome">Home Page</a>

</p>
<%@ include file="footer.jsp"%>