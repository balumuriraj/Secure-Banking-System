<%@ include file="header.jsp"%>
<div style="width: 900px; margin: 0 auto; background:#fff; box-shadow: 0 1px 5px #000;">
	<div style="padding: 10px 30px 10px 30px;">
		<h2 style="text-align: center;">Welcome to Individual User Account HomePage</h2>
		
	     <div>
		
			<br />
			
			<form:form method="post" modelAttribute="indiUser" action="/SecureOnlineBanking/IndividualUser/viewTransactions">
				<table>
					<tr>
						<td><b>User Name<b/></td>
						<td><form:input path="username"></form:input></td>
						<td><input type="submit" value="View Transactions" class ="myButton" /></td>
					</tr>
				</table>
			</form:form>
		</div>
		
		  <div>
		
			<br/>
			
			<form:form method="post" modelAttribute="indiUser" action="/SecureOnlineBanking/IndividualUser/accountManagement">
				<table>
					<tr>
						<td><b>User Name<b/></td>
						<td><form:input path="username"></form:input></td>
						<td><input type="submit" value="Account Management" class ="myButton" /></td>
					</tr>
				</table>
			</form:form>
		</div>
		
		  <div>
		
			<br/>
			
			<form:form method="post" modelAttribute="indiUser" action="/SecureOnlineBanking/IndividualUser/creditCardRequest">
				<table>
					<tr>
						<td><b>User Name<b/></td>
						<td><form:input path="username"></form:input></td>
						<td><input type="submit" value="Credit Card Request" class ="myButton" /></td>
					</tr>
				</table>
			</form:form>
		</div>
		
		<div style="text-align: center;">
		
	
			
		</div>
		
		
	</div>
</div>

<%@ include file="footer.jsp"%>