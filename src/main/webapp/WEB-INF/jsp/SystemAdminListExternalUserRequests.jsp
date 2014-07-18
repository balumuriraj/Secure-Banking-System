<%@ include file="header.jsp"%>
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

		<div style="background: #555; color: white; padding: 10px;">
			<b>Manage Customer Requests</b>
		</div>
		<br />

		<%-- <form:form method="post" modelAttribute="singleExternalAccount" action="/SecureOnlineBanking/SystemAdmin/findExternalRequest">
				<table>
					<tr>
						<td><b>User Name<b/></td>
						<td><form:input path="username"></form:input></td>
						<td><input type="submit" value="Find" class ="myButton" /></td>
					</tr>
				</table>
			</form:form> --%>

		<div style="width: 100%; margin: 0 auto;">
			<table id="tfhover" class="tftable" border="1">
				<thead>
					<tr>
						<th>USERNAME</th>
						<th>FIRSTNAME</th>
						<th>TYPE</th>
						<th>STATUS</th>
						<th colspan="4">ACTIONS</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${externalAccounts}">
						<tr>
							<td>${item.username}</td>
							<td>${item.firstname}</td>
							<td>${item.type}</td>
							<td><c:choose>
									<c:when test="${item.authorized == 'true'}">
											Activated
										</c:when>
									<c:otherwise>Pending</c:otherwise>
								</c:choose></td>
							<td><c:set target="${singleExternalAccount}"
									property="username" value="${item.username}" /> <form:form
									method="post" modelAttribute="singleExternalAccount"
									action="/SecureOnlineBanking/SystemAdmin/assignTypeExternalUserAccount">
									<form:hidden path="username" />
									<input type="submit" value="Assign Role" class="myButton" />
								</form:form></td>
							<td><c:choose>
									<c:when test="${item.authorized == 'true'}">
											Authorized
										</c:when>
									<c:otherwise>
										<c:set target="${singleExternalAccount}" property="username"
											value="${item.username}" />
										<form:form method="post"
											modelAttribute="singleExternalAccount"
											action="/SecureOnlineBanking/SystemAdmin/authorizeExtUserRequest">
											<form:hidden path="username" />
											<input type="submit" value="Authorize" class="myButton" />
										</form:form>
									</c:otherwise>
								</c:choose></td>
							<td><c:set target="${singleExternalAccount}"
									property="username" value="${item.username}" /> <form:form
									method="post" modelAttribute="singleExternalAccount"
									action="/SecureOnlineBanking/SystemAdmin/viewExternalUserAccount">
									<form:hidden path="username" />
									<input type="submit" value="View" class="myButton" />
								</form:form></td>
							<td><c:set target="${singleExternalAccount}"
									property="username" value="${item.username}" /> <form:form
									method="post" modelAttribute="singleExternalAccount"
									action="/SecureOnlineBanking/SystemAdmin/updateExternalUserAccount">
									<form:hidden path="username" />
									<input type="submit" value="Update" class="myButton" />
								</form:form></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<br> <br>
	</div>
</div>
<%@ include file="footer.jsp"%>