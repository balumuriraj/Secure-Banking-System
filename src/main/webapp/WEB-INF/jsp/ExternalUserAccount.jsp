<%@ include file="header.jsp"%>
<div style="width: 900px; margin: 0 auto; background:#fff; box-shadow: 0 1px 5px #000;">
	<div style="padding: 10px 30px 10px 30px;">
		<%@ include file="navigation.jsp"%>
		<br> <br>
		<div>
			<h4>
				Logged in as:
				<sec:authentication property="name" />
				<sec:authentication property="authorities" />
			</h4>
			<div style="background:#555; color:white; padding:10px;">
				<b>Find/Modify External User Account</b>
			</div>
			<br />
			
			<form:form method="post" modelAttribute="externalAccount" action="/SecureOnlineBanking/findExternalAccount">
				<table>
					<tr>
						<td><b>User Name<b/></td>
						<td><form:input path="username"></form:input></td>
						<td><input type="submit" value="Find" class ="myButton" /></td>
					</tr>
				</table>
			</form:form>
		</div>
		<br />
		
	
		<br />

		<hr>
		<div style="text-align: center;">
			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<a class ="myButton" href="displayExternalUsersAccounts">Display External User Accounts </a>
			</div>
			
		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>