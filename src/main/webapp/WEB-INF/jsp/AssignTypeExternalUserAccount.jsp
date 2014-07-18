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
			<b>Assign Role to Customer</b>
		</div>
		<br>
		<form:form method="post" modelAttribute="user"
			action="/SecureOnlineBanking/SystemAdmin/externalUserTypeAssigned">
			<%-- <form:errors path="*" cssClass="errorblock" element="div" /> --%>
			<table class="">
				<tr>
					<td><form:label path="username">USERNAME</form:label></td>
					<td><form:input readonly="true" path="username" /></td>
					<td><form:errors path="username" cssClass="error" /></td>
				</tr>

				<tr>
					<td><form:label path="type">TYPE</form:label></td>
					<td><form:select path="type">
							<form:option value="" label="--- Select ---" />
							<form:option value="INDIVIDUAL" label="Individual" />
							<form:option value="MERCHANT" label="Merchant" />
						</form:select></td>
					<td><form:errors path="type" cssClass="error" /></td>
				</tr>
				<form:hidden path="userid" />
				<form:hidden path="firstname" />
				<form:hidden path="authorized" />
				<form:hidden path="telephone" />
				<form:hidden path="accountNo" />
				<form:hidden path="lastname" />
				<form:hidden path="ssn" />
				<form:hidden path="dob" />
				<form:hidden path="address" />
				<form:hidden path="email" />
				<form:hidden path="gender" />
				<form:hidden path="securityquestion" />
				<form:hidden path="securityanswer" />
				<form:hidden path="password" />
				<tr>
					<td></td>
					<td><input type="submit" value="Update Type" class="myButton" />
					</td>
					<td></td>
				</tr>
			</table>
		</form:form>
		<br>
	</div>
</div>

<%@ include file="footer.jsp"%>