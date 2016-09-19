
 <!-- style="border: 1px solid #d32f2f" -->
<div class="row" style="margin-bottom: 0px;">
	<div class="col l1" style="height:118px; width:142px">
		<img class="responsive-img"  alt="NPHCDA_LOGO" 
			   src="resources/images/NPHDALOGOFORHOME.png">
	</div>    
    <div class="col l8 center">
		<div class="row">
			<h5 id="common_lable" class="green-text text-darken-2" style="font-weight:600">N-LMIS</h5>
			<h6 id="user" class="teal-text text-darken-2" style="font-weight:600">User: </h6>
			<h6 id="warehouse_name" class="teal-text text-darken-2" style="font-weight:600">WAREHOUSE_NAME</h6>
		</div>
	</div>
    <div class="col l2 valign-wrapper" style="height: 118px; width:214px; line-height:25px">      	
		<div class="row valign valign-wrapper logout_div">			
			<a id="About_btn" href="#" title="About license">
				<img src="resources/images/About.png" alt="AboutLicenset-icon" onclick="showLicense()">
			</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a id="logout_btn" href="logOutPage?logOutFlag=logOut" title="Logout">
				<img src="resources/images/Logout.png" alt="logout-icon">
			</a>
			<div class="row">
				<span id="login_time">LOGIN_DATE-TIME</span>
			</div>
		</div>		
    </div>
</div>
<style>
 .logout_div{ 
 	padding-left: 10px; 
 }
.row {
    margin-bottom: 0px;
    margin-left: auto;
    margin-right: auto;
}
#logout_btn img:hover{
 width: 29%;
}
</style>