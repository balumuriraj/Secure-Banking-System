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
				<b>Find/Modify Employee Account</b>
			</div>
			<br />


			<div style="width: 100%; margin: 0 auto;">
				<table id="tfhover" class="tftable" border="1">
					<thead>
						<tr>

							<th>Type</th>
							<th>Description</th>
							<th>Amount</th>
							<th>Status</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${transactions}">
							<tr>

								<td>${item.transType}</td>
								<td>${item.transDetail}</td>
								<td>${item.amountInvolved}</td>
								<td>${item.status}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<br>
		<br>
	</div>
</div>
<%@ include file="footer.jsp"%>