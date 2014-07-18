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
				<b>Assign Role to Employee</b>
			</div>
			<br>

		<form:form method="post" modelAttribute="user"
			action="/SecureOnlineBanking/SystemAdmin/internalUserRoleAssigned">
			<%-- <form:errors path="*" cssClass="errorblock" element="div" /> --%>
			<table class="">
				<tr>
					<td><form:label path="username">Username</form:label></td>
					<td><b>${user.username}</b></td>
					<td><form:errors path="username" cssClass="error" /></td>
				</tr>

				<tr>
					<td><form:label path="position">Position</form:label></td>
					<td><form:select path="position">
							<form:option value="" label="--- Select ---" />
							<form:option value="MANAGER" label="Manager" />
							<form:option value="EMPLOYEE" label="Employee" />
						</form:select></td>
					<td><form:errors path="position" cssClass="error" /></td>
				</tr>


				<form:hidden path="employeeId" />
				<form:hidden path="username" />
				<form:hidden path="firstname" />
				<form:hidden path="lastname" />
				<form:hidden path="ssn" />
				<form:hidden path="dob" />
				<form:hidden path="address" />
				<form:hidden path="email" />
				<form:hidden path="gender" />
				<form:hidden path="securityquestion" />
				<form:hidden path="securityanswer" />
				<form:hidden path="deptid" />
				<form:hidden path="password" />
				<form:hidden path="authorized" />
				<form:hidden path="telephone" />

				<tr>
					<td></td>
					<td><input type="submit" value="Update Role" class="myButton" />
					</td>
					<td></td>
				</tr>

			</table>
		</form:form>
	</div>
</div>

<%@ include file="footer.jsp"%>