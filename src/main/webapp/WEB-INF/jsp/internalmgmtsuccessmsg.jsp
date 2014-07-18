<%@ include file="header.jsp"%>

<div style="width: 900px; background: #eee; margin: 0 auto;">
	<div style="padding: 10px 30px 30px 30px;">
		<h3>${message} </h3>
		Date and time :
		<%=new java.util.Date()%><br> <br> 
		
		<sec:authorize access="hasAnyRole('CEO', 'PRESIDENT', 'VICEPRESIDENT', 'MANAGER')">
		<a class="myButton"
			href="/SecureOnlineBanking/internalUserManagement">Internal User Management</a>
		</sec:authorize>
		
		<sec:authorize access="hasAnyRole('EMPLOYEE')">
		<a class="myButton"
			href="/SecureOnlineBanking/transactionManagement">Transaction Management</a>
		</sec:authorize>
	</div>
</div>

<%@ include file="footer.jsp"%>