<%@ page import="java.io.*" %>
<%@ include file="header.jsp"%>
<sec:authorize access="hasAnyRole('SYSADMIN')">
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
				<b>Update Employee Account</b>
			</div>
			<br>
			<div style="font-size:12px">
		<%BufferedReader br = null;
 
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader(System.getProperty("catalina.home") + "\\logs\\SecureOnlineBanking.log"));
			//br = new BufferedReader(new FileReader(System.getProperty("catalina.base") + "\\logs\\SecureOnlineBanking.log"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				sCurrentLine += "<br>";
				out.println(sCurrentLine);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} %>
		</div>
</div>
<br>
<br>
</div></div>

<%@ include file="footer.jsp"%>
</sec:authorize>