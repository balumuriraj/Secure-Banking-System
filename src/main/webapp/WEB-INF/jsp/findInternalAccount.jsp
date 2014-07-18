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
<table id="tfhover" class="tftable" border="1">
	<thead>
		<tr>
			<th >USERNAME </th>
			<th >FIRSTNAME</th>
			<th>LASTNAME</th>
			<th>DEPT ID</th>
		</tr>
	</thead>
	<tbody>
			<tr>
				<td>${accountbyid.username}</td>
				<td>${accountbyid.firstname}</td>
			    <td>${accountbyid.lastname}</td>
			    <td>${accountbyid.deptid}</td>
				  
			       
			         <td>
			 <form:form method="post" modelAttribute="accountbyid" action="updateInternalUserAccount">
				
				<form:hidden path="employeeId"  />
				<input type="submit" value="Update" class ="myButton"/>
					
				
			</form:form>
			</td>
			
			<td>
			 <form:form method="post" modelAttribute="accountbyid" action="viewInternalUserAccount">
				
				<form:hidden path="employeeId"  />
				<input type="submit" value="View" class ="myButton"/>
					
				
			</form:form>
			</td>
			</tr>
	</tbody>
</table>
</div>
<p style="text-align:center;">Go Back to <a class="myButton" href="/SecureOnlineBanking/internalUsersAccounts">Employee Account Management</a></p><%@ include file="footer.jsp"%>