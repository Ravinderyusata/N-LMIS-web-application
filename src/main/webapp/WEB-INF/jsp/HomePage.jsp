<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html id="homepageHtmlElement" style="font-size:13px">
<head>
<link rel="shortcut icon" type="image/x-icon" href="resources/images/favicon.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="resources/css/materialize.min.css"	type="text/css">

<script src="resources/js/jquery-2.2.3.min.js"></script>
<script src="resources/js/materialize.min.js"></script>
<script src="resources/js/common.js"></script>

<title>N-LMIS</title>
<style>
nav, nav .nav-wrapper i, nav a.button-collapse, nav a.button-collapse i {
    height: 33px;
    line-height: 33px;
}
.dropdown-content li {
	min-height: 25px;
}
.dropdown-content li > a, .dropdown-content li > span {
    color: #26a69a;
    display: block;
    font-size: 16px;
    line-height: 22px;
    padding: 7px 16px;
}
.preloader-wrapper.big {
    height: 100px;
    left: 42%;
    top: 50%;
    width: 100px;
}
.loader_div {
	height: 100%;
	width: 100%;
	position: absolute;
	overflow: overlay;
	opacity: 0.5;
	z-index: 1000;
	top: 0%;
}
.loader {
	border: 16px solid #f3f3f3;
	border-radius: 50%;
	border-top: 16px solid blue;
	border-bottom: 16px solid blue;
	top: 42%;
	left: 43%;
	z-index: 1;
	width: 120px;
	height: 120px;
	position: absolute;
	-webkit-animation: spin 2s linear infinite;
	animation: spin 1s linear infinite;
}

.loader_div_for_iframe {
	height: 84%;
	width: 100%;
	position: absolute;
	overflow: overlay;
	opacity: 0.5;
	z-index: 1000;
	top: 16%;
}
.loader_circle{
	border: 8px solid #f3f3f3;
	border-radius: 50%;
	border-top: 8px solid blue;
	border-bottom: 8px solid blue;
	top: 42%;
	left: 43%;
	z-index: 1;
	width: 120px;
	height: 120px;
	position: absolute;
	-webkit-animation: spin 2s linear infinite;
	animation: spin 1s linear infinite;
}
@-webkit-keyframes spin {
  0% { -webkit-transform: rotate(0deg); }
  100% { -webkit-transform: rotate(360deg); }
}
@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
.decres_trend_image{
-ms-transform: rotate(90deg); /* IE 9 */
    -webkit-transform: rotate(90deg); /* Safari */
    transform: rotate(90deg);
}
.incres_trend_image{
-ms-transform: rotate(270deg); /* IE 9 */
    -webkit-transform: rotate(270deg); /* Safari */
    transform: rotate(270deg);
}
#license_modal p{
line-height: 1;
font-family: arial;
font-stretch: expanded;
}
</style>

