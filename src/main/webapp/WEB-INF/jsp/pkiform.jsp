<%@ include file="header.jsp"%>
<div style="width: 900px; margin: 0 auto; background:#fff; box-shadow: 0 1px 5px #000;">
	<div style="padding: 10px 30px 10px 30px;">
		<h2 style="text-align: center;">Welcome to Individual User Account HomePage</h2>
		
	     <div>
			<br />
			<form:form method="post" modelAttribute="pkisubmit" action="/SecureOnlineBanking/pki/pkiform">
				<table>
					<tr>
						<td><b>PKIe<b/></td>
						<td><form:label path="userName"></form:label></td>
						<td><input type="submit" value="PKI generation" class ="myButton" /></td>
					</tr>
				</table>
			</form:form>
		</div>
		
	</div>
</div>

<%@ include file="footer.jsp"%>