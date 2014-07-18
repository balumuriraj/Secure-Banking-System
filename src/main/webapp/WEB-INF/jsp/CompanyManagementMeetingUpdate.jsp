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
				<b>UpdateScheduled Meeting</b>
			</div>
			<br>

			<form:form method="post" modelAttribute="internalTransaction"
				action="/SecureOnlineBanking/CompanyManagement/updateCompanyMeeting.html">
				<c:if test="${not empty errors}">

					<p class="dberrors">${errors}</p>

				</c:if>

				<table>

					<tr>
						<td><form:label path="employeeId">Employee Id</form:label></td>
						<td><form:input path="employeeId" readonly="true" /></td>
					</tr>
					<tr>
						<td><form:label path="transType">Transaction Type</form:label></td>
						<td><form:input path="transType" readonly="true" /></td>
					</tr>
					<tr>
						<td><form:label path="description">Description </form:label></td>
						<td><form:input path="description" /></td>
					</tr>
					<tr>
						<form:hidden path="transId" />
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Update Meeting"
							class="myButton"></td>
					</tr>
				</table>


			</form:form>
		</div>
		<br>
		<br>
	</div>
</div>
<%@ include file="footer.jsp"%>