<script type="text/javascript">
history.pushState(null, null, "homepage");
window.addEventListener('popstate', function () {
history.pushState(null, null, "homepage");
});	
</script>
<script type="text/javascript">
$(document).ready(function(){
	$('.tabs .tab').css('text-transform', 'none');
});
      
	var reloadDashboards=true;
	$(document).ready(function(){		
// 		$('#mainHomePageDiv').show();		
		$(".dropdown-button").dropdown({ hover: true });
		$("#stockDashboardTabsUL .indicator").css('height','5px');
		$("#ntoStockDashboardTabsUL .indicator").css('height','5px');
		$("#lioMohStockDashboardTabsUL .indicator").css('height','5px');
		var user = '${userdata.getX_ROLE_NAME()}';
		$('#user').text('User: '+user+' ${userdata.getX_WAREHOUSE_NAME()}');
		$('#login_time').text('${login_time}');
		if((user === 'SIO') || (user==='SIFP')){
			user = 'SCCO';
		}else if(user === 'MOH'){
			user = 'LIO';
		}
		switch (user) {
			case "SCCO":
				$('#warehouse_name').text('State: ${userdata.getX_WAREHOUSE_NAME()}');	
				$('#ntoStockDashboardTabsUL').hide();
				$('#lioMohStockDashboardTabsUL').hide();
				$('#stockDashboardDropdown li:gt(5)').hide();
				$('#reportsDropdown li:gt(10)').hide();
				if(reloadDashboards){
					/* Below ajax request will run when user log-in(By-Default screen!) */
					var defaultDashboardPageUrl = $("#stockDashboardTabsUL a").filter(".active").attr('name');
// 					var lgaStockSummaryDashboardPageUrl = $("#stockDashboardTabsUL li:eq(1) > a").attr('name');
					var hfStockSummarySheetDashboardPageUrl = $("#stockDashboardTabsUL li:eq(2) > a").attr('name'); // "hf_stock_summary_sheet_page"
					var lgaStockBalanceDashboardPageUrl = $("#stockDashboardTabsUL li:eq(1) > a").attr('name'); // "hf_stock_summary_sheet_page"
					
					var defaultDashboardTabdivID = $("#stockDashboardTabsUL a").filter(".active").attr('href');
// 					var lgaStockSummaryDashboardTabDivId = $("#stockDashboardTabsUL li:eq(1) > a").attr('href');
					var hfStockSummaryDashboardTabDivId = $("#stockDashboardTabsUL li:eq(2) > a").attr('href');
					var lgaStockbalenceDashboardTabDivId = $("#stockDashboardTabsUL li:eq(1) > a").attr('href');
					
					var stateStockPerfoDashboardDataUrl = "get_state_stock_perfo_dashboard_data?year="+new Date().getFullYear()+"&week="+${PREVIOUS_WEEK_OF_YEAR}+"&lga_id=null";
// 					var lgaStockSummaryDashboardDataUrl = "get_lga_stock_summary_grid_data?year="+new Date().getFullYear()+"&week="+${PREVIOUS_WEEK_OF_YEAR}+"&stateId=${userdata.x_WAREHOUSE_ID}";
					var hfStockSummarySheetDashboardDataUrl = "get_hf_stock_summary_grid_data?year="+new Date().getFullYear()+"&week="+${PREVIOUS_WEEK_OF_YEAR}+"&lgaId=null&lgaName=null";
					var lgaStockBalanceDashboardDataUrl = "get_lga_stock_summary_grid_data?year="+new Date().getFullYear()+"&week="+${PREVIOUS_WEEK_OF_YEAR}+"&stateId=${userdata.x_WAREHOUSE_ID}";
					document.getElementById("loader_div").style.display = "block";
					$('ul.stockDashboardTabsUL').tabs('select_tab', 'stockDashboardLiTab1');
					ajaxGetRequest(defaultDashboardPageUrl, function(data){
						$(defaultDashboardTabdivID).html(data);
				    /* 	if('${loadCount}'==='1'){
				    		showTableData(stateStockPerfoDashboardDataUrl);
				    	}else{
				    		document.getElementById("loader_div").style.display = "none";
				    	} */ 
				    	document.getElementById("loader_div").style.display = "none";
				    	$('#lga_combobox').combobox('setValue','null');
				    	$('#lga_combobox').combobox('setText','All');
				    	$('#year_combobox').combobox('setValue',new Date().getFullYear());
				    	$('#year_combobox').combobox('setText',new Date().getFullYear());
				    	$('#week_combobox').combobox({
							url : 'get_week_list/week?yearParam='+(new Date().getFullYear()),
							valueField : 'value',
							textField : 'label'
						});
				    	$('#week_combobox').combobox('setValue','${PREVIOUS_WEEK_OF_YEAR}');
				    	$('#week_combobox').combobox('setText','${PREVIOUS_WEEK_OF_YEAR}');
					});
// 						ajaxGetRequest(lgaStockSummaryDashboardPageUrl, function(data){
// 							$(lgaStockSummaryDashboardTabDivId).html(data);
// 		// 			    	loadHeadingTable3('${userdata.x_WAREHOUSE_ID}','${userdata.x_WAREHOUSE_NAME}');
// 		// 			    	showTableData3(lgaStockSummaryDashboardDataUrl);
// 					    	$('#state_combobox3_div').css('display','none');
// //						    	$('#year_combobox3').combobox('setValue',new Date().getFullYear());
// //						    	$('#year_combobox3').combobox('setText',new Date().getFullYear());
// //						    	$('#week_combobox3').combobox('setValue',(getWeekNumber(new Date())-1));
// //						    	$('#week_combobox3').combobox('setText',(getWeekNumber(new Date())-1));
// 						});
						
						ajaxGetRequest(hfStockSummarySheetDashboardPageUrl, function(data){
							$(hfStockSummaryDashboardTabDivId).html(data);
		// 			    	loadHeadingTable4('${userdata.x_WAREHOUSE_ID}','${userdata.x_WAREHOUSE_NAME}');
		// 			    	showTableData4(hfStockSummarySheetDashboardDataUrl);
		// 			    	$('#lga_combobox_div4').css('display','none');
//						    	$('#year_combobox4').combobox('setValue',new Date().getFullYear());
//						    	$('#year_combobox4').combobox('setText',new Date().getFullYear());
//						    	$('#week_combobox4').combobox('setValue',(getWeekNumber(new Date())-1));
//						    	$('#week_combobox4').combobox('setText',(getWeekNumber(new Date())-1));
						});
						ajaxGetRequest(lgaStockBalanceDashboardPageUrl, function(data){
							$(lgaStockbalenceDashboardTabDivId).html(data);
							$('#stateStoreFilter').hide();
						});				
					
					/* Above ajax request ends) */
					reloadDashboards=false;
				}
								
				/* NavBar-DropDowns UL elements Id */
				/* $('#administrationDropdown').show();
				$('#productsDropdown').show();
				$('#stockManagementDropdown').show();
				$('#reportsDropdown').show();
				$('#stockDashboardDropdown').show(); */
				
				/* NavBar-DropDowns LI elements Id */			
				/* $('#usersLiElement').show();
				$('#lgaStoreLiElement').show();
				$('#pHFLiElement').show();
				
				$('#productsLiElement').show();
				$('#deviceAssociatonLiElement').show();
				
				$('#physicalStockBalanceLiElement').show();
				$('#transactionHistoryLiElement').show();
				
				$('#wastageReportLiElement').show();
				$('#emergencyStockReportLiElement').show();
				$('#binCardReportLiElement').show();
				$('#stockAdjustmentReportLiElement').show();
				$('#min-maxReportLiElement').show();
				$('#hfStockSummarySheetLiElement').show(); */
				
				/* $('#stateStockStatusDashboardLiElement').show();
				$('#lgaStockPerformDashboardLiElement').show();
				$('#lgaSummarySheetLiElement').show();
				$('#hfStockSummarySheetLiElement').show(); */
				$("#stockDashboardTabsUL .indicator").css('height','5px');
				break;
			case "SIO":			
				$('#warehouse_name').text('State: ${userdata.getX_WAREHOUSE_NAME()}');			
				
				break;
			case "SIFP":
				$('#warehouse_name').text('State: ${userdata.getX_WAREHOUSE_NAME()}');			
				
				break;
			case "NTO":
				$('#warehouse_name').text('National: ${userdata.getX_WAREHOUSE_NAME()}');			
				//$('#stockManagementNavigationUL').hide();
				$('#stockDashboardTabsUL').hide();
				$('#lioMohStockDashboardTabsUL').hide();
				$('#stockDashboardDropdown li:lt(6), #stockDashboardDropdown li:gt(9)').hide();
				$('#reportsDropdown li:gt(10)').hide();
				$('#reportsDropdown li').eq(4).hide();
				$('#productsDropdown li').eq(2).hide();
				$("#ntoStockDashboardTabsUL .indicator").css('height','5px');
				if(reloadDashboards){
					/* Below ajax request will run when user log-in(By-Default screen!) */
					var defaultDashboardPageUrl = $("#ntoStockDashboardTabsUL a").filter(".active").attr('name');
// 	 				alert("NTO LGA Aggregated-defaultDashboardPageUrl: "+defaultDashboardPageUrl);
// 	 				console.log("NTO LGA Aggregated-defaultDashboardPageUrl: ", defaultDashboardPageUrl);
// 					var lgaStockSummaryDashboardPageUrl = $("#ntoStockDashboardTabsUL li:eq(1) > a").attr('name');
//	 				alert("NTO LGA Stock Summary- lgaStockSummaryDashboardPageUrl: "+lgaStockSummaryDashboardPageUrl);
					var lgaStockBalanceDashboardPageUrl = $("#ntoStockDashboardTabsUL li:eq(1) > a").attr('name'); // "hf_stock_summary_sheet_page"
					
					var defaultDashboardTabdivID = $("#ntoStockDashboardTabsUL a").filter(".active").attr('href');
//	 				alert("NTO defaultDashboardTabdivID: "+defaultDashboardTabdivID);
// 					var lgaStockSummaryDashboardTabDivId = $("#ntoStockDashboardTabsUL li:eq(1) > a").attr('href');			
//	 				alert("NTO lgaStockSummaryDashboardTabDivId: "+lgaStockSummaryDashboardTabDivId);
					var lgaStockbalenceDashboardTabDivId = $("#ntoStockDashboardTabsUL li:eq(1) > a").attr('href');
//	 				alert("Current Week: "+getWeekNumber(new Date())+", Currrent-- : "+(getWeekNumber(new Date())-1));
					
					var stateStockStatusLgaAggDashboardDataUrl = "get_lga_agg_stock_dashboard_data?year="+new Date().getFullYear()+"&week="+${PREVIOUS_WEEK_OF_YEAR};
//	 				alert("NTO stateStockStatusLgaAggDashboardDataUrl: "+stateStockStatusLgaAggDashboardDataUrl);
// 					var lgaStockSummaryDashboardDataUrl = "get_lga_stock_summary_grid_data?year="+new Date().getFullYear()+"&week="+${PREVIOUS_WEEK_OF_YEAR}+"&stateId=null";
					var lgaStockBalanceDashboardDataUrl = "get_lga_stock_balance_dashbaord_data?year="+new Date().getFullYear()+"&week="+${PREVIOUS_WEEK_OF_YEAR}+"&stateId=null";
//	 				alert("NTO lgaStockSummaryDashboardDataUrl: "+lgaStockSummaryDashboardDataUrl);
					document.getElementById("loader_div").style.display = "block";
					ajaxGetRequest(defaultDashboardPageUrl, function(data){
//					    	alert("STATE STOCK STATUS DASHBOARD SUCCESS : "+data);		    	
				    	$(defaultDashboardTabdivID).html(data);
						$('#lgaStockAggregatedLinkNTO').on('click',function() {
			    			$(this).css({'background':'#F39814', 'color':'white','font-weight':'bold'});
			    			$('#lgaStockPerformanceLinkNTO').css({'background':'#f9f9f9','color':'black','font-weight':'bold'});
			    		});
			    		$('#lgaStockPerformanceLinkNTO').on('click',function() {
				    		$(this).css({'background':'#F39814', 'color':'white','font-weight':'bold'});
				    		$('#lgaStockAggregatedLinkNTO').css({'background':'#f9f9f9','color':'black','font-weight':'bold'});
				    	});
				    /* 	if('${loadCount}'==='1'){
//					    		background: #F39814; color: white;
				    		$('#lgaStockAggregatedLinkNTO').css({'background':'#F39814', 'color':'white'});
				    		loadLgaAggStockDataNTO(stateStockStatusLgaAggDashboardDataUrl);
				    	}else{
				    		document.getElementById("loader_div").style.display = "none";
				    	} */
				    	document.getElementById("loader_div").style.display = "none";
				    	$('#state_combobox').combobox('setValue','null');
				    	$('#state_combobox').combobox('setText','All');
				    	$('#year_comboboxNTO').combobox('setValue',new Date().getFullYear());
				    	$('#year_comboboxNTO').combobox('setText',new Date().getFullYear());
				    	$('#week_comboboxNTO').combobox({
							url : 'get_week_list/week?yearParam='+(new Date().getFullYear()),
							valueField : 'value',
							textField : 'label'
						});
				    	$('#week_comboboxNTO').combobox('setValue','${PREVIOUS_WEEK_OF_YEAR}');
				    	$('#week_comboboxNTO').combobox('setText','${PREVIOUS_WEEK_OF_YEAR}');
				    });
					
// 					ajaxGetRequest(lgaStockSummaryDashboardPageUrl, function(data){
//					    	alert("NTO LGA STOCK SUMMARY DASHBOARD SUCCESS : "+data);		    	
// 				    	$(lgaStockSummaryDashboardTabDivId).html(data);
				    	/* When LGA STOCK SUMMARY DASHBOARD clicked */
// 						$("state_combobox3_div").css('display','block');
// 						loadStateCombobox3();
//	 				    	$('#state_combobox3').combobox('setValue','All');
//	 				    	$('#state_combobox3').combobox('setText','All');
//					    	$('#year_combobox3').combobox('setValue',new Date().getFullYear());
//					    	$('#year_combobox3').combobox('setText',new Date().getFullYear());
//					    	$('#week_combobox3').combobox('setValue',(getWeekNumber(new Date())-1));
//					    	$('#week_combobox3').combobox('setText',(getWeekNumber(new Date())-1));
// 				    });
					
					
					ajaxGetRequest(lgaStockBalanceDashboardPageUrl, function(data) {
						$(lgaStockbalenceDashboardTabDivId).html(data);
						loadStateCombobox5();
					});

								reloadDashboards = false;
				}
				break;
			case "LIO":
				$('#warehouse_name').text('LGA: ${userdata.getX_WAREHOUSE_NAME()}');
				$('#ntoStockDashboardTabsUL').hide();
				//$('#stockManagementNavigationUL').hide();
				$('#stockDashboardTabsUL').hide();
				$('#stockDashboardDropdown li:lt(11)').hide();
				$('#reportsDropdown li:lt(6)').hide();
				if (reloadDashboards) {
					/* Below ajax request will run when user log-in(By-Default screen!) */
					var defaultDashboardPageUrl = $("#lioMohStockDashboardTabsUL a").filter(".active").attr('name');
					var hfStockSummarySheetDashboardPageUrl = $("#lioMohStockDashboardTabsUL li:eq(1) > a").attr('name'); // "hf_stock_summary_sheet_page"
					var defaultDashboardTabdivID = $("#lioMohStockDashboardTabsUL a").filter(".active").attr('href');
					var hfStockSummaryDashboardTabDivId = $("#lioMohStockDashboardTabsUL li:eq(1) > a").attr('href');
					var stateStockPerfoDashboardDataUrl = "get_state_stock_perfo_dashboard_data?year="+ new Date().getFullYear()+ "&week="+ ${PREVIOUS_WEEK_OF_YEAR}
					+"&lga_id=${userdata.x_WAREHOUSE_ID}";
// 					alert("stateStockPerfoDashboardDataUrl: "+stateStockPerfoDashboardDataUrl);
					var hfStockSummarySheetDashboardDataUrl = "get_hf_stock_summary_grid_data?year="
							+ new Date().getFullYear()+ "&week="+ ${PREVIOUS_WEEK_OF_YEAR}
							+"&lgaId=${userdata.x_WAREHOUSE_ID}&lgaName=${userdata.x_WAREHOUSE_NAME}";
					document.getElementById("loader_div").style.display = "block";
					ajaxGetRequest(defaultDashboardPageUrl,function(data) {
								$(defaultDashboardTabdivID).html(data);
								/* if('${loadCount}'==='1'){
									showTableData(stateStockPerfoDashboardDataUrl);
								}else{
									document.getElementById("loader_div").style.display = "none";
								} */
								document.getElementById("loader_div").style.display = "none";
	//					    	$('#lga_label_span').hide();
	//							$('#lga_combobox').hide();
								$('#lga_combobox_div').hide();
								$('#lga_combobox').combobox('setValue','${userdata.x_WAREHOUSE_ID}');
								$('#lga_combobox').combobox('setText','${userdata.x_WAREHOUSE_NAME}');
								$('#year_combobox').combobox('setValue',new Date().getFullYear());
								$('#year_combobox').combobox('setText',new Date().getFullYear());
								$('#week_combobox').combobox({
									url : 'get_week_list/week?yearParam='+(new Date().getFullYear()),
									valueField : 'value',
									textField : 'label'
								});
								$('#week_combobox').combobox('setValue','${PREVIOUS_WEEK_OF_YEAR}');
								$('#week_combobox').combobox('setText','${PREVIOUS_WEEK_OF_YEAR}');
							});
					ajaxGetRequest(hfStockSummarySheetDashboardPageUrl,function(data) {
								$(hfStockSummaryDashboardTabDivId).html(data);
	//					    	loadHeadingTable4('${userdata.x_WAREHOUSE_ID}','${userdata.x_WAREHOUSE_NAME}');
	//					    	showTableData4(hfStockSummarySheetDashboardDataUrl);
								$('#lga_combobox_div4').css('display', 'none');
	//					    	$('#year_combobox4').combobox('setValue',new Date().getFullYear());
	//					    	$('#year_combobox4').combobox('setText',new Date().getFullYear());
	//					    	$('#week_combobox4').combobox('setValue',(getWeekNumber(new Date())-1));
	//					    	$('#week_combobox4').combobox('setText',(getWeekNumber(new Date())-1));
							});
					/* Above ajax request ends) */
					reloadDashboards = false;
				}
				break;
			case "MOH":
				$('#warehouse_name').text('LGA: ${userdata.getX_WAREHOUSE_NAME()}');
				break;
			}

	/* Below handler will run when Menu-Items(Navigation Menu-Dropdowns) clicked */
		$("#stockDashboardDropdown a").on("click", function(e) {
			elementId = ('#' + this.name);
			stockDashboardTabsUL = '#stockDashboardTabsUL';
			if ((this.id === '7') || (this.id === '9')) {
				alert("this.id="+(this.id));
				stockDashboardTabsUL = '#ntoStockDashboardTabsUL';
			} else if ((this.id === '11') || (this.id === '13')) {
				stockDashboardTabsUL = '#lioMohStockDashboardTabsUL';
			}
			var clickableTab = $(stockDashboardTabsUL+" a[href='"+elementId+"']").attr('id');
			/* $("#stockDashboardTabsUL a[href='"+elementId+"']").attr('class','active'); */
			/* $(elementId).css('display','block'); */
			// 			alert("Element ID = "+$("#stockDashboardTabsUL a[href='"+elementId+"']").attr('id')+", HREF = "+$("#stockDashboardTabsUL a[href='"+elementId+"']").attr('href'));
			// 			alert("clicked url '"+$(this).attr('href')+"', this.name = "+elementId);			
			e.preventDefault(); // cancel the link itself
			if ($(this).attr('href') !== '#!' || $(this).attr('href') !== '#') {
				if (reloadDashboards) {
// 					$.get(this.href,function(data) {
// //	 					alert(elementId+", response: "+data);						
// 						$(elementId).html(data);
// 					    $("#"+clickableTab).click();
// 					    if(('${userdata.getX_ROLE_NAME()}' === 'SCCO') && ($("#stockDashboardTabsUL .active").attr('id') === 'stockDashboardLiTab3Link')){
// 							/* When LGA STOCK SUMMARY DASHBOARD clicked */
// 							$('#state_combobox3_div').css('display','none');

// 						}else if(('${userdata.getX_ROLE_NAME()}' === 'NTO') && ($("#stockDashboardTabsUL .active").attr('id') === 'stockDashboardLiTab3Link')){
// 							/* When LGA STOCK SUMMARY DASHBOARD clicked */
// 							$('#state_combobox3_div').css('display','block');
// 						}else if(('${userdata.getX_ROLE_NAME()}' === 'NTO') && ($(stockDashboardTabsUL+" .active").attr('id') === 'ntoStockDashboardLiTab2Link')){
// 							/* When LGA STOCK SUMMARY DASHBOARD clicked */
// 							$('#state_combobox3_div').css('display','block');
// 						}							    
// 					});	
				} else {
					$("#" + clickableTab).click();
					if (('${userdata.getX_ROLE_NAME()}' === 'SCCO') && ($("#stockDashboardTabsUL .active").attr('id') === 'stockDashboardLiTab3Link')) {
						/* When LGA STOCK SUMMARY DASHBOARD clicked */
						$('#state_combobox3_div').css('display','none');
					} else if (('${userdata.getX_ROLE_NAME()}' === 'NTO') && ($(stockDashboardTabsUL+" .active").attr('id') === 'ntoStockDashboardLiTab5Link')) {
						/* When LGA STOCK SUMMARY DASHBOARD clicked */
						$('#state_combobox3_div').css('display','block');
					}
				}
			}
		});
		$('#iframe').hide();
	});

	function showLicense() {
		$('#license_modal').openModal();
	}
	function showDashBoardDivAndHideIframe() {
		$('#mainHomePageDiv').show();
		$('#iframe').hide();
	}
	function showIframeAndHideDashBoardDiv(action) {
		$('#loader_for_iframe').show();
		$('#mainHomePageDiv').hide();
		$('#iframe').show();
		$('#iframe').attr('src', "");
		$('#iframe').attr('src', action);

		document.getElementById("iframe").onload = function() {
			$('#loader_for_iframe').hide();
		};

	}
