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


			<div style="width: 100%; margin: 0 auto;">
				<%-- <div>
					<div style="background: #555; color: white; padding: 10px;">
						<b>Find/Delete External User Transaction</b>
					</div>
					<br />

					<form:form method="post" modelAttribute="singleEntry"
						action="/SecureOnlineBanking/transactionMonitoringEmployee/findExternalTransaction">
						<table>
							<tr>
								<td><b>Transaction ID<b /></td>
								<td><form:input path="transId"></form:input></td>
								<td><input type="submit" value="Find" class="myButton" /></td>
							</tr>
						</table>
					</form:form>
				</div> --%>
				<div style="background: #555; color: white; padding: 10px;">
					<b>Manage Customer Transactions</b>
				</div>
				<br>
				<table id="tfhover" class="tftable" border="1">
					<thead>
						<tr>
							<th>Transaction Id</th>
							<th>Type</th>
							<th>Description</th>
							<th>Status</th>
							<th>Amount</th>
							<th colspan="3">Actions</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${transactions}">
							<tr>

								<td>${item.transId}</td>
								<td>${item.transType}</td>
								<td>${item.transDetail}</td>
								<td>${item.status}</td>
								<td>${item.amountInvolved}</td>
								<td><c:set target="${transactionbyid}" property="transId"
										value="${item.transId}" /> <form:form method="post"
										modelAttribute="transactionbyid"
										action="/SecureOnlineBanking/transactionMonitoringEmployee/updateexternaltransaction">
										<form:hidden path="transId" />
										<input type="submit" value="Update" class="myButton" />
									</form:form></td>

								<td>
								<c:choose>
								<c:when test="${item.status == 'Approved'}">
											Cannot Delete
										</c:when>
										<c:otherwise>
								<c:set target="${transactionbyid}" property="transId"
										value="${item.transId}" /> <form:form method="post"
										modelAttribute="transactionbyid"
										action="/SecureOnlineBanking/transactionMonitoringEmployee/deleteexternaltransaction">
										<form:hidden path="transId" /><form:hidden path="transType" /><form:hidden path="transDetail" /><form:hidden path="status" /><form:hidden path="amountInvolved" />
										<input type="submit" value="Delete" class="myButton" />
									</form:form>
									</c:otherwise></c:choose>
									</td>

								<td><c:choose>
										<c:when test="${item.status == 'Approved'}">
											Authorized
										</c:when>
										<c:otherwise>
											<c:set target="${transactionbyid}" property="transId"
												value="${item.transId}" />
											<form:form method="post" modelAttribute="transactionbyid"
												action="/SecureOnlineBanking/transactionMonitoringEmployee/authorizeexternaltransaction">
												<form:hidden path="transId" />
												<input type="submit" value="Authorize" class="myButton" />
											</form:form>
										</c:otherwise>
									</c:choose></td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<br> <br>
	</div>
</div>
<%@ include file="footer.jsp"%>