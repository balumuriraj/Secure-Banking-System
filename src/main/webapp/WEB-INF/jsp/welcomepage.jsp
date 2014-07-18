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

			<sec:authorize
				access="hasAnyRole('CEO', 'PRESIDENT', 'VICEPRESIDENT')">
				<h2
					style="padding: 150px 20px; text-align: center; background-color: #f2f2f2; color: #069;">Welcome
					to Employee Management and Transaction Management!</h2>
			</sec:authorize>

			<sec:authorize access="hasAnyRole('MANAGER')">
				<c:choose>
					<c:when test="${currentuser.deptid == '1'}">
						<h2
							style="padding: 150px 20px; text-align: center; background-color: #f2f2f2; color: #069;">Welcome
							to Sales Employee Management and Transaction Management!</h2>
					</c:when>
					<c:when test="${currentuser.deptid == '2'}">
						<h2
							style="padding: 150px 20px; text-align: center; background-color: #f2f2f2; color: #069;">Welcome
							to IT Employee Management and Transaction Management!</h2>
					</c:when>
					<c:when test="${currentuser.deptid == '3'}">
						<h2
							style="padding: 150px 20px; text-align: center; background-color: #f2f2f2; color: #069;">Welcome
							to Transaction Monitoring Employee Management and Transaction
							Management!</h2>
					</c:when>
					<c:when test="${currentuser.deptid == '4'}">
						<h2
							style="padding: 150px 20px; text-align: center; background-color: #f2f2f2; color: #069;">Welcome
							to HR Employee Management and Transaction Management!</h2>
					</c:when>
					<c:when test="${currentuser.deptid == '5'}">
						<h2
							style="padding: 150px 20px; text-align: center; background-color: #f2f2f2; color: #069;">Welcome
							to Company Management Employee Management and Transaction
							Management!</h2>
					</c:when>
				</c:choose>
			</sec:authorize>

			<sec:authorize access="hasAnyRole('EMPLOYEE')">
				<c:choose>
				<c:when test="${currentuser.deptid == '1'}">
					<h2
						style="padding: 150px 20px; text-align: center; background-color: #f2f2f2; color: #069;">Welcome
						Sales Employee!</h2>
				</c:when>
				<c:when test="${currentuser.deptid == '2'}">
					<h2
						style="padding: 150px 20px; text-align: center; background-color: #f2f2f2; color: #069;">Welcome
						IT Employee!</h2>
				</c:when>
				<c:when test="${currentuser.deptid == '3'}">
					<h2
						style="padding: 150px 20px; text-align: center; background-color: #f2f2f2; color: #069;">Welcome
						Transaction Monitoring Employee!</h2>
				</c:when>
				<c:when test="${currentuser.deptid == '4'}">
					<h2
						style="padding: 150px 20px; text-align: center; background-color: #f2f2f2; color: #069;">Welcome
						HR Employee!</h2>
				</c:when>
				<c:when test="${currentuser.deptid == '5'}">
					<h2
						style="padding: 150px 20px; text-align: center; background-color: #f2f2f2; color: #069;">Welcome
						Company Management Employee!</h2>
				</c:when>
					</c:choose>
			</sec:authorize>

			<sec:authorize access="hasAnyRole('INDIVIDUAL')">
				<h2
						style="padding: 150px 20px; text-align: center; background-color: #f2f2f2; color: #069;">Welcome
						Individual User!</h2>
			</sec:authorize>

			<sec:authorize access="hasAnyRole('MERCHANT')">
				<h2
						style="padding: 150px 20px; text-align: center; background-color: #f2f2f2; color: #069;">Welcome
						Merchant!</h2>
			</sec:authorize>

			<sec:authorize access="hasAnyRole('SYSADMIN')">
				<h2
						style="padding: 150px 20px; text-align: center; background-color: #f2f2f2; color: #069;">Welcome System Administrator!</h2>
			</sec:authorize>

		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>