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

			<div style="background: #555; color: white; padding: 10px;">
				<b>Find User</b>
			</div>
			<br />

			<form:form method="post" modelAttribute="user"
				action="externalUserManagement/finduser">
				<table>
					<tr>
						<td><b>User Id</b></td>
						<td><form:input path="userid" type="number"></form:input></td>
						<td><input type="submit" value="Find" class="myButton" /></td>
					</tr>
				</table>
			</form:form>
		</div>
		<br />

		<sec:authorize access="hasAnyRole('CEO')">
			<div style="background: #555; color: white; padding: 10px;">
				<b>PII Authorization Requests</b>
			</div>
			<br />

			<table id="tfhover" class="tftable" border="1">
				<thead>
					<tr>
						<th>userid</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="piiAuth" items="${piiAuths}">
						<tr>
							<td>${piiAuth.userid}</td>
							<td><c:choose>
									<c:when test="${piiAuth.isauthorized == 'yes'}">
									Authorized
								</c:when>

									<c:otherwise>
										<c:set target="${user}" property="userid"
											value="${piiAuth.userid}" />
										<form:form method="post" modelAttribute="user"
											action="externalUserManagement/authorizepii">
											<form:hidden path="userid" />
											<input type="submit" value="Authorize" class="myButton" />
										</form:form>
									</c:otherwise>
								</c:choose></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br />
		</sec:authorize>

		<div style="background: #555; color: white; padding: 10px;">
			<b>PII</b>
		</div>
		<br />

		<table id="tfhover" class="tftable" border="1">
			<thead>
				<tr>
					<th>userid</th>
					<th>firstname</th>
					<th>lastname</th>
					<th>ssn</th>
					<th>telephone</th>
					<th>email</th>
					<th>accountNo</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="extpiireq" items="${extpiireqs}">
					<tr>
						<td>${extpiireq.userid}</td>
						<td>${extpiireq.firstname}</td>
						<td>${extpiireq.lastname}</td>
						<td>${extpiireq.ssn}</td>
						<td>${extpiireq.telephone}</td>
						<td>${extpiireq.email}</td>
						<td>${extpiireq.accountNo}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br />

		<hr>
		<div style="text-align: center;">
			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<a class="myButton" href="externalUserManagement/displayusers">Display
					Users </a>
			</div>
		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>