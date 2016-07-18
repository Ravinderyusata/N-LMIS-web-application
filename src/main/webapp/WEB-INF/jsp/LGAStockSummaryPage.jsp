<%@ page isELIgnored="false"%>
<head>
<link type="text/css" href="resources/easyui/themes/default/easyui.css" rel="stylesheet">
<script src="resources/easyui/jquery.easyui.min.js"></script>
<style>

.status-list {
	 padding-left: 25px;
	 display: inline-flex;
 } 
.status-list li div {
	display: inline-flex;
 } 
.red-status {
	width: 10px;
	height: 10px;
	background: red;
 	display: inline-block; 
 	margin-top: 6px;
}
.green-status {
	width: 10px;
	height: 10px;
	background: green;
 	display: inline-block; 
 	margin-top: 6px;
}

.yellow-status {
	width: 10px;
	height: 10px;
	background: yellow;
 	display: inline-block; 
 	margin-top: 6px;
}
.table_div3{
	overflow-x:auto;
	overflow-y:auto;
}
.wrap {
	width: 100%;
}
.wrap table {
	width: 100%;
	table-layout: fixed;
	border-collapse: collapse;
	font-size:12px;
}
.wrap table tr th {
	border: 1px solid black;
	background-color: #e7f0ff;
	padding: 5px 0px 5px 0px;
}
table tr td {
	padding: 5px;
	border: 1px solid black;
	width: 100px;
	word-wrap: break-word;
}
table tr td {
	padding: 5px;
	border: 1px solid black;
	width: 100px;
	text-align:center;
	word-wrap: break-word;
}
table tr td:FIRST-CHILD {
	padding: 5px;
	border: 1px solid black;
	width: 200px;
	word-wrap: break-word;
	text-align: left;
}
table.head tr td {
	background: #eee;
}
.inner_table {
	height:410px;
	overflow-y: overlay;
	overflow-x: auto;
}
</style>
</head>
<body>
	<div style="margin: 5px 5px">
		<!-- filters -->
		<div class="row" style="margin: 8px 0px">
			<div class="col l3" id="state_combobox3_div" style="height:24px">
				<span>State Store Filter:</span>
				<select id="state_combobox3" class="easyui-combobox" name="state_combobox" style="width: 150px;"></select>
			</div>
			<div class="col l9" style="height:24px">
				<span>Year:</span> 				
				<select id="year_combobox3" class="easyui-combobox" name="year_combobox" style="width: 80px;"></select>				
				<span>Week</span> 				
				<select id="week_combobox3" class="easyui-combobox" name="week_combobox" style="width: 60px;"></select>
				<a id="viewDashboardLinkBtn3" href="#" class="easyui-linkbutton" onclick="filterGridData3()">View Dashboard </a>
				<a id="exportLinkBtn3" href="export_data_grid" class="easyui-linkbutton">Export</a>
				<ul class="status-list" style="margin:0 0;">
					<li><div><div class="red-status"></div><div>Antigen Below Minimum Level</div></div></li>
					<li><div><div class="green-status"></div><div>Antigen Sufficient</div></div></li>
					<li><div><div class="yellow-status"></div> <div>Antigen need to be re-orderd</div></div></li>
				</ul>				
			</div>	
		</div>
		<hr width="100%;">
		<!-- filters ends here -->
		
		<div id="table_div3" class="table_div3">
			<div class="wrap">
				<table class="head" id="heading_table3">
					<!-- DYNAMICALLY ROWS ADDED HERE! -->
				</table>
				<div class="inner_table">
					<table id="table_body3">
						<!-- Some more tr's -->
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
function filterGridData3() {
	var stateId="";
	var lgaName="";
	var validate=true;
	var message="";
//	alert("ROLE: "+'${userBean.getX_ROLE_NAME()}');
	if('${userBean.getX_ROLE_NAME()}'==='NTO'){
		if ($('#state_combobox3').combobox('getValue') === "") {
			message=("State is Empty");
			validate=false;
		}else if ($('#year_combobox3').combobox('getValue') === "") {
			message=("Year is Empty");
			validate=false;
		} else if ($('#week_combobox3').combobox('getValue') === "") {
			validate=false;
			message=("Week is Empty");
		}else{	
			stateId=$('#state_combobox3').combobox('getValue');
//			alert("state Iddddddddddddddddddddd : "+stateId);
			lgaName=$('#state_combobox3').combobox('getText');
			 validate=true;
		}
	}else if(('${userBean.getX_ROLE_NAME()}'==="SCCO") || ('${userBean.getX_ROLE_NAME()}'==="SIO") || ('${userBean.getX_ROLE_NAME()}'==="SIFP")){
//		alert("SCCO LGA_ID = "+'${userdata.getX_ROLE_NAME()}');
		if ($('#year_combobox3').combobox('getValue') === "") {
			message=("Year is Empty");
			validate=false;
		} else if ($('#week_combobox3').combobox('getValue') === "") {
			validate=false;
			message=("Week is Empty");
		}else{
			 validate=true;
		}
	}
	if(message!=''){
		alertBox(message);
	}
	if(validate){
		var year = $('#year_combobox3').combobox('getValue');
		var week = $('#week_combobox3').combobox('getValue');
		var url = "get_lga_stock_summary_grid_data?year="+year+"&week="+week+"&stateId="+stateId;
		loadHeadingTable3(stateId,lgaName);
		showTableData3(url);
	}
}
function loadStateCombobox3(){
	$('#state_combobox3').combobox({
		url : 'get_state_store_list',
		valueField : 'value',
		textField : 'label'
	});
}
$('#year_combobox3').combobox({
		url : 'get_year_list',
		valueField : 'value',
		textField : 'label',
		onSelect : function(rec) {
			$('#week_combobox3').combobox({
				url : 'get_week_list/week?yearParam='+rec.value,
				valueField : 'value',
				textField : 'label'
			});

		}
});
/* Do not delete below code - IMPORTANT */
$('#year_combobox3').combobox({});
$('#week_combobox3').combobox({});
$('#viewDashboardLinkBtn3').linkbutton({});
$('#exportLinkBtn3').linkbutton({});
$('#state_combobox3').combobox({});
</script>
	<!-- /* Do not delete below code - IMPORTANT */
	$('#year_combobox').combobox({}); */
	$('#week_combobox').combobox({});
	$('#viewDashboardLinkBtn').linkbutton({});
	$('#exportLinkBtn').linkbutton({}); */ -->