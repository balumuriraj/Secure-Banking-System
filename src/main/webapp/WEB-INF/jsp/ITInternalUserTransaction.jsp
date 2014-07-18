<%@ include file="header.jsp"%>
<div
	style="width: 900px; margin: 0 auto; background: #fff; box-shadow: 0 1px 5px #000;">
	<div style="padding: 10px 30px 10px 30px;">
		<sec:authorize access="hasAnyRole('MANAGER')">
			<c:choose>
				<c:when
					test="${currentuser.deptid == '1' || currentuser.deptid == '4'}">
					<div style="float: left;">
						<ul id="nav">
							<li><a href="#">Home</a></li>
							<li><a href="internalUserManagement">Internal User
									Management</a></li>
							<li><a href="transactionManagement">Transactions</a></li>
						</ul>
					</div>
				</c:when>
				<c:when test="${currentuser.deptid == '2'}">
					<div style="float: left;">
						<ul id="nav">
							<li><a href="#">Home</a></li>
							<li><a href="internalUserManagement">Internal User
									Management</a></li>
							<li><a href="ITRegularEmployee/internalUsers">Transactions</a></li>
						</ul>
					</div>
				</c:when>
			</c:choose>
		</sec:authorize>

		<sec:authorize access="hasAnyRole('EMPLOYEE')">
			<c:choose>
				<c:when
					test="${currentuser.deptid == '1' || currentuser.deptid == '4'}">
					<div style="float: left;">
						<ul id="nav">
							<li><a href="#">Home</a></li>
							<li><a href="transactionManagement">Transaction
									Management</a></li>
						</ul>
					</div>
				</c:when>
				<c:when test="${currentuser.deptid == '2'}">
					<div style="float: left;">
						<ul id="nav">
							<li><a href="#">Home</a></li>
							<li><a href="ITRegularEmployee/internalUsers">Transaction
									Management</a></li>
						</ul>
					</div>
				</c:when>
			</c:choose>

		</sec:authorize>
		<div style="float: right;">
			<a class="myButton"
				href="<c:url value="/j_spring_security_logout" />"> Logout</a>
		</div>
		<br> <br>
		<div style="">

			<h4>
				Logged in as:
				<sec:authentication property="name" />
				<sec:authentication property="authorities" />
			</h4>


			<div>
				<div style="background: #555; color: white; padding: 10px;">
					<b>Find/Delete Transaction</b>
				</div>
				<br />

				<form:form method="post" modelAttribute="transaction"
					action="/SecureOnlineBanking/ITRegularEmployee/findInternalTransaction">
					<table>
						<tr>
							<td><b>Transaction ID</b></td>
							<td><form:input path="transId"></form:input></td>
							<td><input type="submit" value="Find" class="myButton" /></td>
						</tr>
					</table>
				</form:form>
			</div>
			<br /> <br />

			<hr>
			<div style="text-align: center;">
				<div
					style="text-align: center; padding: 20px; display: inline-block;">
					<a class="myButton" href="displaytransactions">Display IT Dept
						Transactions </a>
				</div>
				<div
					style="text-align: center; padding: 20px; display: inline-block;">
					<a class="myButton" href="InternalUserTransaction">Add IT Dept
						User Transaction</a>
				</div>
			</div>
		</div>
	</div>

	<p style="text-align: center;">
		Go Back to <a class="myButton"
			href="/SecureOnlineBanking/ITRegularEmployee/internalUsers"> IT
			Department Home Page</a>
	</p>
	<%@ include file="footer.jsp"%>