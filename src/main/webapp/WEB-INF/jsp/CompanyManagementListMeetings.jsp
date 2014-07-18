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
					<b>Find/Delete Meeting</b>
				</div>
				<br />

				<form:form method="post" modelAttribute="singleMeeting"
					action="/SecureOnlineBanking/CompanyManagement/findMeeting">
					<table>
						<tr>
							<td><b>Ticket ID<b /></td>
							<td><form:input path="transId" type="number"></form:input></td>
							<td><input type="submit" value="Find" class="myButton" /></td>
						</tr>
					</table>
				</form:form>
			</div> --%>
			<div style="background: #555; color: white; padding: 10px;">
				<b>List of Scheduled Meeting</b>
			</div>
			<br>

			<div style="width: 100%; margin: 0 auto;">
				<table id="tfhover" class="tftable" border="1">
					<thead>
						<tr>
							<th>Ticket ID</th>
							<th>Employee ID</th>
							<th>Type</th>
							<th>Meeting Description</th>
							<th>Status</th>
							<th colspan="2">Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${transactions}">
							<tr>
								<td>${item.transId}</td>
								<td>${item.employeeId}</td>
								<td>${item.transType}</td>
								<td>${item.description}</td>
								<td>${item.status}</td>
								<td>
								<c:set target="${trans}" property="transId"
											value="${item.transId}" />
										<form:form method="post" modelAttribute="trans"
											action="/SecureOnlineBanking/CompanyManagement/updateMeeting">
											<form:hidden path="transId" />
											<input type="submit" value="Update" class="myButton" />
										</form:form>
								</td>

								<td>
								<c:set target="${trans}" property="transId"
											value="${item.transId}" />
										<form:form method="post" modelAttribute="trans"
											action="/SecureOnlineBanking/CompanyManagement/deleteMeeting">
											<form:hidden path="transId" />
											<input type="submit" value="Delete" class="myButton" />
										</form:form>
								</td>


							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<br>


		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>