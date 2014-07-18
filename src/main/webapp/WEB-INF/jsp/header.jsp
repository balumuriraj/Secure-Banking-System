<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>

.error {
	color: #ff0000;
	font-size: 14px;
}

.dberrors {
	border: 1px solid red;
	color: red;
	padding: 10px;
}

body {
	width: 900px;
	font-family: Sans-serif;
	color: #555;
	background: #F8F8F8;
	margin: 0 auto;
}

textarea,select,input {
	padding-left: 5px;
	color: #555;
	height: 30px;
	background: white;
	font-size: 15px;
	font-weight: bold;
	border: 1px solid #aaa;
	border-radius: 2px;
	width: 100%;
}

label {
	font-size: 15px !important;
}

.radio {
	width: 20px;
	vertical-align: middle;
	margin: 5px;
}

.regform {
	border-spacing: 10px;
	margin: 0 auto;
	padding: 30px 50px;
	background: #fff;
	box-shadow: 0 1px 5px #000;
}

table {
	border-spacing: 10px;
}

.errorblock {
	color: #D8000C;
	background-color: #FFBABA;
}

.header {
	padding: 20px;
	background: FireBrick;
	color: white;
}

.heading {
	text-align: center;
	font-family: Sans-serif;
}

.myButton {
	-moz-box-shadow: inset 0px 1px 0px 0px #9acc85;
	-webkit-box-shadow: inset 0px 1px 0px 0px #9acc85;
	box-shadow: inset 0px 1px 0px 0px #9acc85;
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #74ad5a
		), color-stop(1, #68a54b));
	background: -moz-linear-gradient(top, #74ad5a 5%, #68a54b 100%);
	background: -webkit-linear-gradient(top, #74ad5a 5%, #68a54b 100%);
	background: -o-linear-gradient(top, #74ad5a 5%, #68a54b 100%);
	background: -ms-linear-gradient(top, #74ad5a 5%, #68a54b 100%);
	background: linear-gradient(to bottom, #74ad5a 5%, #68a54b 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#74ad5a',
		endColorstr='#68a54b', GradientType=0);
	background-color: #74ad5a;
	border: 1px solid #3b6e22;
	display: inline-block;
	color: #ffffff;
	font-family: arial;
	font-size: 13px;
	font-weight: bold;
	padding: 6px 12px;
	text-decoration: none;
}

.myButton:hover {
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #68a54b
		), color-stop(1, #74ad5a));
	background: -moz-linear-gradient(top, #68a54b 5%, #74ad5a 100%);
	background: -webkit-linear-gradient(top, #68a54b 5%, #74ad5a 100%);
	background: -o-linear-gradient(top, #68a54b 5%, #74ad5a 100%);
	background: -ms-linear-gradient(top, #68a54b 5%, #74ad5a 100%);
	background: linear-gradient(to bottom, #68a54b 5%, #74ad5a 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#68a54b',
		endColorstr='#74ad5a', GradientType=0);
	background-color: #68a54b;
}

.myButton:active {
	position: relative;
	top: 1px;
}

ul {
	margin-top: 0px;
	padding: 8px;
	list-style: none;
}

li {
	display: inline;
}

li a {
	background-color: #555;
	padding: 8px 15px;
	text-decoration: none;
	font-weight: bold;
	color: white;
}

li a:hover {
	color: white;
	background-color: black;
}


table.tftable {
	font-size: 15px;
	color: #333333;
	width: 100%;
	border-width: 1px;
	border-color: #729ea5;
	border-collapse: collapse;
}

table.tftable th {
	font-size: 15px;
	background-color: #acc8cc;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #729ea5;
	text-align: left;
}

table.tftable tr {
	background-color: #d4e3e5;
}

table.tftable td {
	font-size: 15px;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #729ea5;
}

</style>
<script type="text/javascript">
	window.onload = function() {
		var tfrow = document.getElementById('tfhover').rows.length;
		var tbRow = [];
		for (var i = 1; i < tfrow; i++) {
			tbRow[i] = document.getElementById('tfhover').rows[i];
			tbRow[i].onmouseover = function() {
				this.style.backgroundColor = '#ffffff';
			};
			tbRow[i].onmouseout = function() {
				this.style.backgroundColor = '#d4e3e5';
			};
		}
	};
</script>
<title>Sun Devil Bank of ASU</title>
</head>
<body viewsource="no">
	<h1 class="header">Sun Devil Bank of Arizona State</h1>
	<div style="background: #555; padding: 3px; margin-top: -25px"></div>