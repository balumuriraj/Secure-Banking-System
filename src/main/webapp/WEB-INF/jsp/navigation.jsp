<sec:authorize access="hasAnyRole('CEO', 'PRESIDENT', 'VICEPRESIDENT')">
	<div style="float: left;">
		<ul id="nav">
			<li><a href="/SecureOnlineBanking/welcome">Home</a></li>
			<li><a href="/SecureOnlineBanking/internalUserManagement">Employee
					Management</a></li>
			<li><a href="/SecureOnlineBanking/externalUserManagement">Customer
					Management</a></li>
		</ul>
	</div>
</sec:authorize>

<sec:authorize access="hasAnyRole('MANAGER')">
	<c:choose>
		<c:when test="${currentuser.deptid == '1'}">
			<div style="float: left;">
				<ul id="nav">
					<li><a href="/SecureOnlineBanking/welcome">Home</a></li>
					<li><a href="/SecureOnlineBanking/internalUserManagement">Employee
							Management</a></li>
					<li><a href="/SecureOnlineBanking/transactionManagement">Sales
							Management</a></li>
				</ul>
			</div>
		</c:when>
		<c:when test="${currentuser.deptid == '2'}">
			<div style="float: left;">
				<ul id="nav">
					<li><a href="/SecureOnlineBanking/welcome">Home</a></li>
					<li><a href="/SecureOnlineBanking/internalUserManagement">Employee
							Management</a></li>
					<li><a href="/SecureOnlineBanking/ITRegularEmployee/displaytransactions">IT & Support Management</a></li>
					<li><a href="/SecureOnlineBanking/ITRegularEmployee/ITInternalUserTransaction">Create IT Ticket</a></li>
				</ul>
			</div>
		</c:when>
		<c:when test="${currentuser.deptid == '3'}">
			<div style="float: left;">
				<ul id="nav"  style="font-size:12px">
					<li><a href="/SecureOnlineBanking/welcome">Home</a></li>
					<li><a href="/SecureOnlineBanking/internalUserManagement">Employee
							Management</a></li>
					<li><a
						href="/SecureOnlineBanking/transactionMonitoringEmployee/displayexternaltransactions">Monitor
							Customer Transactions</a></li>
							<li><a
						href="/SecureOnlineBanking/transactionMonitoringEmployee/ExternalUserTransaction">Create
							Customer Transaction</a></li>
					<li><a href="/SecureOnlineBanking/displayExternalUsersAccounts">Customer
							Accounts</a></li>
				</ul>
			</div>
		</c:when>
		<c:when test="${currentuser.deptid == '4'}">
			<div style="float: left;">
				<ul id="nav">
					<li><a href="/SecureOnlineBanking/welcome">Home</a></li>
					<li><a href="/SecureOnlineBanking/internalUserManagement">Employee
							Management</a></li>
					<li><a href="/SecureOnlineBanking/transactionManagement">HR
							Management</a></li>
				</ul>
			</div>
		</c:when>
		<c:when test="${currentuser.deptid == '5'}">
			<div style="float: left;">
				<ul id="nav">
					<li><a href="/SecureOnlineBanking/welcome">Home</a></li>
					<li><a href="/SecureOnlineBanking/internalUserManagement">Employee
							Management</a></li>
					<li><a href="/SecureOnlineBanking/CompanyManagement/displayMeetings">Scheduled Meeting Management</a></li>
					<li><a href="/SecureOnlineBanking/CompanyManagement/CompanyManagementTransaction">Schedule Meeting</a></li>
				</ul>
			</div>
		</c:when>
	</c:choose>
</sec:authorize>

