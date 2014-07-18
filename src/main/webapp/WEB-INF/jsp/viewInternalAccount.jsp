<%@ include file="header.jsp"%>
<h3 class="heading"></h3>
<form:form method="post" modelAttribute="user">
	<%-- <form:errors path="*" cssClass="errorblock" element="div" /> --%>
	<table class="regform">
		<tr>
			<td><form:label path="username">Username</form:label></td>
			<td><form:input path="username" readonly="true"/></td>
			<td><form:errors path="username" cssClass="error" /></td>
		</tr>

		<tr>
			<td><form:label path="firstname">First Name</form:label></td>
			<td><form:input path="firstname" readonly="true"/></td>
			<td><form:errors path="firstname" cssClass="error" /></td>
		</tr>
		<tr>
			<td><form:label path="lastname">Last Name</form:label></td>
			<td><form:input path="lastname" readonly="true"/></td>
			<td><form:errors path="lastname" cssClass="error" /></td>
		</tr>
		
		<tr>
			<td><form:label path="dob">Date of Birth (mm/dd/yyyy)</form:label></td>
			<td><form:input path="dob" readonly="true"/></td>
			<td><form:errors path="dob" cssClass="error" /></td>
		</tr>


		<tr>
			<td><form:label path="address">Address </form:label></td>
			<td><form:textarea path="address" readonly="true"/></td>
			<td><form:errors path="address" cssClass="error" /></td>
		</tr>

		<tr>
			<td><form:label path="email">Email</form:label></td>
			<td><form:input path="email" readonly="true"/></td>
			<td><form:errors path="email" cssClass="error" /></td>
		</tr>

		<tr>
			<td><form:label path="telephone">Phone</form:label></td>
			<td><form:input path="telephone" readonly="true"/></td>
			<td><form:errors path="telephone" cssClass="error" /></td>
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
			<td><form:input path="deptid" readonly="true"/></td>
			<td><form:errors path="deptid" cssClass="error" /></td>
		</tr>

		<tr>
			<td><form:label path="position">Position</form:label></td>
			<td><form:input path="position" readonly="true"/></td>
			<td><form:errors path="position" cssClass="error" /></td>
		</tr>
		<tr>
			<td><br></td>
		</tr>
		<tr>
		
			<td></td>
		<td>
		<p style="text-align:center;">Go Back to <a class="myButton" href="/SecureOnlineBanking/internalUsersAccounts">Internal User Account Management</a></p>
			</td>
			
		</tr>
	</table>
</form:form>

<%@ include file="footer.jsp"%>