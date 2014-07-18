<%@ include file="header.jsp"%>
<h3 class="heading">Update Account </h3>
<form:form method="post" modelAttribute="user" action="/SecureOnlineBanking/updateInternalUserAccountDetails">
	<%-- <form:errors path="*" cssClass="errorblock" element="div" /> --%>
	<table class="regform">
		<tr>
			<td><form:label path="username">Username</form:label></td>
			<td><form:input path="username" /></td>
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
			<td><form:label path="email">Email</form:label></td>
			<td><form:input path="email" /></td>
			<td><form:errors path="email" cssClass="error" /></td>
		</tr>

		<tr>
			<td><form:label path="telephone">Phone</form:label></td>
			<td><form:input path="telephone" /></td>
			<td><form:errors path="telephone" cssClass="error" /></td>
		</tr>

		<tr>
			<td><form:label path="gender">Sex</form:label></td>
			<td><form:radiobutton path="gender" value="male"
					cssClass="radio" />Male <form:radiobutton path="gender"
					value="female" cssClass="radio" />Female</td>
			<td><form:errors path="gender" cssClass="error" /></td>
		</tr>
>

		<tr>
			<td><form:label path="deptid">Department ID</form:label></td>
			<td><form:input path="deptid" /></td>
			<td><form:errors path="deptid" cssClass="error" /></td>
		</tr>

		<tr>
			<td><form:label path="position">Position</form:label></td>
			<td><form:input path="position" /></td>
			<td><form:errors path="position" cssClass="error" /></td>
		</tr>
	
			   <tr>
    <form:hidden path="employeeId"  />
    </tr>
	
	    				   <tr>
    <form:hidden path="password"  />
    </tr>
    
    
	    				   <tr>
    <form:hidden path="ssn"  />
    </tr>
    
    
	    				   <tr>
    <form:hidden path="securityquestion"  />
    </tr>
    
    
	    				   <tr>
    <form:hidden path="securityanswer"  />
    </tr>
		<tr>
			<td></td>
			<td><input type="submit" value="Update Account" class ="myButton" />
			</td>
			<td></td>
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