<%@ include file="header.jsp"%>
<div style="width:100%; margin:0 auto;">
<table id="tfhover" class="tftable" border="1">
	<thead>
		<tr>
			<th >USERNAME </th>
			<th >FIRSTNAME</th>
			<th>LASTNAME</th>
			<th>DEPT ID</th>
			<th>POSITION</th>
			<th>STATUS</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="item" items="${internalAccounts}">
			<tr>
				<td>${item.username}</td>
				<td>${item.firstname}</td>
			    <td>${item.lastname}</td>
			    <td>${item.deptid}</td>
			    <td>${item.position}</td>
			    <td>${item.authorized}</td>
			     <td>   &nbsp;<a href="assignRoleInternalUserAccount?employeeId=${item.employeeId}">Assign Role</a></td>
			       <td> &nbsp;<a href="authorizeUserRequest?employeeId=${item.employeeId}">Authorise</a></td>
				 
			    
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
<p style="text-align:center;">Go Back to <a class="myButton" href="/SecureOnlineBanking/SystemAdmin">System Admin Home Page</a></p>

<%@ include file="footer.jsp"%>