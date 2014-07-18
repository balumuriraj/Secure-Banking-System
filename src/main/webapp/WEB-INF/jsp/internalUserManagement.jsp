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
		<sec:authorize
			access="hasAnyRole('CEO', 'PRESIDENT', 'VICEPRESIDENT')">
			<div>

				<div style="background: #555; color: white; padding: 10px;">
					<b>Find User</b>
				</div>
				<br />

				<form:form method="post" modelAttribute="user"
					action="internalUserManagement/finduser">
					<table>
						<tr>
							<td><b>Employee ID</b></td>
							<td><form:input path="employeeId" type="number" ></form:input></td>
							<td><input type="submit" value="Find" class="myButton" /></td>
						</tr>
					</table>
				</form:form>
			</div>
			<br />
		</sec:authorize>

		<div>
			<div style="background: #555; color: white; padding: 10px;">
				<b>Transfer User</b>
			</div>
			<br />

			<form:form method="post" modelAttribute="user"
				action="internalUserManagement/transferuser">
				<table>
					<tr>
						<td><b>Employee ID</b></td>
						<td><form:input path="employeeId" type="number" ></form:input></td>
					</tr>
					<tr>
						<td><b>To Department ID</b></td>
						<td><form:select path="deptid">
								<form:option value="0" label="--- Select ---" />
								<form:option value="1" label="Sales" />
								<form:option value="2" label="IT and Technical Support" />
								<form:option value="3" label="Transactions Monitoring" />
								<form:option value="4" label="Human Resources" />
								<form:option value="5" label="Company management" />
							</form:select></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Transfer" class="myButton" /></td>
					</tr>
				</table>
			</form:form>
		</div>
		<br />

		<div>
			<div style="background: #555; color: white; padding: 10px;">
				<b>Delete User</b>
			</div>
			<br />

			<form:form method="post" modelAttribute="user"
				action="internalUserManagement/deleteinternaluser">
				<table>
					<tr>
						<td><b>Employee ID</b></td>
						<td><form:input path="employeeId" type="number" ></form:input></td>
					</tr>
					<tr>
						<td><b>Department ID</b></td>
						<%-- <td><form:input path="deptid"></form:input></td> --%>
						<td><sec:authorize
								access="hasAnyRole('CEO', 'PRESIDENT', 'VICEPRESIDENT')">
								<form:select path="deptid">
									<form:option value="0" label="--- Select ---" />
									<form:option value="1" label="Sales" />
									<form:option value="2" label="IT and Technical Support" />
									<form:option value="3" label="Transactions Monitoring" />
									<form:option value="4" label="Human Resources" />
									<form:option value="5" label="Company management" />
								</form:select>
							</sec:authorize> <sec:authorize access="hasAnyRole('MANAGER')">
								<c:choose>
									<c:when test="${currentuser.deptid == 1}">
										<form:select path="deptid">
											<form:option value="1" label="Sales" />
										</form:select>
									</c:when>

									<c:when test="${currentuser.deptid == 2}">
										<form:select path="deptid">
											<form:option value="2" label="IT and Technical Support" />
										</form:select>
									</c:when>

									<c:when test="${currentuser.deptid == 3}">
										<form:select path="deptid">
											<form:option value="3" label="Transactions Monitoring" />
										</form:select>
									</c:when>

									<c:when test="${currentuser.deptid == 4}">
										<form:select path="deptid">
											<form:option value="4" label="Human Resources" />
										</form:select>
									</c:when>

									<c:when test="${currentuser.deptid == 5}">
										<form:select path="deptid">
											<form:option value="5" label="Company management" />
										</form:select>
									</c:when>
								</c:choose>



							</sec:authorize></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Delete" class="myButton" /></td>
					</tr>
				</table>
			</form:form>
		</div>
		<br />

		<div>
			<div style="background: #555; color: white; padding: 10px;">
				<b>Critical Transactions</b>
			</div>
			<br />

			<table id="tfhover" class="tftable" border="1">
				<thead>
					<tr>
						<th>TransactionID</th>
						<th>Request</th>
						<th>employeeID</th>
						<th>DeptartmentID</th>
						<th>CEO Approval</th>
						<th>President Approval</th>
						<th>Vice-President Approval</th>
						<sec:authorize
							access="hasAnyRole('CEO', 'PRESIDENT', 'VICEPRESIDENT')">
							<th>Action</th>
						</sec:authorize>
						<th>Status</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="ctran" items="${criticaltrans}">
						<tr>
							<td>${ctran.transId}</td>
							<td>${ctran.request}</td>
							<td>${ctran.employeeId}</td>
							<td>${ctran.deptid}</td>
							<td>${ctran.ceoapp}</td>
							<td>${ctran.presapp}</td>
							<td>${ctran.vpresapp}</td>
							<sec:authorize
								access="hasAnyRole('CEO', 'PRESIDENT', 'VICEPRESIDENT')">
								<td><c:choose>
										<c:when
											test="${ctran.vpresapp == 'no' && currentuser.position == 'VICEPRESIDENT'}">
											<c:set target="${criticaltran}" property="employeeId"
												value="${ctran.employeeId}" />
											<form:form method="post" modelAttribute="criticaltran"
												action="internalUserManagement/approveinternalcriticaltransaction">
												<form:hidden path="employeeId" />
												<input type="submit" value="Approve" class="myButton" />
											</form:form>
										</c:when>

										<c:when
											test="${ctran.presapp == 'no' && currentuser.position == 'PRESIDENT'}">
											<c:set target="${criticaltran}" property="employeeId"
												value="${ctran.employeeId}" />
											<form:form method="post" modelAttribute="criticaltran"
												action="internalUserManagement/approveinternalcriticaltransaction">
												<form:hidden path="employeeId" />
												<input type="submit" value="Approve" class="myButton" />
											</form:form>
										</c:when>

										<c:when
											test="${ctran.ceoapp == 'no' && currentuser.position == 'CEO'}">
											<c:set target="${criticaltran}" property="employeeId"
												value="${ctran.employeeId}" />
											<form:form method="post" modelAttribute="criticaltran"
												action="internalUserManagement/approveinternalcriticaltransaction">
												<form:hidden path="employeeId" />
												<input type="submit" value="Approve" class="myButton" />
											</form:form>
										</c:when>

										<c:otherwise>
										Approved
									</c:otherwise>
									</c:choose></td>
							</sec:authorize>
							<td><c:choose>
									<c:when
										test="${ctran.vpresapp == ctran.presapp && ctran.vpresapp == ctran.ceoapp}">
										Completed
									</c:when>

									<c:otherwise>
										Pending
									</c:otherwise>
								</c:choose></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<br />

		<hr>
		<div style="text-align: center;">
			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<form:form method="post" modelAttribute="currentuser"
					action="internalUserManagement/displayusers">
					<form:hidden path="deptid" />
					<input type="submit" value="Display Users" class="myButton" />
				</form:form>
			</div>
			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<a class="myButton" href="InternalRegistration">Add User</a>
			</div>
		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>