</script>
</head>

<body>

	<jsp:include page="Header.jsp"></jsp:include>
	<%! int loadCount=0; %>
	<% loadCount =(int)request.getSession().getAttribute("loadCount");
		loadCount++;
		request.getSession().setAttribute("loadCount",loadCount);
	%>
	<!--Administration - Dropdown Structure -->
	<ul id="administrationDropdown" class="dropdown-content" style="">
		<li><a href="#"  onclick="showIframeAndHideDashBoardDiv('userpage')">Users</a></li>
		<li class="divider"></li>
		<li><a href="#" onclick="showIframeAndHideDashBoardDiv('lgastorepage')">LGA Stores</a></li>
		<li class="divider"></li>
		<li><a href="#" onclick="showIframeAndHideDashBoardDiv('primaryhealthfacilitypage')">Primary Health Facilities</a></li>
	</ul>
	
	<!--Products - Dropdown Structure -->
	<ul id="productsDropdown" class="dropdown-content" style="">
		<li><a href="#" onclick="showIframeAndHideDashBoardDiv('show_product_main_grid')">Products Overview</a></li>
		<li class="divider"></li>
		<li><a href="#" onclick="showIframeAndHideDashBoardDiv('device_association_detail')">Device Association</a></li>
		<!-- <li class="divider"></li>
		<li><a href="device_association_detail">Product Categories</a></li> -->
	</ul>
	
	<!--Stock Management - Dropdown Structure -->
	<ul id="stockManagementDropdown" class="dropdown-content">
		<li><a href="#" onclick="showIframeAndHideDashBoardDiv('item_onhand_grid')">Physical Stock Balance</a></li>
		<li class="divider"></li>
		<li><a href="#" onclick="showIframeAndHideDashBoardDiv('transaction_register_page')">Transaction History</a></li>
		<!-- <li class="divider"></li> 
		<li><a href="#!">LGA Stock Issue To Facility</a></li> --> 
	</ul>
	
	<!--Reports - Dropdown Structure -->
	<ul id="reportsDropdown" class="dropdown-content">		
		<li><a href="#" onclick="showIframeAndHideDashBoardDiv('lga_emergency_stock_issue_report_page')">LGA Emergency Stock Issued Report</a></li>
		<li class="divider"></li>
		<li><a href="#" onclick="showIframeAndHideDashBoardDiv('lga_min_max_page')">LGA MIN/MAX Report</a></li>
		<li class="divider"></li>
		<li><a href="#" onclick="showIframeAndHideDashBoardDiv('lga_Inconsistency_report_page')">LGA Stock Inconsistency Report</a></li>
		<li class="divider"></li>
		
		<li><a href="#" onclick="showIframeAndHideDashBoardDiv('lga_bin_card_page')">LGA Bin Cards</a></li> <!-- I=6 -->
		<li class="divider"></li>
		<li><a href="#" onclick="showIframeAndHideDashBoardDiv('lga_stock_adjustment_report_page')">LGA Stock Adjustment Report</a></li>
		<li class="divider"></li>
		<li><a href="#" onclick="showIframeAndHideDashBoardDiv('lga_wastage_report_page')">LGA Wastage Report</a></li>
		<li class="divider"></li>
		<li><a href="#" onclick="showIframeAndHideDashBoardDiv('hf_wastage_report_page')">HF Wastage Report</a></li> <!-- I=12  -->
		<li class="divider"></li>
		<li><a href="#" onclick="showIframeAndHideDashBoardDiv('hf_emergency_allocation_report_page')">HF Emergency Stock Allocation</a></li>
		<li class="divider"></li>
		<li><a href="#" onclick="showIframeAndHideDashBoardDiv('hf_bin_card_page')">HF Bin Cards</a></li>
		<li class="divider"></li>
		<li><a href="#" onclick="showIframeAndHideDashBoardDiv('hf_min_max_stock_level_report_page')">HF Min./Max. Stock Balance Report</a></li>
		<li class="divider"></li>
	</ul>
	
	<!--SCCO - Stock Dashboard - Dropdown Structure -->
	<ul id="stockDashboardDropdown" class="dropdown-content">
		<li id="0"><a href="state_stock_perfo_dashboard" onclick="showDashBoardDivAndHideIframe()" name="stockDashboardTab1View">State Stock Performance Dashboard</a></li>
		<li id="1" class="divider"></li>
		<li><a id="2" href="lga_stock_balance_dashboard_page" onclick="showDashBoardDivAndHideIframe()" name="stockDashboardTab5View">LGA Stock Summary</a></li>
