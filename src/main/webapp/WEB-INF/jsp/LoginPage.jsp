<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="resources/css/login.css" type="text/css">
<link rel="stylesheet" href="resources/css/materialize.min.css" type="text/css">
<title>Login Page</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<style type="text/css">
</style>
<script type="text/javascript">
function showMessage() {
	var message='${errormessage}';
	document.getElementById("message").innerHTML=message;
	if(message.localeCompare("Wrong UserName And Password")=="0"){
		document.getElementById("message").style.color = "red";
	}else{
		document.getElementById("message").innerHTML=message="Enter UserName And Password";
	}
}
</script>
</head>
<body style="background-color: #ebeff9;" onload="showMessage()">
	<div id="mainDiv" class="row" >
		<div id="logindiv" style="size:auto; margin: 2%;padding-left: 10%;" class="row">
			<div id="loginPageImage"  class="col s6">
				<img   alt="loginPageImage"
					src="resources/images/NPHDA_LOGO-1_400x400.png">
			</div>
			<div class="col s6 z-depth-1" id="loginform">
				<div  title="nlmis-heading" id="nlmis-heading">
					<h4>Logistics Stock Management Information System</h4>
					<hr style="width: inherit;">
				</div>
				<f:form class="col s12" action="login" method="post" modelAttribute="userBean">
					<div class="row">
						<div class="input-field ">
							<f:input type="text" id="username" cssClass="validate" path="x_LOGIN_NAME" /> 
							<label for="username">Username</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field">
							<f:input type="password" id="password"  class="validate" path="x_PASSWORD"/>
							 <label for="password">Password</label>
						</div>
						<div >
						<h6 id="message"></h6>
						</div>
					</div>
					<div  class="center">
					<input type="submit" class="btn waves-effect waves-light" value="Login" style="padding-top: 9px;" >
					</div>
				</f:form>
			</div>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
<script src="resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
<script src="resources/js/materialize.min.js" type="text/javascript"></script>
</html>
