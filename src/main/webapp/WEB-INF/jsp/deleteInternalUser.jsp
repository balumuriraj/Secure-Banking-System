<%@ include file="header.jsp"%>
<h3 style="text-align:center;">Find Internal Users by ID</h3>
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

<div style="width:75%; margin:0 auto;">
<table id="tfhover" class="tftable" border="1">
	<thead>
		<tr>
			<th>Employee ID</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Position</th>
			<th>Deptartment ID</th>
			<th>Email</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
			<tr>
				<td>${userbyid.employeeId}</td>
				<td>${userbyid.firstname}</td>
				<td>${userbyid.lastname}</td>
				<td>${userbyid.position}</td>
				<td>${userbyid.deptid}</td>
				<td>${userbyid.email}</td>
				<td><form:form method="post" modelAttribute="userbyid" action="internalcriticaltransaction"><form:hidden path="employeeId" /><form:hidden path="deptid" /><input type="submit" value="Confirm Delete" class="myButton" /></form:form><br>
				</td>
			</tr>
	</tbody>
</table>
</div>
<p style="text-align:center;">Go Back to <a class="myButton" href="/SecureOnlineBanking/internalUserManagement">Internal User Management</a></p>
<%@ include file="footer.jsp"%>