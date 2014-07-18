<%@ include file="header.jsp"%>
<div>
	<div>
		<h3 class="heading">External Account</h3>
		<form:form action="/SecureOnlineBanking/SystemAdmin/SystemAdminAddExternalUsersAccount">
			<%-- <form:errors path="*" cssClass="errorblock" element="div" /> --%>
			<table class="regform">
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
					<td><form:label path="type">Type</form:label></td>
					<td><form:input path="type" /></td>
					<td><form:errors path="type" cssClass="error" /></td>
				</tr>
				
			
				<tr>
					<td><br></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Create Account"
						class="myButton" /></td>
					<td></td>
				</tr>
			</table>
		</form:form>
	</div>

	
</div>
<%@ include file="footer.jsp"%>