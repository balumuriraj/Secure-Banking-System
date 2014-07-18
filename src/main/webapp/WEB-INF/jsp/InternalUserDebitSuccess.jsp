<%@ include file="header.jsp"%>

<div style="width: 900px; background: #eee; margin: 0 auto;">
	<div style="padding: 10px 30px 30px 30px;">
		<h1>The Transaction is processed successfully! </h1><br>
		<h3>Date and Time : <%=new java.util.Date() %>
		<h3> Current Account Balance is ${message}</h3>
         <br> <a class="myButton"
			href="/SecureOnlineBanking/IndividualUser">User Account Home Page </a>
	</div>
</div>

<%@ include file="footer.jsp"%>