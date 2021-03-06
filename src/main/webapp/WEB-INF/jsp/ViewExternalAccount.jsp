<%@ include file="header.jsp"%>

<div
	style="width: 900px; margin: 0 auto; background: #fff; box-shadow: 0 1px 5px #000;">
	<div style="padding: 10px 30px 10px 30px;">
		<%@ include file="navigation.jsp"%>
		<br> <br>
		<div style="">

			<h4>
				Logged in as:
				<sec:authentication property="name" />
				<sec:authentication property="authorities" />
			</h4>
			<div style="background: #555; color: white; padding: 10px;">
					<b>View Account</b>
				</div>
				<br>

				<table id="tfhover" class="tftable" border="1">
					<tr>
						<td>Username</td>
						<td><b>${user.username}</b></td>
					</tr>

					<tr>
						<td>First Name</td>
						<td><b>${user.firstname}</b></td>
					</tr>
					<tr>
						<td>Last Name</td>
						<td><b>${user.lastname}</b></td>
					</tr>

					<tr>
						<td>Date of Birth </td>
						<td><b>${user.dob}</b></td>
					</tr>


					<tr>
						<td>Address</td>
						<td><b>${user.address}</b></td>
					</tr>

					<tr>
						<td>Email</td>
						<td><b>${user.email}</b></td>
					</tr>

					<tr>
						<td>Phone</td>
						<td><b>${user.telephone}</b></td>
					</tr>

				</table>
		</div>
		<br> <br>
	</div>
</div>

<%@ include file="footer.jsp"%>