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
				<b>Update Employee Account</b>
			</div>
			<br>
			<form:form method="post" modelAttribute="user"
				action="/SecureOnlineBanking/SystemAdmin/updateInternalUserAccountDetails">
				<%-- <form:errors path="*" cssClass="errorblock" element="div" /> --%>
				<c:if test="${not empty errors}">
					<tr>
						<td colspan="3"><p class="dberrors">${errors}</p></td>
					</tr>
				</c:if>
				<table class="">
					<tr>
						<td><form:label path="username">Username</form:label></td>
						<td><form:input path="username" readonly = "true"/></td>
						<td><form:errors path="username" cssClass="error" /></td>
					</tr>

					<tr>
						<td><form:label path="firstname">First Name</form:label></td>
						<td><form:input path="firstname" /></td>
						<td><form:errors path="firstname" cssClass="error" /></td>
					</tr>
					<tr>
						<td><form:label path="lastname">Last Name</form:label></td>
						<td><form:input path="lastname" /></td>
						<td><form:errors path="lastname" cssClass="error" /></td>
					</tr>

					<tr>
						<td><form:label path="dob">Date of Birth (mm/dd/yyyy)</form:label></td>
						<td><form:input path="dob" /></td>
						<td><form:errors path="dob" cssClass="error" /></td>
					</tr>


					<tr>
						<td><form:label path="address">Address </form:label></td>
						<td><form:textarea path="address" /></td>
						<td><form:errors path="address" cssClass="error" /></td>
					</tr>

					<tr>
						<td><form:label path="gender">Sex</form:label></td>
						<td><form:radiobutton path="gender" value="male"
								cssClass="radio" />Male <form:radiobutton path="gender"
								value="female" cssClass="radio" />Female</td>
						<td><form:errors path="gender" cssClass="error" /></td>
					</tr>



					<tr>
						<td><form:label path="deptid">Department ID</form:label></td>
						<td><form:select path="deptid">
							<form:option value="1" label="Sales" />
							<form:option value="2" label="IT & Support" />
							<form:option value="3" label="Transaction Monitoring" />
							<form:option value="4" label="HR" />
							<form:option value="5" label="Company Managment" />
						</form:select></td>
						<td><form:errors path="deptid" cssClass="error" /></td>
					</tr>

					<tr>
						<td><form:label path="position">Position</form:label></td>
						<td><form:select path="position">
							<form:option value="MANAGER" label="Manager" />
							<form:option value="EMPLOYEE" label="Employee" />
						</form:select></td>
						<td><form:errors path="position" cssClass="error" /></td>
					</tr>


					<tr>
						<td></td>
						<td><input type="submit" value="Update Account"
							class="myButton" /></td>
						<td></td>
					</tr>

						<form:hidden path="employeeId" />
						<form:hidden path="password" />
						<form:hidden path="ssn" />
						<form:hidden path="firstTimeLogin" />
						<form:hidden path="authorized" />
						<form:hidden path="securityquestion" />
						<form:hidden path="securityanswer" />
						<form:hidden path="email" />
						<form:hidden path="telephone" />
				</table>
			</form:form>
		</div>
		<br> <br>
	</div>
</div>
<%@ include file="footer.jsp"%>