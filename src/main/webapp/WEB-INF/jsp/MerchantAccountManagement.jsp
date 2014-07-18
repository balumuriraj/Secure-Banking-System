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
		<div>
			<div style="background: #555; color: white; padding: 10px;">
				<b>Merchant Account Management</b>
			</div>
			<br />
			<div style="width: 100%; margin: 0 auto;">
				<table id="tfhover" class="tftable" border="1">
					<thead>
						<tr>
							<th>USERNAME</th>
							<th>ACCOUNT NO</th>
							<th>CURRENTBALANCE</th>

						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${accountbyname.username}</td>
							<td>${accountbyname.accountNo}</td>
							<td>${accountbyname.currentBalance}</td>


						</tr>

					</tbody>
				</table>
			</div>
		</div>
		<br>
		<br>
		<hr>
		<div style="text-align: center;">
			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<a class="myButton" href="merchantDebit">Debit</a>
			</div>

			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<a class="myButton" href="merchantCredit">Credit</a>
			</div>

			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<a class="myButton" href="merchantTransfer">Transfer</a>
			</div>

		</div>
	</div>
</div>
<%@ include file="footer.jsp"%>