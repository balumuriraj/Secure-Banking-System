<%@ include file="header.jsp"%>
<div style="width: 900px; margin: 0 auto; background:#fff; box-shadow: 0 1px 5px #000;">
	<div style="padding: 10px 30px 10px 30px;">
		<h2 style="text-align: center;">Welcome to Individual User Account HomePage</h2>
		
	     <div>
		
			<br />
			
			<form:form method="post" modelAttribute="otp" action="/SecureOnlineBanking/FirstTimeLogin">
				<table>
					<tr>
						<td><b>Enter One Time Password<b/></td>
						<td><form:input path="otp"></form:input></td>
						<td><input type="submit" value="Login To System" class ="myButton"/></td>
					</tr>
					
					<tr> <form:hidden path="userid"/> </tr>
						<tr> <form:hidden path="empid"/> </tr>
    			
				</table>
			</form:form>
		</div>
		
	</div>
</div>
<%@ include file="footer.jsp"%>