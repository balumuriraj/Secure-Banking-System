<%@ include file="header.jsp"%>
<h3 style="text-align:center;">Employee Account Management</h3>
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

<div>
			<div style="background:#555; color:white; padding:10px;">
				<b>Find/Modify Employee Account</b>
			</div>
			<br />
			
			<form:form method="post" modelAttribute="singleInternalAccount" action="/SecureOnlineBanking/findInternalAccount">
				<table>
					<tr>
						<td><b>Employee Id<b/></td>
						<td><form:input path="employeeId"></form:input></td>
						<td><input type="submit" value="Find" class ="myButton" /></td>
					</tr>
				</table>
			</form:form>
		</div>
		<div style="background:#555; color:white; padding:10px;">
				<b>All Employee Accounts</b>
			</div>
<table id="tfhover" class="tftable" border="1">
	
	<thead>
		<tr>
		<th>EMPLOYEE ID</th>
			<th >USERNAME </th>
			<th >FIRSTNAME</th>
			<th>LASTNAME</th>
			<th>DEPT ID</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="item" items="${internalAccounts}">
			<tr>
			    <td>${item.employeeId}</td>
				<td>${item.username}</td>
				<td>${item.firstname}</td>
			    <td>${item.lastname}</td>
			    <td>${item.deptid}</td>
			  
				 
			    
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
<p style="text-align:center;">Go Back to <a class="myButton" href="/SecureOnlineBanking/internalUsersAccounts">Internal User Account Management</a></p>

<%@ include file="footer.jsp"%>