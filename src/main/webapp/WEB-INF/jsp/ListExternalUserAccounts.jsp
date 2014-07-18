<%@ include file="header.jsp"%>

<div
	style="width: 900px; margin: 0 auto; background: #fff; box-shadow: 0 1px 5px #000;">
	<div style="padding: 10px 30px 10px 30px;">
		<%@ include file="navigation.jsp"%>
		<br> <br>
		<div style="">

			<h4>
				Logged in as:
				<sec:authentication property="name" />
				<sec:authentication property="authorities" />
			</h4>

			<div style="width: 100%; margin: 0 auto;">

				<%-- <div>
					<div style="background: #555; color: white; padding: 10px;">
						<b>Find/Modify External User Account</b>
					</div>
					<br />

					<form:form method="post" modelAttribute="singleExternalAccount"
						action="/SecureOnlineBanking/findExternalAccount">
						<table>
							<tr>
								<td><b>User Name<b /></td>
								<td><form:input path="username"></form:input></td>
								<td><input type="submit" value="Find" class="myButton" /></td>
							</tr>
						</table>
					</form:form>
				</div> --%>
				<div style="background: #555; color: white; padding: 10px;">
					<b>All External User Accounts</b>
				</div>
				<br>
				<table id="tfhover" class="tftable" border="1">
					<thead>
						<tr>
							<th>USER ID</th>
							<th>USERNAME</th>
							<th>FIRSTNAME</th>
							<th>LASTNAME</th>
							<th>ACCOUNT NO</th>
							<th colspan="2">ACTIONS</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${externalAccounts}">
							<tr>
								<td>${item.userid}</td>
								<td>${item.username}</td>
								<td>${item.firstname}</td>
								<td>${item.lastname}</td>
								<td>${item.accountNo}</td>
								<td><c:set target="${accountbyname}" property="username"
										value="${item.username}" /> <form:form method="post"
										modelAttribute="accountbyname"
										action="/SecureOnlineBanking/updateExternalUserAccount">
										<form:hidden path="username" />
										<input type="submit" value="Update" class="myButton" />
									</form:form></td>

								<td><c:set target="${accountbyname}" property="username"
										value="${item.username}" /> <form:form method="post"
										modelAttribute="accountbyname"
										action="/SecureOnlineBanking/viewExternalUserAccount">
										<form:hidden path="username" />
										<input type="submit" value="View" class="myButton" />
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