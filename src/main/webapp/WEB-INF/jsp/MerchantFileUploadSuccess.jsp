<%@ include file="header.jsp"%>
<div style="width: 900px; background: #eee; margin: 0 auto;">
	<div style="padding: 10px 30px 30px 30px;">
		<h3>Certificate Validated Successfully and Payment Processed  Current Balance is ${message}  </h3>
		Date and time :
		<%=new java.util.Date()%><br> <br> Go Back to <a class="myButton"
			href="/SecureOnlineBanking/welcome/">Merchant Home Page</a>
	</div>
</div>
<%@ include file="footer.jsp"%>