<!-- 		<li id="2"><a href="lga_stock_summary_grid" onclick="showDashBoardDivAndHideIframe()" name="stockDashboardTab3View">LGA Stock Summary</a></li> -->
		<li id="3" class="divider"></li>
		<li id="4"><a href="hf_stock_summary_sheet_page" onclick="showDashBoardDivAndHideIframe()" name="stockDashboardTab4View">HF Stock Summary Sheet</a></li>
		<li id="5" class="divider"></li>
<!-- 		<li><a id="6" href="lga_stock_balance_dashboard_page" onclick="showDashBoardDivAndHideIframe()" name="stockDashboardTab5View">LGA Stock Balance Dashboard</a></li> -->
		<li><a id="7" href="state_Stock_Status_DashboardPage" onclick="showDashBoardDivAndHideIframe()" name="stockDashboardTab1View">State Stock Status Dashboard</a></li>
		<li id="8" class="divider"></li>
		<li><a id="9" href="lga_stock_balance_dashboard_page" onclick="showDashBoardDivAndHideIframe()" name="stockDashboardTab5View">LGA Stock Summary</a></li>
<!-- 		<li><a id="9" href="lga_stock_summary_grid" onclick="showDashBoardDivAndHideIframe()" name="stockDashboardTab3View">LGA Stock Summary</a></li> -->
		<li id="10" class="divider"></li>
		<li><a id="11" href="state_stock_perfo_dashboard" onclick="showDashBoardDivAndHideIframe()" name="stockDashboardTab1View">LGA Stock Performance Dashboard</a></li>
		<li id="12" class="divider"></li>
		<li><a id="13" href="hf_stock_summary_sheet_page" onclick="showDashBoardDivAndHideIframe()" name="stockDashboardTab3View">HF Stock Summary Sheet</a></li>
		
	</ul>
	
	<!--SCCO - About - Dropdown Structure -->
	<ul id="aboutDropdown" class="dropdown-content">
		<li id="0"><a href="#" name="stockDashboardTab1View" onclick="showLicense()">License</a></li>
	</ul>
	
	<nav class="#1b5e20 green darken-1">
		<div class="nav-wrapper">
			<!-- <a href="#!" class="brand-logo">Logo</a> -->
			<ul class="right hide-on-med-and-down">
				<!--Administration - Dropdown Trigger -->
				<li>
					<a class="dropdown-button" href="#!" data-activates="administrationDropdown" data-beloworigin="true" data-constrainwidth="false">
						Administration<i class="material-icons right">arrow_drop_down</i>
					</a>
				</li>
				<!--Products - Dropdown Trigger -->
				<li>
					<a class="dropdown-button" href="#!" data-activates="productsDropdown" data-beloworigin="true" data-constrainwidth="false">
						Products<i class="material-icons right">arrow_drop_down</i>
					</a>
				</li>
				<!--Stock Management - Dropdown Trigger -->
				<li id="stockManagementNavigationUL">
					<a class="dropdown-button" href="#!" data-activates="stockManagementDropdown" data-beloworigin="true" data-constrainwidth="false">
						Stock Management<i class="material-icons right">arrow_drop_down</i>
					</a>
				</li>
				<!--Reports - Dropdown Trigger -->
				<li>
					<a class="dropdown-button" href="#!" data-activates="reportsDropdown" data-beloworigin="true" data-constrainwidth="false">
						Reports<i class="material-icons right">arrow_drop_down</i>
					</a>
				</li>
				<!--Stock Dashboard - Dropdown Trigger -->
				<li>
					<a class="dropdown-button" href="#!" data-activates="stockDashboardDropdown" data-beloworigin="true" data-constrainwidth="false">
						Stock Dashboard<i class="material-icons right">arrow_drop_down</i>
					</a>
				</li>
				
			</ul>
		</div>
	</nav>

	<!-- Below div, By default display's Stock Dashboard as home-page-default-view -->
	<div id="mainHomePageDiv" class="row">
	    <div id="stockDashboardULTabsDiv" class="col l12">
	      <ul id="stockDashboardTabsUL" class="tabs" style="border-color: #95d0b7; height:5%">
	        <li id="stockDashboardLiTab1" class="tab col l4"><a id="stockDashboardLiTab1Link"  href="#stockDashboardTab1View" name="state_stock_perfo_dashboard" >State Stock Performance Dashboard</a></li>
	        <li id="stockDashboardLiTab5" class="tab col l4"><a id="stockDashboardLiTab5Link" href="#stockDashboardTab5View" name="lga_stock_balance_dashboard_page">LGA Stock Summary Sheet</a></li> <!-- Actual: Lga Stock Balance Dashboard -->
	        <li id="stockDashboardLiTab4" class="tab col l4"><a id="stockDashboardLiTab4Link" href="#stockDashboardTab4View" name="hf_stock_summary_sheet_page">HF Stock Summary Sheet</a></li> 
