<%@ include file="header.jsp"%>

<div style="width: 900px; background: #eee; margin: 0 auto;">
	<div style="padding: 10px 30px 30px 30px;">
		<h3>Internal User with Employee ID ${message} deleted
			Successfully!</h3>
		Date and time :
		<%=new java.util.Date()%><br> <br> <a class="myButton"
			href="/SecureOnlineBanking/internalUserManagement/displayusers">Display
			all Internal users</a>
	</div>
</div>

<%@ include file="footer.jsp"%>