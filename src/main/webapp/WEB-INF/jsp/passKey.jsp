<%@ include file="header.jsp"%>
<div>
	<div>
		<h3 class="heading">Enter Pass Key</h3>
		<form:form method="post" modelAttribute="passKey">
			<%-- <form:errors path="*" cssClass="errorblock" element="div" /> --%>
			<table class="regform">
				<tr>
					<td><form:label path="passKey">Pass Key</form:label></td>
					<td><form:input path="passKey" /></td>
					<td><form:errors path="passKey" cssClass="error" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Submit" class="myButton" /></td>
					<td></td>
				</tr>
			</table>
		</form:form>
	</div>

	<p style="text-align: center">
		Already have an account? Then Login <a href="/SecureOnlineBanking/">here</a>
		or <a href="/SecureOnlineBanking/">GO to Home Page</a>
	</p>
</div>
<%@ include file="footer.jsp"%>