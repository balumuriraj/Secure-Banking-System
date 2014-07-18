<%@ include file="header.jsp"%>
<div
	style="width: 900px; margin: 0 auto; background: #fff; box-shadow: 0 1px 5px #000;">
	<div style="padding: 10px 30px 10px 30px;">

		<%@ include file="navigation.jsp"%>
		<br> <br>

		<h4>
			Logged in as:
			<sec:authentication property="name" />
			<sec:authentication property="authorities" />
		</h4>
		
		<div style="background: #555; color: white; padding: 10px;">
				<b>Customer Requests</b>
			</div>
			<br>

<div style="width:100%; margin:0 auto;">
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
			
				<td>
			 <form:form method="post" modelAttribute="accountbyid" action="authorizeUserRequest">
				
				<form:hidden path="employeeId"  />
				<input type="submit" value="Authorize" class ="myButton"/>
					
				
			</form:form>
			</td>
			</tr>
	</tbody>
</table>
</div>
</div></div>
<%@ include file="footer.jsp"%>
