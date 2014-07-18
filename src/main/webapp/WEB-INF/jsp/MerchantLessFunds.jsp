<%@ include file="header.jsp"%>

<div style="width: 900px; background: #eee; margin: 0 auto;">
	<div style="padding: 10px 30px 30px 30px;">
		<h3>Sorry The Transaction Cannot be Completed You have Insufficient Funds </h3><br>
		<h3> Current Account Balance is ${message}</h3>
         <br> <a class="myButton"
			href="/SecureOnlineBanking/welcome">Home Page </a>
	</div>
</div>

<%@ include file="footer.jsp"%>