<!-- 		    <li id="stockDashboardLiTab3" class="tab col l4"><a id="stockDashboardLiTab3Link" href="#stockDashboardTab3View" name="lga_stock_summary_grid">Lga Stock Balance Dashboard</a></li> Actual: LGA Stock Summary Sheet -->
	      </ul>
	      <ul id="ntoStockDashboardTabsUL" class="tabs" style="border-color: #95d0b7; height:7%">
<!-- 	      	default dashboard data action - get_lga_agg_stock_dashboard_data -->
	        <li id="ntoStockDashboardLiTab1" class="tab col l6"><a id="ntoStockDashboardLiTab1Link" href="#stockDashboardTab1View" name="state_Stock_Status_DashboardPage">State Stock Status Dashboard</a></li>
<!-- 	        <li id="ntoStockDashboardLiTab3" class="tab col l6"><a id="ntoStockDashboardLiTab3Link" href="#stockDashboardTab3View" name="lga_stock_summary_grid">LGA Stock Summary Sheet</a></li> -->
	         <li id="stockDashboardLiTab5" class="tab col l4"><a id="ntoStockDashboardLiTab5Link" href="#stockDashboardTab5View" name="lga_stock_balance_dashboard_page">LGA Stock Summary Sheet</a></li>
	      </ul>
	      <ul id="lioMohStockDashboardTabsUL" class="tabs" style="border-color: #95d0b7; height:7%">
