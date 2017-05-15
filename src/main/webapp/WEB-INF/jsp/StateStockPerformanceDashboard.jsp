<%@ page isELIgnored="false"%>
<head>
<script src="resources/easyui/jquery.easyui.min.js"></script>
<link type="text/css" href="resources/easyui/themes/default/easyui.css" rel="stylesheet">
<style>
#table_div table{
border-collapse: inherit;
border: 1px solid black;
}
#table_div table tr td{
border: 1px solid black;
padding: 0px;
}
.l-btn {
    background: rgba(0, 0, 0, 0) linear-gradient(to bottom, #ffffff 0px, #eeeeee 100%) repeat-x scroll 0 0;
    border: 1px solid #bbb;
    border-radius: 5px;
    color: #444;
}
.l-btn {
/*    cursor: pointer;  */
   display: inline-block;  
/*      line-height: normal;  */
/*     margin: 0;    outline: medium none;  */
/*     overflow: hidden;  */
/*    padding: 0;  */
/*    text-align: center;  */
/*      text-decoration: none;  */
/*      vertical-align: middle;  */
}

.red-status {
	width: 10px;
	height: 10px;
	background: red;
	display: inline-block;
}
.green-status {
	width: 10px;
	height: 10px;
	background: green;
	display: inline-block;
}
.yellow-status {
	background: yellow none repeat scroll 0 0;
    display: inline-block;
    height: 10px;
    width: 10px;
}
h6 {
    font-weight: normal;
    line-height: 1.1;
}
</style>
</head>
<body>
	<div>
		<!-- filters -->
		<div class="row" style="padding: 0px 12;line-height: 3">
			<div class="col l3" style="width:18%">				
				<div id='lga_combobox_div'>
					<span id='lga_label_span'>LGA:</span>
					<select id="lga_combobox" class="easyui-combobox" name="lga_combobox" style="width: 200px;"></select>	
				</div>
			</div>
			<div class="col l9">		
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span>Year:</span> 
				<select id="year_combobox" class="easyui-combobox" name="year combobox" style="width: 100px;"></select>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span>Week:</span> 
				<select id="week_combobox" class="easyui-combobox" name="week_combobox" style="width: 100px;"></select>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a id="viewDashboardLinkBtn" class="easyui-linkbutton" href="#" 
					onclick="filterStockDashboardGridData('get_state_stock_perfo_dashboard_data','lga_combobox', 'year_combobox','week_combobox')">
					View Dashboard</a> 
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a id="exportLinkBtn" class="easyui-linkbutton" href="get_state_stock_perfo_dashboard_export">Export</a>
				<!-- filters end here -->
			</div>
			<div class="row" style="margin-bottom: 0px">
				<!-- FOR TEST : border color of table_div - #95d0b7 -->
				<hr style="width: 100%;float: left;color: #f8f8f8;">
				<div id="table_div" class="col l10" style="overflow-y:auto;height:61%; ">
					<!-- dynamic row created and inserted here... -->
				</div>
			
				<div class="col l2">
					<!--FOR TESTING: parent div's border color-> style="border: 1px solid #881818;" -->
<!-- 					<ul style="line-height: 35px;;margin-top:0"> -->
<!-- 						<li>&nbsp;<div class="red-status"></div>&nbsp;&nbsp;&nbsp;&nbsp;% LGA With >3 -->
<!-- 							Antigen in red</li> -->
<!-- 						<li>&nbsp;<div class="green-status"></div>&nbsp;&nbsp;&nbsp;&nbsp;% LGA With no -->
<!-- 							Antigen in red</li> -->
<!-- 						<li>&nbsp;<div class="yellow-status"></div>&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 							<span style="float: right;line-height: 34px; text-align: left; width: 197px"> -->
<!-- 								% LGA With Antigen that need to &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 								&nbsp;&nbsp;re-order Stock -->
<!-- 							</span> -->
<!-- 						</li> -->
<!-- 						<li><img alt="" height="7px;" width="30px;" -->
<!-- 							src="resources/images/Arrow.png" class="same_trend_image"> -->
<!-- 							&nbsp;Trend is the same</li> -->
<!-- 						<li><img alt="" height="7px;" width="25px" -->
<!-- 							src="resources/images/Arrow.png" class="decres_trend_image"> -->
<!-- 							&nbsp;&nbsp;Decreasing Trend</li> -->
<!-- 						<li><img alt="" height="7px" width="25px" -->
<!-- 							src="resources/images/Arrow.png" class="incres_trend_image"> -->
<!-- 							&nbsp;&nbsp;Increasing Trend</li> -->
<!-- 					</ul> -->
					<ul style="line-height: 35px; margin-top: 0;">
						<li>
							 <div>
							 <span class="red-status"></span> 
							<span>% of Antigens below minimum level in LGA</span>
							 </div>
						</li>
	
						<li>
							<div>
							 <span class="green-status"></span> 
							<span> % of Sufficient Antigens in LGA</span>
							</div>
						</li>
	
						<li>
							<div>
							 <span class="yellow-status"></span>
							<span >
								% of Antigens at reorder level in LGA 
							</span>
							</div>
						</li>
						<li>
							<span style="padding-left: 13px;">
								<img width="30px;" height="7px;" alt="" src="resources/images/Arrow.png" class="same_trend_image">
							</span>
							<span> Trend is the same</span>
						</li>
						<li>
							<img width="25px" height="7px;" class="decres_trend_image" src="resources/images/Arrow.png" alt="">
							&nbsp;&nbsp;Decreasing Trend
						</li>
						<li>
							<img width="25px" height="7px" class="incres_trend_image" src="resources/images/Arrow.png" alt="">
							&nbsp;&nbsp;Increasing Trend
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
function showTableData(url) {
	document.getElementById("loader_div").style.display = "block";
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			document.getElementById("loader_div").style.display = "none";
			var ss = JSON.parse(xhttp.responseText);
			loaddata(ss);
			if(($('#lga_combobox').combobox('getText') === "All") || ($('#lga_combobox').combobox('getText') === "ALL") || ('${userBean.x_ROLE_NAME}'==='LIO')){
				$('#lgaStockPerformanceDashboardHeaderDiv').css('display','none');
// 				$('#lga_combobox_div').hide();
			}else{
				$('#lgaStockPerformanceDashboardHeaderDiv').css('display','block');
//				$('#table_div .row .col .l8').css('width','865px');
//				$('#table_div .row .col .l8 .row').css('width','863px');
			}
		}
	};
	xhttp.open("POST", url, true);
	xhttp.send();
}
	$('#year_combobox').combobox({
		url : 'get_year_list',
		valueField : 'value',
		textField : 'label',
		onSelect : function(rec) {
			$('#week_combobox').combobox({
				url : 'get_week_list/week?yearParam='+ rec.value,
				valueField : 'value',
				textField : 'label'
			});

		}
	});
	$('#lga_combobox').combobox({
		url : 'getlgalist?option=All',
		valueField : 'value',
		textField : 'label',
		onLoadError:function(data){
			window.location.href="logOutPage?logOutFlag=logOut";
		} 
	});
	/* Do not delete below code - IMPORTANT */
	$('#week_combobox').combobox({});
	$('#viewDashboardLinkBtn').linkbutton({});
	$('#exportLinkBtn').linkbutton({});
	if(('${userBean.x_ROLE_NAME}' === "LIO") || ('${userBean.x_ROLE_NAME}' === "MOH")){
		$('#lga_combobox').hide();
	}
</script>
<style>
/* VERY IMPORTANT - DON'T REMOVE BELOW CSS STYLE */
.row .col {
    box-sizing: border-box;
    float: left;
    padding: 0 0;
}
</style>