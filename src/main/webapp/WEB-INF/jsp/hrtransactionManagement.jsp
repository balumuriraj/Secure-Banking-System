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



		<div>
			<div style="background: #555; color: white; padding: 10px;">
				<b>Update Salary</b>
			</div>
			<br />

			<form:form method="post" modelAttribute="user"
				action="/SecureOnlineBanking/transactionManagement/updatesalary">
				<c:if test="${not empty errors}">

					<p class="dberrors">${errors}</p>

				</c:if>
				<table>
					<tr>
						<td><b>Employee ID</b></td>
						<td><form:input path="employeeId"  type="number"></form:input></td>
					</tr>
					<tr>
						<td><b>Salary</b></td>
						<td><form:input path="salary" type="number"></form:input></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Update" class="myButton" /></td>
					</tr>
				</table>
			</form:form>
		</div>
		<br />
		<hr>
		<div style="text-align: center;">

			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<form:form method="post" modelAttribute="currentuser"
					action="/SecureOnlineBanking/transactionManagement/displayusers">
					<form:hidden path="deptid" />
					<input type="submit" value="Display Users" class="myButton" />
				</form:form>
			</div>

		</div>

	</div>
</div>

<%@ include file="footer.jsp"%>