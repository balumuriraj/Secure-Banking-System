<%@ include file="header.jsp"%>
<h3 style="text-align:center;">Company Meeting </h3>
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
		   <th>TRANSACTION ID</th>
			<th >EMPLOYEE ID</th>
			<th >TYPE</th>
			<th>DESCRIPTION</th>
			<th >STATUS</th>
		</tr>
	</thead>
	<tbody>
			<tr>
			    <td>${transactionbyid.transId}</td>
			    <td>${transactionbyid.employeeId}</td>
				<td>${transactionbyid.transType}</td>
				<td>${transactionbyid.description}</td>
				<td>${transactionbyid.status}></td>
				 <td> 
			 <form:form method="post" modelAttribute="transactionbyid" action="/SecureOnlineBanking/CompanyManagement/updateMeeting">
				
				<form:hidden path="transId"  />
				<input type="submit" value="Update" class ="myButton"/>
					
				
			</form:form>
			</td>
			
			<td>
			 <form:form method="post" modelAttribute="transactionbyid" action="/SecureOnlineBanking/CompanyManagement/deleteMeeting">
				
				<form:hidden path="transId"  />
				<input type="submit" value="Delete" class ="myButton"/>
					
				
			</form:form>
			</td>
			</tr>
	</tbody>
</table>
</div>
<p style="text-align:center;">Go Back to <a class="myButton" href="/SecureOnlineBanking/welcome"> Company Management Home Page</a></p>
<%@ include file="footer.jsp"%>