<!-- 	      	default dashboard data action - state_stock_perfo_dashboard : LGA dropdown hidden | default warehouse id passed is logged in LGA's ID -->
	        <li id="lioMohStockDashboardLiTab1" class="tab col l6"><a id="lioMohStockDashboardLiTab1Link" href="#stockDashboardTab1View" name="state_stock_perfo_dashboard">LGA Stock Performance Dashboard</a></li>
	        <li id="lioMohStockDashboardLiTab3" class="tab col l6"><a id="lioMohStockDashboardLiTab3Link" href="#stockDashboardTab3View" name="hf_stock_summary_sheet_page">HF Stock Summary Sheet</a></li>
	      </ul>
	    </div>
	    <!-- For NTO/SCCO/SIO/SIFP -->
		<div id="stockDashboardTab1View" class="col l12">State Stock Performance Dashboard</div> <!-- action = state_stock_perfo_dashboard -->
<!-- 		<div id="stockDashboardTab2View" class="col l12">LGA Stock Performance Dashboard</div> action = N/A -->
		<div id="stockDashboardTab5View" class="col l12">LGA Stock Summary Sheet</div> <!-- action = N/A -->
		<div id="stockDashboardTab4View" class="col l12">HF Stock Summary Sheet</div> <!-- action = N/A -->
