<%@ include file="header.jsp"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form:form method="post" commandName="command" action="/SecureOnlineBanking/transactionMonitoringEmployee/updateInternalUserTransaction.html">
 
 <table>
    
    <tr>
        <td><form:label path="employeeId">Employee Id</form:label></td>
        <td><form:input path="employeeId" /></td> 
    </tr>
    <tr>
        <td><form:label path="transType">Transaction Type</form:label></td>
        <td><form:input path="transType" /></td>
    </tr>
     <tr>
        <td><form:label path="description">Description </form:label></td>
        <td><form:input path="description" /></td>
    </tr>
    <tr>
    <form:hidden path="transId"  />
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="Update Internal Transaction">
        </td>
    </tr>
</table> 
 
     
</form:form>

<p style="text-align:center;">Go Back to <a class="myButton" href="/SecureOnlineBanking/transactionMonitoringEmployee/internalUsersTransactions">Internal User Transaction Management</a></p>
</body>
</html>
<%@ include file="footer.jsp"%>