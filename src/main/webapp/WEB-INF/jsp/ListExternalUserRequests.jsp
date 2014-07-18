<%@ include file="header.jsp"%>
<h3 style="text-align:center;">List of Internal User Accounts</h3>
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


function myFunction(i)
{
if(i==1)
	return "Authorized";
else
	return "Pending";
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
		    <th>TYPE</th>
			<th>STATUS</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="item" items="${externalAccounts}">
			<tr>a
				<td>${item.username}</td>
				<td>${item.firstname}</td>
			    <td>${item.lastname}</td>
			    <td>${item.type}</td>
	            <td>${item.authorized}</td>
			     <td>   &nbsp;<a href="assignTypeExternalUserAccount?username=${item.username}">Assign Type</a></td>
			       <td> &nbsp;<a href="authorizeExtUserRequest?username=${item.username}">Authorize</a></td>
				 
			    
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
<p style="text-align:center;">Go Back to <a class="myButton" href="/SecureOnlineBanking/SystemAdmin">System Admin Home Page</a></p>

<%@ include file="footer.jsp"%>