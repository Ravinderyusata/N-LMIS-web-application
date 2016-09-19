<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<head>
<link rel="shortcut icon" type="image/x-icon" href="resources/images/favicon.ico" />
<link rel="  stylesheet" href="resources/easyui/themes/default/easyui.css" type="text/css">
<link rel=" stylesheet" href="resources/css/w3css.css" type="text/css">
<link rel="stylesheet" href="resources/easyui/themes/icon.css" type="text/css">
<link rel="stylesheet" href="resources/easyui/demo/demo.css" type="text/css">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<style type="text/css">
.datagrid-header .datagrid-cell span {
    font-size: 12px;
    font-weight: bold;
}
.logout_div{
padding-left: 10px;
}
#logout_btn img:hover,#home_btn img:hover{
 width: 105%;
 height: 105%;
}
.home_btn_div{
padding-right: 10px;
}
#home_btn{
  
}
#common_lable{
color: #578e3c  !important;
font-size: 173%;
font-weight: bold;
}
#warehouse_name,#user{
color: #578e3c  !important;
font-size: 130%;
font-weight: 600;
}
#common_lable,#warehouse_name,#user{
height: 8px;
 margin: 0px;
 padding: 0px;
}
.main-header-div{
margin: 0;
padding: 0;
box-shadow: 0px 0px 0px 1px #95b8e7;
}
.logodiv{
display: inline-block; 
width: 30%;
}
.logodiv img{
width: 55%;
}
.middle-div{
display: inline-block;
 width: 40%;
 text-align: center;
vertical-align: top;
}
.right-div{
display: inline-block;
    text-align: right;
    float: right;
}
.right-div{
    width: 10%;
    display: inline-flex;
    margin-top: 3%;
    margin-right: 3%;
}
</style>
</head>
<html>

<div  class="main-header-div">
	<div class="logodiv" >
		<img alt="leftimage" src="resources/images/NPHDALOGOFORHOME.png"
			style="height: 117px; width:142px" >
	</div>
	<div class="middle-div">
		<h5 id="common_lable" >N-LMIS</h5>
		 <br>
		  <h5 id="user" >User :</h5>
		  <br>
		<h5 id="warehouse_name" ></h5>
	</div>
	<div class="right-div">
			<h6 id="login_time" class=""></h6>
			<div class="home_btn_div">
			<a title="homepage" id="home_btn"   href="homepage">
			<img alt="home" src="resources/images/home.png"> </a>
			</div>	
			
			<div class="logout_div">
			<a title="Logout" href="logOutPage?logOutFlag=logOut"  id="logout_btn">
			<img alt="logout-icon" src="resources/images/Logout.png">
			</a>
			</div>
			
						
	</div>
</div>
</html>