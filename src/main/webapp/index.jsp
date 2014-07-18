<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>

		<h2 class="heading">Welcome to Sun Devil Bank!</h2>

		<table class="regform">
			<tbody >

				<tr>
					<td><h2>If you have an account, Login here:</h2></td>

				</tr>
				<tr style="text-align:center">
					<td><a href="login" class="myButton" style="font-size: 30px;">Login</a></td>

				</tr>
				<tr>
					<td><br></td>

				</tr>
				<tr>
					<td><hr></td>

				</tr>
				<tr>
					<td><h2>If you don't have an account, Register here:</h2></td>

				</tr>
				<tr style="text-align:center">
					<td>
						<a href = "PassKey" class="myButton" style="font-size: 30px;">Register</a>
					</td>
				</tr>


			</tbody>
		</table>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>