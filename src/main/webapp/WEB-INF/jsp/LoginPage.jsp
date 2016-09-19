<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isErrorPage="true" %>
<%@ page errorPage="ErrorPage.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="resources/images/favicon.ico" />
<link rel="stylesheet" href="resources/css/login.css" type="text/css">
<link rel="stylesheet" href="resources/css/materialize.min.css" type="text/css">
<title>Login Page</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>

<script type="text/javascript">
history.pushState(null, null, "loginPage");
window.addEventListener('popstate', function () {
    history.pushState(null, null, "loginPage");
});
</script>
</head>
<body style="background-color: #ebeff9;" ">

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
				<f:form id="loginForm" class="col s12" action="login" method="post" modelAttribute="userBean" >
					<div class="row">
						<div class="input-field ">
							<f:input type="text" id="username" cssClass="validate" path="x_LOGIN_NAME" /> 
							<label for="username">*Username</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field">
							<f:input type="password" id="password"  class="validate" path="x_PASSWORD"/>
							 <label for="password">*Password</label>
						</div>
						<div >
						<h6 id="message">Enter UserName And Password</h6>
						</div>
					</div>
					<div  class="center">
							<div id="loader_div"style="display: none">
			<div class="preloader-wrapper small active" >
			      <div class="spinner-layer spinner-blue">
			        <div class="circle-clipper left">
			          <div class="circle"></div>
			        </div><div class="gap-patch">
			          <div class="circle"></div>
			        </div><div class="circle-clipper right">
			          <div class="circle"></div>
			        </div>
			      </div>
			
			      <div class="spinner-layer spinner-red">
			        <div class="circle-clipper left">
			          <div class="circle"></div>
			        </div><div class="gap-patch">
			          <div class="circle"></div>
			        </div><div class="circle-clipper right">
			          <div class="circle"></div>
			        </div>
			      </div>
			
			      <div class="spinner-layer spinner-yellow">
			        <div class="circle-clipper left">
			          <div class="circle"></div>
			        </div><div class="gap-patch">
			          <div class="circle"></div>
			        </div><div class="circle-clipper right">
			          <div class="circle"></div>
			        </div>
			      </div>
			
			      <div class="spinner-layer spinner-green">
			        <div class="circle-clipper left">
			          <div class="circle"></div>
			        </div><div class="gap-patch">
			          <div class="circle"></div>
			        </div><div class="circle-clipper right">
			          <div class="circle"></div>
			        </div>
			      </div>
    </div>
	</div>
	<br>
					<button  class="btn waves-effect waves-light" value="Login" onclick="userLogin()"  >Login</button>
					</div>
				</f:form>
		
			</div>
			
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
	
</body>
<script src="resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
<script src="resources/js/materialize.min.js" type="text/javascript"></script>
<script type="text/javascript">
function userLogin(){
	 $("#loginForm").submit(function(event) {
			event.preventDefault();
			 /* get the action attribute from the <form action=""> element */
	      var $form = $( this ),
	          url = $form.attr( 'action' );
			 /* Send the data using post with element id name and name2*/
	     if(validate()){
	    	 $('#loader_div').show();
	    	 var posting = $.post( url+"?userName="+$('#username').val()+"&password="+$('#password').val());

		      /* Alerts the results */
		      posting.done(function( data ) {
		    	  if (data.toString()=='succsess'){
		                window.location.href="homepage";
		           	 } else {
		           		$('#message').css('color','red');
		        		$('#message').text(data.toString());
		           }
		    	  $('#loader_div').hide();
		      });
	     }
	    });
}

function validate(){
	$('#message').css('color','red');
	if($('#password').val()=='' && $('#username').val()==''){
		$('#message').text("UserName And Password Is Empty");
		return false;
	}else if($('#username').val()==''){
		$('#message').text("User Name Is Empty");
		return false;
	}else if($('#password').val()==''){
		$('#message').text("Password Is Empty");
		return false;
	}else{
		$('#message').css('color','black');
		$('#message').text("");
		return true;
	}
}
</script>
</html>
