<%@ include file="header.jsp"%>
<div>
	<div>
		<h3 class="heading">Raise Request</h3>
		<form:form method="post" modelAttribute="user">
			<%-- <form:errors path="*" cssClass="errorblock" element="div" /> --%>
			<table>
				<tr>
					<td><form:label path="RequestType">RequestType</form:label></td>
					<td><form:input path="RequestType" /></td>
					<td><form:errors path="RequestType" cssClass="error" /></td>
				</tr>

				<tr>
					<td><form:label path="RequestDescription">RequestDescription</form:label></td>
					<td><form:password path="RequestDescription" /></td>
					<td><form:errors path="RequestDescription" cssClass="error" /></td>
				</tr>
				<tr>

					<td colspan="2"><input type="submit" value="Submit" />
					</td>

					<td>
					</td>
				</tr>
				</table>
		</form:form>
	</div>
</div>
<%@ include file="footer.jsp"%>