<!-- 	<div id="stockDashboardTab3View" class="col l12">LGA Stock Summary Sheet  </div> action = lga_stock_summary_grid --> <!-- LGA Stock Balance Dashboard -->
		<!-- For LIO/MOH
		<div id="stockDashboardTab3View" class="col s12">LGA Stock Summary</div>
		<div id="stockDashboardTab4View" class="col s12">HF Stock Summary Sheet</div> -->
		
	   <!-- For NTO 
		<div id="stockDashboardTab3View" class="col s12">LGA Stock Summary</div> action = lga_stock_summary_grid
		<div id="stockDashboardTab1View" class="col s12">State Stock Status Dashboard</div> -->	 
		
		
		
    </div>
     <iframe id="iframe" src=""  height="75%" width="100%"></iframe> 
	<!-- loader div -->
<!-- 	<div style="display: none;" id="loader_div" class="loader_div"> -->
<!-- 		<div class="loader" id="loader_show"></div> -->
<!-- 	</div> -->
	<div id="loader_div" style="display: none" class="loader_div">
		<div id="loader_show" class="preloader-wrapper big active">
			<div class="spinner-layer spinner-blue">
				<div class="circle-clipper left">
					<div class="circle"></div>
				</div>
				<div class="gap-patch">
					<div class="circle"></div>
				</div>
				<div class="circle-clipper right">
					<div class="circle"></div>
				</div>
			</div>

			<div class="spinner-layer spinner-red">
				<div class="circle-clipper left">
					<div class="circle"></div>
				</div>
				<div class="gap-patch">
					<div class="circle"></div>
				</div>
				<div class="circle-clipper right">
					<div class="circle"></div>
				</div>
			</div>

			<div class="spinner-layer spinner-yellow">
				<div class="circle-clipper left">
					<div class="circle"></div>
				</div>
				<div class="gap-patch">
					<div class="circle"></div>
				</div>
				<div class="circle-clipper right">
					<div class="circle"></div>
				</div>
			</div>

			<div class="spinner-layer spinner-green">
				<div class="circle-clipper left">
					<div class="circle"></div>
				</div>
				<div class="gap-patch">
					<div class="circle"></div>
				</div>
				<div class="circle-clipper right">
					<div class="circle"></div>
				</div>
			</div>
		</div>
	</div>

	<!-- loader div for ifram-->
