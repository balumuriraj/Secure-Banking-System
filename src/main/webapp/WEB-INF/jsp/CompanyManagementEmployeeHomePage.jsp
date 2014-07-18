<%@ include file="header.jsp"%>
<div style="width: 900px; margin: 0 auto; background:#fff; box-shadow: 0 1px 5px #000;">
	<div style="padding: 10px 30px 10px 30px;">
		<h2 style="text-align: center;">Welcome to Company Management Employee Home Page</h2>
		<div>
			<div style="background:#555; color:white; padding:10px;">
				<b>Find/Delete Meeting</b>
			</div>
			<br />
			
			<form:form method="post" modelAttribute="transaction" action="/SecureOnlineBanking/CompanyManagement/findMeeting">
				<table>
					<tr>
						<td><b>Transaction ID<b/></td>
						<td><form:input path="transId"></form:input></td>
						<td><input type="submit" value="Find" class ="myButton" /></td>
					</tr>
				</table>
			</form:form>
		</div>
		<br />
		
	
		<br />

		<hr>
		<div style="text-align: center;">
			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<a class ="myButton" href="/SecureOnlineBanking/CompanyManagement/displayMeetings">Display All Scheduled Meetings</a>
			</div>
			<div
				style="text-align: center; padding: 20px; display: inline-block;">
				<a class ="myButton" href="/SecureOnlineBanking/CompanyManagement/CompanyManagementTransaction">Schedule New Meeting</a>
			</div>
		</div>
	</div>
</div>

<p style="text-align:center;">Go Back to <a class="myButton" href="/SecureOnlineBanking/CompanyManagement"> Company Management Home Page</a></p>
<%@ include file="footer.jsp"%>