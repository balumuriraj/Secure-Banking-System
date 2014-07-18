<%@ include file="header.jsp"%>
<div style="width:900px; background: #eee; margin: 0 auto;">
	<div style="padding: 10px 30px 30px 30px;">
		<h3>${exception.exceptionMsg}</h3>
		
		<sec:authorize access="hasAnyRole('CEO', 'PRESIDENT', 'VICEPRESIDENT')">
		Go Back to <a class="myButton" href="/SecureOnlineBanking/internalUserManagement">Internal User Management</a> or 
		<a class="myButton" href="/SecureOnlineBanking/externalUserManagement">External User Management</a>
		</sec:authorize>
		
		<sec:authorize access="hasAnyRole('MANAGER')">
		Go Back to <a class="myButton" href="/SecureOnlineBanking/internalUserManagement">Internal User Management</a>
		</sec:authorize>
		
		<sec:authorize access="hasAnyRole('EMPLOYEE')">
		Go Back to <a class="myButton" href="/SecureOnlineBanking/transactionManagement">Transaction Management</a>
		</sec:authorize>
		
	</div>
</div>

<%@ include file="footer.jsp"%>