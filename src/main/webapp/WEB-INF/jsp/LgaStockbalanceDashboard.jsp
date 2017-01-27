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
.mediumpurple-status {
	width: 10px;
	height: 10px;
	background:  #8e84fb;
 	display: inline-block; 
 	margin-top: 6px;
}
.table_div5{
	height: 60%;
    overflow: hidden;
}
.wrap5 {
	width: 100%;
}
.headingTable5 table{
	width: 98.7%;
	border-collapse: collapse;
	font-size:12px;
}

.headingTable5 table tr td {
	border: 1px solid black;
	background-color: #e7f0ff;
	padding: 6px;
	width: 106px;
	text-align: center;
}


.inner_table5 table tr td:FIRST-CHILD {
	padding: 5px;
	border: 2px solid black;
	width: 15.1%;
	word-wrap: break-word;
	text-align: left;
	font-size: 12px;
}
.headingTable5 table tr td:FIRST-CHILD{
padding: 5px;
	border: 1px solid black;
	width: 15.1%;
	word-wrap: break-word;
	text-align: left;
}

.inner_table5 {
	height:88%;
	overflow-x: auto;
	overflow-y: scroll;
}
.inner_table5 table tr td {
	padding: 5px;
	border: 1px solid black;
	width: 106px;
	word-wrap5: break-word;
	text-align: center;
	font-size: 12px;
}
</style>

</head>
<body >
	<div style="margin: 5px 5px">
		<!-- filters -->
		<div class="row" style="margin: 8px 0px">
			<div class="col l3" id="stateStoreFilter" style="height:24px">
				<span>State Store Filter:</span>
				<select id="filterGridData5" class="easyui-combobox" name="state_combobox" style="width: 150px;"></select>
			</div>
			<div class="col l12" style="height:24px">
				<span>Year:</span> 				
				<select id="year_combobox5" class="easyui-combobox" name="year_combobox" style="width: 80px;"></select>				
				<span>Week</span> 				
				<select id="week_combobox5" class="easyui-combobox" name="week_combobox" style="width: 60px;"></select>
				<a id="viewDashboardLinkBtn5" href="#" class="easyui-linkbutton" onclick="filterGridData5()">View Dashboard </a>
				<a id="filterGridData5" href="lga_Stock_balance_dashboard_export" class="easyui-linkbutton">Export</a>
				<ul class="status-list" style="margin:0 0;">
					<li><div><div class="red-status"></div><div>&nbsp;&nbsp;Antigen Below Minimum Level&nbsp;&nbsp;</div></div></li>
					<li><div><div class="green-status"></div><div>&nbsp;&nbsp;Antigen Sufficient&nbsp;&nbsp;</div></div></li>
					<li><div><div class="yellow-status"></div> <div>&nbsp;&nbsp;Antigen need to be re-orderd&nbsp;&nbsp;</div></div></li>
					<li><div><div class="mediumpurple-status"></div> <div>Over Stock</div></div></li>
				</ul>				
			</div>	
		</div>
		<hr width="100%;">
		<!-- filters ends here -->
		
		<div id="table_div5" class="table_div5">
			<div class="wrap5">
				<div class="headingTable5">
				<table  id="heading_table5">
					<!-- DYNAMICALLY ROWS ADDED HERE! -->
				</table>
				</div>
				<div class="inner_table5">
					<table id="table_body5">
						<!-- Some more tr's -->
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
function filterGridData5() {
	var stateId="";
	var stateName="";
	var validate=true;
	var message="";
//	alert("ROLE: "+'${userBean.getX_ROLE_NAME()}');
	if('${userBean.getX_ROLE_NAME()}'==='NTO'){
		if ($('#filterGridData5').combobox('getValue') === "") {
			message=("State is Empty");
			validate=false;
		}else if ($('#year_combobox5').combobox('getValue') === "") {
			message=("Year is Empty");
			validate=false;
		} else if ($('#week_combobox5').combobox('getValue') === "") {
			validate=false;
			message=("Week is Empty");
		}else{	
			stateId=$('#filterGridData5').combobox('getValue');
//			alert("state Iddddddddddddddddddddd : "+stateId);
			stateName=$('#filterGridData5').combobox('getText');
			 validate=true;
		}
	}else if(('${userBean.getX_ROLE_NAME()}'==="SCCO") || ('${userBean.getX_ROLE_NAME()}'==="SIO") || ('${userBean.getX_ROLE_NAME()}'==="SIFP")){
//		alert("SCCO LGA_ID = "+'${userdata.getX_ROLE_NAME()}');
		if ($('#year_combobox5').combobox('getValue') === "") {
			message=("Year is Empty");
			validate=false;
		} else if ($('#week_combobox5').combobox('getValue') === "") {
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
		var year = $('#year_combobox5').combobox('getValue');
		var week = $('#week_combobox5').combobox('getValue');
		var url = "get_lga_stock_balance_dashbaord_data?year="+year+"&week="+week+"&stateId="+stateId+"&stateName="+stateName;
		loadHeadingTable5(stateId,stateName);
		showTableData5(url);
	}
}
function loadStateCombobox5(){
	$('#filterGridData5').combobox({
		url : 'get_state_store_list',
		valueField : 'value',
		textField : 'label'
	});
}
$('#year_combobox5').combobox({
		url : 'get_year_list',
		valueField : 'value',
		textField : 'label',
		onSelect : function(rec) {
			$('#week_combobox5').combobox({
				url : 'get_week_list/week?yearParam='+rec.value,
				valueField : 'value',
				textField : 'label'
			});

		}
});
/* Do not delete below code - IMPORTANT */
$('#year_combobox5').combobox({});
$('#week_combobox5').combobox({});
$('#viewDashboardLinkBtn5').linkbutton({});
$('#filterGridData5').linkbutton({});
$('#filterGridData5').combobox({});
</script>