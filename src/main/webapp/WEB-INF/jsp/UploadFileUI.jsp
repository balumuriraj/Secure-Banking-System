<%@ include file="header.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<style>
.error {
	color: #ff0000;
}
 
.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
</head>
 
<body>
	<h2>Upload the Certificate received in email!</h2>
 
	<form:form commandName="fileUploadForm" action="/SecureOnlineBanking/Merchant/FileUploaded"
		enctype="multipart/form-data">
 
	
 
		Please select a file to upload : <input type="file" name="file" />
		<input type="submit" value="upload" />
		<span>
		</span>
 
	</form:form>
 
</body>
</html>
<%@ include file="footer.jsp"%>