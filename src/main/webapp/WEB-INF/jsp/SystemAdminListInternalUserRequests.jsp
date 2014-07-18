<%@ include file="header.jsp"%>
<div
	style="width: 900px; margin: 0 auto; background: #fff; box-shadow: 0 1px 5px #000;">
	<div style="padding: 10px 30px 10px 30px;">
		<%@ include file="navigation.jsp"%>
		<br> <br>
		<div>
			<h4>
				Logged in as:
				<sec:authentication property="name" />
				<sec:authentication property="authorities" />
			</h4>

			<%-- <div>
				<div style="background: #555; color: white; padding: 10px;">
					<b>Find/Modify Employee Account</b>
				</div>
				<br />

				<form:form method="post" modelAttribute="singleInternalAccount"
					action="/SecureOnlineBanking/SystemAdmin/findInternalRequest">
					<table>
						<tr>
							<td><b>Employee Id<b /></td>
							<td><form:input path="employeeId"></form:input></td>
							<td><input type="submit" value="Find" class="myButton" /></td>
						</tr>
					</table>
				</form:form>
			</div> --%>
			<div style="background: #555; color: white; padding: 10px;">
				<b>Manage Employee Accounts</b>
			</div>
			<br>

			<div style="width: 100%; margin: 0 auto;">
				<table id="tfhover" class="tftable" border="1">
					<thead>
						<tr>
							<th>EMPLOYEE ID</th>
							<th>FIRSTNAME</th>
							<th>DEPT ID</th>
							<th>POSITION</th>
							<th>STATUS</th>
							<th colspan="4">Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${internalAccounts}">
							<tr>
								<td>${item.employeeId}</td>
								<td>${item.firstname}</td>
								<td>${item.deptid}</td>
								<td>${item.position}</td>
								<td><c:choose>
										<c:when test="${item.authorized == 'true'}">
											Activated
										</c:when>
										<c:otherwise>Pending</c:otherwise>
									</c:choose></td>
								<td><c:set target="${singleInternalAccount}"
										property="employeeId" value="${item.employeeId}" /> <form:form
										method="post" modelAttribute="singleInternalAccount"
										action="/SecureOnlineBanking/SystemAdmin/assignRoleInternalUserAccount">
										<form:hidden path="employeeId" />
										<input type="submit" value="Assign Role" class="myButton" />
									</form:form></td>
								<td><c:choose>
										<c:when test="${item.authorized == 'true'}">
											Authorized
										</c:when>
										<c:otherwise>
											<c:set target="${singleInternalAccount}"
												property="employeeId" value="${item.employeeId}" />
											<form:form method="post"
												modelAttribute="singleInternalAccount"
												action="/SecureOnlineBanking/SystemAdmin/authorizeUserRequest">
												<form:hidden path="employeeId" />
												<input type="submit" value="Authorize" class="myButton" />
											</form:form>
										</c:otherwise>
									</c:choose></td>
									<td><c:set target="${singleInternalAccount}"
										property="employeeId" value="${item.employeeId}" /> <form:form
										method="post" modelAttribute="singleInternalAccount"
										action="/SecureOnlineBanking/SystemAdmin/viewInternalUserAccount">
										<form:hidden path="employeeId" />
										<input type="submit" value="View" class="myButton" />
									</form:form></td>
									<td><c:set target="${singleInternalAccount}"
										property="employeeId" value="${item.employeeId}" /> <form:form
										method="post" modelAttribute="singleInternalAccount"
										action="/SecureOnlineBanking/SystemAdmin/updateInternalUserAccount">
										<form:hidden path="employeeId" />
										<input type="submit" value="Update" class="myButton" />
									</form:form></td>


							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<br> <br>
	</div>
</div>
<%@ include file="footer.jsp"%>