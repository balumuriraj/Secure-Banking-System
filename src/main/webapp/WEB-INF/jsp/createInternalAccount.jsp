<%@ include file="header.jsp"%>

<h3 class="heading">Registration Form (Internal)</h3>

<form:form method="post" modelAttribute="user">
	<%-- <form:errors path="*" cssClass="errorblock" element="div" /> --%>
	<table class="regform">
		<c:if test="${not empty errors}">
			<tr>
				<td colspan="3"><p class="dberrors">${errors}</p></td>
			</tr>
		</c:if>
		<c:if test="${not empty checkbox}">
			<tr>
				<td colspan="3"><p class="dberrors">${checkbox}</p></td>
			</tr>
		</c:if>
		<tr>
			<td><form:label path="username">Username</form:label></td>
			<td><form:input path="username" /></td>
			<td><form:errors path="username" cssClass="error" /></td>
		</tr>

		<tr>
			<td><form:label path="password">Password</form:label></td>
			<td><form:password path="password" /></td>
			<td><form:errors path="password" cssClass="error" /></td>
		</tr>

		<tr>
			<td><form:label path="vpassword">Password(again)</form:label></td>
			<td><form:password path="vpassword" /></td>
			<td><form:errors path="vpassword" cssClass="error" /></td>
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
			<td><form:label path="ssn">SSN</form:label></td>
			<td><form:password path="ssn" /></td>
			<td><form:errors path="ssn" cssClass="error" /></td>
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

		<tr>
			<td><form:label path="securityquestion">Security Question</form:label></td>
			<td><form:select path="securityquestion">
					<form:option value="NONE" label="--- Select ---" />
					<form:options items="${securityList}" />
				</form:select></td>
			<td><form:errors path="securityquestion" cssClass="error" /></td>

		</tr>
		<tr>
			<td><form:label path="securityanswer">Security Answer</form:label></td>
			<td><form:input path="securityanswer" /></td>
			<td><form:errors path="securityanswer" cssClass="error" /></td>
		</tr>
		<%-- 
		<tr>
			<td><form:label path="deptid">Department ID</form:label></td>
			<td><form:select path="deptid">
					<form:option value="0" label="--- Select ---" />
					<form:option value="1" label="Sales" />
					<form:option value="2" label="IT and Technical Support" />
					<form:option value="3" label="Transactions Monitoring" />
					<form:option value="4" label="Human Resources" />
					<form:option value="5" label="Company management" />
				</form:select></td>
			<td><form:errors path="deptid" cssClass="error" /></td>
		</tr>

		<tr>
			<td><form:label path="position">Position</form:label></td>
			<td><form:select path="position">
					<form:option value="" label="--- Select ---" />
					<form:option value="MANAGER" label="Manager" />
					<form:option value="EMPLOYEE" label="Employee" />
				</form:select></td>
			<td><form:errors path="position" cssClass="error" /></td>
		</tr> --%>
		<form:hidden path="deptid" />
		<form:hidden path="position" />
		<tr>
			<td></td>
			<td><form:checkbox style="width: 20px; height: 12px;"
					path="checkbox" id="conditions" value="true" /><label
				style="line-height: 12px;" for="conditions">I accepts terms
					and conditions here</label></td>
		</tr>
		<tr>
			<td></td>
			<td><p style="font-size: 10px;">I hereby agree to provide
					authorization to Bank Official access to my account.</p></td>
		</tr>

		<tr>
			<td></td>
			<td><input type="submit" value="Create Account" class="myButton" />
			</td>
			<td></td>
		</tr>
	</table>
</form:form>
<p style="text-align: center">
	Already have an account? Then Login <a href="/SecureOnlineBanking/">here</a>
	or <a href="/SecureOnlineBanking/">GO to Home Page</a>
	<sec:authorize
		access="hasAnyRole('CEO', 'PRESIDENT', 'VICEPRESIDENT', 'MANAGER', 'SYSADMIN')">
	or go to <a class="myButton" href="/SecureOnlineBanking/welcome">Management Page</a>
	</sec:authorize>
</p>

<%@ include file="footer.jsp"%>