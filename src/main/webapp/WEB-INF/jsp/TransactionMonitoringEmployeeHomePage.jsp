<%@ include file="header.jsp"%>
<div style="width: 900px; margin: 0 auto; background:#fff; box-shadow: 0 1px 5px #000;">
	<div style="padding: 10px 30px 10px 30px;">
		<h2 style="text-align: center;">Welcome to Transaction Monitoring Employee HomePage</h2>
		
			
		<div style="text-align: center;">
			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<a class ="InternalUserTransactions" href="transactionMonitoringEmployee/internalUsersTransactions">Internal User Transactions </a>
			</div>
			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<a class ="ExternalUserTransactions" href="transactionMonitoringEmployee/externalUsersTransactions"> External User Transactions</a>
			</div>
			
		</div>
		
		<div style="text-align: center;">
		<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<a class ="InternalUserTransactions" href="/SecureOnlineBanking/externalUsersAccounts"> External User Accounts</a>
			</div>
			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<a class ="InternalUserTransactions" href="/SecureOnlineBanking/internalUsersAccounts"> Internal User Accounts</a>
		</div>
		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>