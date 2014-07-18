<%@ include file="header.jsp"%>
<div>
	<div>
		<h3 class="heading">Forget Password</h3>
		<form:form method="post" modelAttribute="forgot_user" action="/SecureOnlineBanking/Verify">
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
					<td><form:label path="telephone">Telephone</form:label></td>
					<td><form:password path="telephone" /></td>
					<td><form:errors path="telephone" cssClass="error" /></td>
				</tr>

				<tr>
					<td><form:label path="email">Email</form:label></td>
					<td><form:input path="email" /></td>
					<td><form:errors path="email" cssClass="error" /></td>
				</tr>

				<tr>
					<td><form:label path="securityquestion">Security Question</form:label></td>
					<td><form:select path="securityquestion">
				
							<form:options items="${securityList}" />
						</form:select></td>
					<td><form:errors path="securityquestion" cssClass="error" /></td>

				</tr>
				<tr>
					<td><form:label path="securityanswer">Security Answer</form:label></td>
					<td><form:input path="securityanswer" /></td>
					<td><form:errors path="securityanswer" cssClass="error" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Send Temporary Password"
						class="myButton" /></td>
					<td></td>
				</tr>
			</table>
		</form:form>
	</div>

	<p style="text-align: center">
		<a href="/SecureOnlineBanking/">GO to Home Page</a>
	</p>
</div>
<%@ include file="footer.jsp"%>