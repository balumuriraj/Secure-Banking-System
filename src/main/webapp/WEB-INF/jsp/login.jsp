<%@ include file="header.jsp"%>

<h1 class="heading">Login page</h1>



<form method="post" action="<c:url value='j_spring_security_check' />">
	<table class="regform">
		<tbody>
			<tr>
				<td>
					<form method="post"
						action="<c:url value='j_spring_security_check' />">
						<table class="">
							<tbody>
								<c:if test="${not empty message}">
									<tr>
										<td colspan="3"><p class="dberrors">${message}</p></td>
									</tr>
								</c:if>
								<tr>
									<td><h3
											style="color: white; background: #555; padding: 5px; text-align: center">Login</h3></td>
								</tr>
								<tr>
									<td><input type="text" name="j_username" id="j_username"
										size="30" maxlength="40" placeholder="username" /></td>
								</tr>
								<tr>
									<td><input type="password" name="j_password"
										id="j_password" size="30" maxlength="32"
										placeholder="password" /></td>
								</tr>
								
								
								
								<tr>
									<td><input type="submit" value="Login" class="myButton" /></td>
								</tr>

							</tbody>
						</table>

					</form>
				</td>

			</tr>
			<tr>
				<td style="text-align: center;"><a href="/SecureOnlineBanking/ForgetPwd">Did you forget password?</a></td>
			</tr>
			<tr>
				<td style="text-align: center;"><a href="/SecureOnlineBanking/">GO
						to Home Page</a></td>
			</tr>

		</tbody>
	</table>

</form>

<%@ include file="footer.jsp"%>