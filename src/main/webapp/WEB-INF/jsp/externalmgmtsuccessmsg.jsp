<%@ include file="header.jsp"%>

<div style="width: 900px; background: #eee; margin: 0 auto;">
	<div style="padding: 10px 30px 30px 30px;">
		<h3>${message} </h3>
		Date and time :
		<%=new java.util.Date()%><br> <br> Go Back to <a class="myButton"
			href="/SecureOnlineBanking/externalUserManagement/">External User Management</a>
	</div>
</div>

<%@ include file="footer.jsp"%>