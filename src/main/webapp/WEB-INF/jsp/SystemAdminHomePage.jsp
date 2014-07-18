<%@ include file="header.jsp"%>
<div style="width: 900px; margin: 0 auto; background:#fff; box-shadow: 0 1px 5px #000;">
	<div style="padding: 10px 30px 10px 30px;">
		<h2 style="text-align: center;">Welcome to System Administrator HomePage</h2>
		
			
		<div style="text-align: center;">
			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<a class ="InternalUserTransactions" href="SystemAdmin/internalUsersNewRequests">Modify and Authorize Internal User Accounts </a>
			</div>
			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<a class ="ExternalUserTransactions" href="SystemAdmin/externalUsersNewRequests"> Modify and Authorize External User Accounts</a>
			</div>
			
		</div>
		
		<div style="text-align: center;">
		<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<a class ="InternalUserTransactions" href="SystemAdmin/externalUsersAccounts"> External User Account Management</a>
			</div>
			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<a class ="InternalUserTransactions" href="SystemAdmin/internalUsersAccounts"> Internal User Account Management</a>
		</div>
		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>