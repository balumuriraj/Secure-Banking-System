<%@ include file="header.jsp"%>
<div
	style="width: 900px; margin: 0 auto; background: #fff; box-shadow: 0 1px 5px #000;">
	<div style="padding: 10px 30px 10px 30px;">
		<%@ include file="navigation.jsp"%>
		<br> <br>
		<div>
			<h4>
				Logged in as:
				<sec:authentication property="name" />
				<sec:authentication property="authorities" />
			</h4>
			<div style="background: #555; color: white; padding: 10px;">
				<b>View Customer Account</b>
			</div>
			<br>

			<table id="tfhover" class="tftable" border="1">
				<tr>
					<td>Username</td>
					<td>${user.username }</td>
				</tr>

				<tr>
					<td>First Name</td>
					<td>${user.firstname}</td>
				</tr>
				<tr>
					<td>Last Name</td>
					<td>${user.lastname}</td>
				</tr>

				<tr>
					<td>Date of Birth</td>
					<td>${user.dob}</td>
				</tr>


				<tr>
					<td>Address</td>
					<td>${user.address}</td>
				</tr>

				<tr>
					<td>Email</td>
					<td>${user.email}</td>
				</tr>

				<tr>
					<td>Phone</td>
					<td>${user.telephone}</td>
				</tr>

				<tr>
					<td>Type</td>
					<td>${user.type}</td>
				</tr>
			</table>
		</div>
		<br> <br>
	</div>
</div>
<%@ include file="footer.jsp"%>