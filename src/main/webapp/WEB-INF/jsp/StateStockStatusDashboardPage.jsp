<head>
<link type="text/css" href="resources/easyui/themes/default/easyui.css" rel="stylesheet">
<script type="text/javascript" src="resources/easyui/jquery.easyui.min.js"></script>
<style>
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
.loader_div {
	height: 100%;
	width: 100%;
	position: absolute;
	overflow: overlay;
	opacity: 0.5;
	z-index: 2;
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
#filters span {
font-weight: bold;
}
#table_divNTO table {
border: 1px solid black;
}
#table_divNTO table tr td{
border: 3px solid black;
}
</style>
</head>
<body>
	<div style="padding: 0px 12;line-height: 3">
		<!-- filters -->
		<div style="padding: 0px 12;line-height: 3">
			<span id="state_comboboxNTO_label">State Store:</span> 
			<select id="state_comboboxNTO" class="easyui-combobox" name="state_combobox" style="display:none;width:200px;">
			</select> 
			<span>Year:</span> <select id="year_comboboxNTO" class="easyui-combobox" name="year combobox" style="width: 100px;">
			</select> 
			<span>Week</span> <select id="week_comboboxNTO" class="easyui-combobox" name="week_combobox" style="width: 100px;">
			</select> 
			<a id="lgaStockPerformanceLinkNTO" href="#" class="easyui-linkbutton" onclick="filterGridDataNTO()">LGA Stock Performance Dashboard</a>
			<a id="lgaStockAggregatedLinkNTO" href="#" class="easyui-linkbutton" onclick="lgaStockAggregatedDataNTO()">LGA Aggregated Stock Performance Dashboard</a>
			<a id="exportLinkBtnNTO" href="get_state_stock_status_dashboard_export" class="easyui-linkbutton">Export</a>
		</div>
		<!-- filters end here -->
		<hr width="100%;">
		<div class="row" style="margin-bottom: 0px">
			<div id="table_divNTO" class="col l10" style="overflow-y:auto;height: 58%;">
				
			</div>
			<div class="col l2">
				<!--FOR TESTING: parent div's border color-> style="border: 1px solid #881818;" -->
<!-- 				<ul style="line-height: 35px;;margin-top:0"> -->
<!-- 					<li>&nbsp;<div class="red-status"></div>&nbsp;&nbsp;&nbsp;&nbsp;% LGA With >3 Antigen in red</li> -->
<!-- 					<li>&nbsp;<div class="green-status"></div>&nbsp;&nbsp;&nbsp;&nbsp;% LGA With no Antigen in red</li> -->
<!-- 					<li>&nbsp;<div class="yellow-status"></div>&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 						<span style="line-height: 5px"> -->
<!-- 							% LGA With Antigen that need to &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;re-order Stock -->
<!-- 						</span> -->
<!-- 					</li> -->
<!-- 					<li><img alt="" height="7px;" width="30px;" src="resources/images/Arrow.png" class="same_trend_image">&nbsp;Trend is the same</li> -->
<!-- 					<li><img alt="" height="7px;" width="25px" src="resources/images/Arrow.png" class="decres_trend_image">&nbsp;&nbsp;Decreasing Trend</li> -->
<!-- 					<li><img alt="" height="7px" width="25px" src="resources/images/Arrow.png" class="incres_trend_image">&nbsp;&nbsp;Increasing Trend</li> -->
<!-- 				</ul> -->
				<ul style="line-height: 35px; margin-top: 0;">
						<li>
							 <div>
							 <span class="red-status"></span> 
							<span>% LGA	With &gt;3 Antigen in red</span>
							 </div>
						</li>
	
						<li>
							<div>
							 <span class="green-status"></span> 
							<span> % LGA With no Antigen in red</span>
							</div>
						</li>
	
						<li>
							<div>
							 <span class="yellow-status"></span>
							<span >
								% LGA With Antigen that need to re-order Stock 
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
</body>
<script type="text/javascript">
	$('#year_comboboxNTO').combobox({
		url : 'get_year_list',
		valueField : 'value',
		textField : 'label',
		onSelect : function(rec) {
			$('#week_comboboxNTO').combobox({
				url : 'get_week_list/week?yearParam=' + rec.value,
				valueField : 'value',
				textField : 'label'
			});

		}
	});
	$('#state_comboboxNTO').combobox({
		url : 'get_state_store_list?option=All',
		valueField : 'value',
		textField : 'label'
	});
	$('#state_comboboxNTO').combobox({'value':'null','label':'All'});
	$('#year_comboboxNTO').combobox({});
	$('#week_comboboxNTO').combobox({});
	$('#lgaStockPerformanceLinkNTO').linkbutton({});
	$('#lgaStockAggregatedLinkNTO').linkbutton({});
	$('#exportLinkBtnNTO').linkbutton({});
</script>
<style>
/* VERY IMPORTANT - DON'T REMOVE BELOW CSS STYLE */
.row .col {
    box-sizing: border-box;
    float: left;
    padding: 0 0;
}
</style>