<sec:authorize access="hasAnyRole('EMPLOYEE')">
	<c:choose>
		<c:when test="${currentuser.deptid == '1'}">
			<div style="float: left;">
				<ul id="nav">
					<li><a href="/SecureOnlineBanking/welcome">Home</a></li>
					<li><a href="/SecureOnlineBanking/transactionManagement">Sales
							Management</a></li>
				</ul>
			</div>
		</c:when>
		<c:when test="${currentuser.deptid == '2'}">
			<div style="float: left;">
				<ul id="nav">
					<li><a href="/SecureOnlineBanking/welcome">Home</a></li>
					<li><a href="/SecureOnlineBanking/ITRegularEmployee/displaytransactions">IT & Support Management</a></li>
					<li><a href="/SecureOnlineBanking/ITRegularEmployee/ITInternalUserTransaction">Create IT Ticket</a></li>
				</ul>
			</div>
		</c:when>
		<c:when test="${currentuser.deptid == '3'}">
			<div style="float: left;">
				<ul id="nav">
					<li><a href="/SecureOnlineBanking/welcome">Home</a></li>
					<li><a
						href="/SecureOnlineBanking/transactionMonitoringEmployee/displayexternaltransactions">Monitor
							Customer Transactions</a></li>
							<li><a
						href="/SecureOnlineBanking/transactionMonitoringEmployee/ExternalUserTransaction">Create
							Customer Transaction</a></li>
					<li><a href="/SecureOnlineBanking/displayExternalUsersAccounts">Customer
							Accounts</a></li>
				</ul>
			</div>
		</c:when>
		<c:when test="${currentuser.deptid == '4'}">
			<div style="float: left;">
				<ul id="nav">
					<li><a href="/SecureOnlineBanking/welcome">Home</a></li>
					<li><a href="/SecureOnlineBanking/transactionManagement">HR
							Management</a></li>
				</ul>
			</div>
		</c:when>
		<c:when test="${currentuser.deptid == '5'}">
			<div style="float: left;">
				<ul id="nav">
					<li><a href="/SecureOnlineBanking/welcome">Home</a></li>
					<li><a href="/SecureOnlineBanking/CompanyManagement/displayMeetings">Scheduled Meeting Management</a></li>
					<li><a href="/SecureOnlineBanking/CompanyManagement/CompanyManagementTransaction">Schedule Meeting</a></li>
				</ul>
			</div>
		</c:when>
	</c:choose>
</sec:authorize>
<sec:authorize access="hasAnyRole('INDIVIDUAL')">
	<div style="float: left;">
		<ul id="nav">
			<li><a href="/SecureOnlineBanking/welcome">Home</a></li>
			<li><a href="/SecureOnlineBanking/IndividualUser/creditCardRequest">Credit Card Request</a></li>
			<li><a href="/SecureOnlineBanking/IndividualUser/accountManagement">Make Transaction</a></li>
			<li><a href="/SecureOnlineBanking/IndividualUser/viewTransactions">View Transactions</a></li>
			<li><a href="/SecureOnlineBanking/IndividualUser/transferToMerchant">Payment to Merchant</a></li>
		</ul>
	</div>
</sec:authorize>

<sec:authorize access="hasAnyRole('MERCHANT')">
	<div style="float: left;">
		<ul id="nav">
			<li><a href="/SecureOnlineBanking/welcome">Home</a></li>
			<li><a href="/SecureOnlineBanking/Merchant/viewMerchantTransactions">View Transactions</a></li>
			<li><a href="/SecureOnlineBanking/Merchant/merchantAccountManagement">Make Transactions</a></li>
			<li><a href="/SecureOnlineBanking/Merchant/makeCustomerPayment">Make Customer Payment</a></li>
		</ul>
	</div>
</sec:authorize>

<sec:authorize access="hasAnyRole('SYSADMIN')">
	<div style="float: left;">
		<ul id="nav" style="font-size:13px">
			<li><a href="/SecureOnlineBanking/welcome">Home</a></li>
			<li><a href="/SecureOnlineBanking/SystemAdmin/internalUsersNewRequests">Employee Requests</a></li>
			<li><a href="/SecureOnlineBanking/SystemAdmin/externalUsersNewRequests">Customer Requests</a></li>
			<li><a href="/SecureOnlineBanking/SystemAdmin/addinternalaccount"> Employee Accounts</a></li>
		
			<li><a href="/SecureOnlineBanking/SystemAdmin/addexternalaccount">Customer Accounts</a></li>
			<li><a href="/SecureOnlineBanking/SystemAdmin/viewLogs">Logs</a></li>
			
		</ul>
	</div>
</sec:authorize>


<div style="float: right;">
	<a class="myButton" href="<c:url value="/j_spring_security_logout" />">
		Logout</a>
</div>
