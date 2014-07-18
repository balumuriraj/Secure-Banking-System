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
				<b>Create Customer Transaction</b>
			</div>
			<br>
			<form:form method="post" modelAttribute="exttrans"
				action="addExternalUserTransaction.html">

				<c:if test="${not empty errors}">
					<tr>
						<td colspan="3"><p class="dberrors">${errors}</p></td>
					</tr>
				</c:if>
				<table>

					<tr>
						<td><form:label path="userId">User Id</form:label></td>
						<td><form:input path="userId" type="number" /></td>
					</tr>
					<tr>
						<td><form:label path="transType">Transaction Type</form:label></td>
						<td><form:select path="transType">
								<form:option value="Debit" label="Debit" />
								<form:option value="Credit" label="Credit" />
								<form:option value="Transfer" label="Transfer" />
							</form:select></td>
					</tr>
					<tr>
						<td><form:label path="transDetail">Description </form:label></td>
						<td><form:input path="transDetail" /></td>
					</tr>
					<tr>
						<td><form:label path="amountInvolved">Amount Involved </form:label></td>
						<td><form:input path="amountInvolved" type="number" /></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Create Transaction"
							class="myButton"></td>
					</tr>
				</table>


			</form:form>
		</div>
		<br> <br>

	</div>
</div>
<%@ include file="footer.jsp"%>