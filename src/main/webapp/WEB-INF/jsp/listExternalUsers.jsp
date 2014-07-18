<%@ include file="header.jsp"%>
<h3 style="text-align:center;">List of External Users</h3>
<!-- Generated from http://html-generator.weebly.com -->
<script type="text/javascript">
	window.onload=function(){
	var tfrow = document.getElementById('tfhover').rows.length;
	var tbRow=[];
	for (var i=1;i<tfrow;i++) {
		tbRow[i]=document.getElementById('tfhover').rows[i];
		tbRow[i].onmouseover = function(){
		  this.style.backgroundColor = '#ffffff';
		};
		tbRow[i].onmouseout = function() {
		  this.style.backgroundColor = '#d4e3e5';
		};
	}
};
</script>

<style type="text/css">
table.tftable {font-size:15px;color:#333333;width:100%;border-width: 1px;border-color: #729ea5;border-collapse: collapse;}
table.tftable th {font-size:15px;background-color:#acc8cc;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;text-align:left;}
table.tftable tr {background-color:#d4e3e5;}
table.tftable td {font-size:15px;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;}
</style>

<div style="width:50%; margin:0 auto;">
<table id="tfhover" class="tftable" border="1">
	<thead>
		<tr>
			<th>UserID</th>
			<th>Name</th>
			<th>Account No</th>
			<th>Current Balance</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="user" items="${users}">
			<tr>
				<td>${user.userid}</td>
				<td>${user.firstname}</td>
				<td>${user.accountNo}</td>
				<td>${user.currentBalance}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
<sec:authorize access="hasAnyRole('CEO', 'PRESIDENT', 'VICEPRESIDENT')">
<p style="text-align:center;">Go Back to <a class="myButton" href="/SecureOnlineBanking/externalUserManagement">External User Management</a></p>
</sec:authorize>
<sec:authorize access="hasAnyRole('MANAGER','EMPLOYEE')">
<p style="text-align:center;">Go Back to <a class="myButton" href="/SecureOnlineBanking/transactionManagement">Transaction Management</a></p>
</sec:authorize>
<%@ include file="footer.jsp"%>