<%@ include file="header.jsp"%>

<div
	style="width: 900px; margin: 0 auto; background: #fff; box-shadow: 0 1px 5px #000;">
	<div style="padding: 10px 30px 10px 30px;">
		<%@ include file="navigation.jsp"%>
		<br> <br>
		<div style="">

			<h4>
				Logged in as:
				<sec:authentication property="name" />
				<sec:authentication property="authorities" />
			</h4>
			<div style="background: #555; color: white; padding: 10px;">
				<b>Update Account</b>
			</div>
			<br>
			<form:form method="post" modelAttribute="user"
				action="/SecureOnlineBanking/updateExternalUserAccountDetails">
				<%-- <form:errors path="*" cssClass="errorblock" element="div" /> --%>
				<table class="">
					<c:if test="${not empty errors}">
						<tr>
							<td colspan="3"><p class="dberrors">${errors}</p></td>
						</tr>
					</c:if>
					<tr>
						<td><form:label path="username">Username</form:label></td>
						<td><form:input path="username" readonly="true" /></td>
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

					<form:hidden path="userid" />
					<form:hidden path="password" />
					<form:hidden path="accountNo" />
					<form:hidden path="authorized" />
					<form:hidden path="ssn" />
					<form:hidden path="securityanswer" />
					<form:hidden path="securityquestion" />
					<form:hidden path="currentBalance" />
					<form:hidden path="firstTimeLogin" />
					<form:hidden path="type" />
					<form:hidden path="email" />
					<form:hidden path="telephone" />
					<tr>
						<td></td>
						<td><input type="submit" value="Update Account"
							class="myButton" /></td>
						<td></td>
					</tr>
				</table>
			</form:form>
		</div>
		<br> <br>
	</div>
</div>

<%@ include file="footer.jsp"%>