<!-- 	<div style="display: none;" id="loader_for_iframe" class="loader_div_for_iframe"> -->
<!-- 		<div class="loader_circle" id="loader_show"></div> -->
<!-- 	</div> -->
	<div id="loader_for_iframe" style="display: none" class="loader_div_for_iframe">
		<div id="loader_show" class="preloader-wrapper big active">
			<div class="spinner-layer spinner-blue">
				<div class="circle-clipper left">
					<div class="circle"></div>
				</div>
				<div class="gap-patch">
					<div class="circle"></div>
				</div>
				<div class="circle-clipper right">
					<div class="circle"></div>
				</div>
			</div>

			<div class="spinner-layer spinner-red">
				<div class="circle-clipper left">
					<div class="circle"></div>
				</div>
				<div class="gap-patch">
					<div class="circle"></div>
				</div>
				<div class="circle-clipper right">
					<div class="circle"></div>
				</div>
			</div>

			<div class="spinner-layer spinner-yellow">
				<div class="circle-clipper left">
					<div class="circle"></div>
				</div>
				<div class="gap-patch">
					<div class="circle"></div>
				</div>
				<div class="circle-clipper right">
					<div class="circle"></div>
				</div>
			</div>

			<div class="spinner-layer spinner-green">
				<div class="circle-clipper left">
					<div class="circle"></div>
				</div>
				<div class="gap-patch">
					<div class="circle"></div>
				</div>
				<div class="circle-clipper right">
					<div class="circle"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal Structure For license -->
  <div id="license_modal" class="modal modal-fixed-footer" >
    <div class="modal-content" >
      <p><i>Copyright (c) <2016>, National Primary Health Care Development Agency, Nigeria
		All rights reserved.</i></p>
		
		<p><i>Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following 
		conditions are met:</i></p>
		 
		<p><i>1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.</i></p>
		
		<p><i>2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in
		 the documentation and/or other materials provided with the distribution.</i></p>
        
		 <p><i>
		  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
		  BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
		  EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
		  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
		  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, 
		  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
		  POSSIBILITY OF SUCH DAMAGE.</i></p>
    </div>
    
  </div>
</body>

<style>
.tabs {
	/* In easy-ui.css */
    border-style: solid;
    border-width: 0 0 1px;
    height: 26px;
    list-style-type: none;
    margin: 0;
    padding: 0 0 0 0px;
    width: 50000px;
}
.tabs .tab {
line-height: 35px;
}
.tabs {
	/* In material-min.css */
    background-color: #fff;
    display: flex;
    height: 48px;
    margin: 0 auto;
    /* overflow-x: auto; */
    /* overflow-y: hidden; */
    position: relative;
    white-space: nowrap;
    width: 100%;
}
</style>
<script src="resources/js/stockdashboards.js"></script>
</html>