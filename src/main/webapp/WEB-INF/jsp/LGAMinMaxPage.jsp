<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LGA Min/Max Stock Balance Report</title>
<link rel="stylesheet" href="resources/css/buttontoolbar.css"
	type="text/css">
<link rel=" stylesheet" href="resources/css/w3css.css" type="text/css">
<link rel="stylesheet" href="resources/css/table.css" type="text/css">
<link rel="stylesheet" type="text/css" href="resources/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="resources/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="resources/easyui/demo/demo.css">
<script type="text/javascript">
	function setRole() {
		var user = '${userBean.getX_ROLE_NAME()}';
		switch (user) {
		case "SCCO":
		$('#state_combobox_div').hide();
		loadLgaComboboxList('${userBean.getX_WAREHOUSE_ID()}');
			break;
		case "SIO":
			$('#state_combobox_div').hide();
			loadLgaComboboxList('${userBean.getX_WAREHOUSE_ID()}');
			break;
		case "SIFP":
			$('#state_combobox_div').hide();
			loadLgaComboboxList('${userBean.getX_WAREHOUSE_ID()}');
			break;
		case "NTO":
			loadStateComboboxList();
			break;
		case "LIO":

			break;
		case "MOH":

			break;
		}
		
	}
</script>
<style>
#filters  span{
font-weight: bold;
}
</style>
</head>
<body style="margin: 0px;" onload="setRole()">

							<!-- status dialog -->
	<div id="stutus_dialog" ></div>
	<div class="report_title" style="text-align: center;font-size: 15px;">LGA Min/Max Stock Balance Report</div>
	
	<!-- button bar -->

	<div id="filters" style="padding: 3px;display: inline-flex;padding-left: 5px;">
				<div id="state_combobox_div" >
					<label id="state_label">State Store:</label><br>
					<input id="state_combobox"  class="easyui-combobox" name="state_combobox"  style="width:120px" >
				</div>
			<div id="lga_combobox_div" style="display: inline;">
				<span>LGA:</span><br>
				 <select id="lga_combobox" class="easyui-combobox"
					name="lga_combobox" style="width:200px;">
				</select> 
			</div>&nbsp;&nbsp;
			<div id="min_max_combobox_div">
						<span>Min./Max Filter: </span><br>
						 <select id="min_max_combobox" class="easyui-combobox"
							name="period_combobox" style="width:130px;">
						</select> 
				</div>&nbsp;&nbsp;
			<div id="period_combobox_div">
						<span>Period Type: </span><br>
						 <select id="period_combobox" class="easyui-combobox"
							name="period_combobox" style="width:130px;">
						</select> 
				</div>&nbsp;&nbsp;
			<div id="year_combobox_div">
					<span>Year:</span> <br>
					<select id="year_combobox" class="easyui-combobox"
						name="year combobox" style="width: 100px;">
					</select>
				</div>&nbsp;&nbsp;
			<div id="week_combobox_div">
					<span id="week_label">Week</span><br>
					 <select id="week_combobox" class="easyui-combobox"
						name="week_combobox" style="width: 100px;">
					</select> 
			</div>&nbsp;&nbsp;&nbsp;
			<div style="margin-top: 12px;">
			<a class="easyui-linkbutton" onclick="showLgaMinMaxData()"> View Report</a>
			</div>
			
	</div>
	<!-- user table -->
	<table id="LgaMinMaxTable" class="easyui-datagrid"
		style="width: 100%; height: 420px" title="LGA MIN/MAX"
		toolbar="#tb" rownumbers="true" pagination="true" singleSelect="true"
		striped="true" remoteSort="false">


	</table>
		
	</body>
<script type="text/javascript" src="resources/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript"
	src="resources/easyui/jquery.easyui.min.js"></script>
<script src="resources/js/common.js" type="text/javascript"></script>
<script src="resources/js/datagrid_agination.js" type="text/javascript"></script>
<script type="text/javascript">
function showLgaMinMaxData(){
	var message="";
	var url="";
	var validate=true;
	if($('#state_combobox').combobox('getValue')=='' 
			&& '${userBean.getX_ROLE_NAME()}'=='NTO'){
		validate=false;
		message=("State is Empty");
	}else if($('#lga_combobox').combobox('getValue')==''){
		validate=false;
		message=("LGA is Empty");
	}else if($('#min_max_combobox').combobox('getValue')==''){
		validate=false;
		message=("Please Select Min/Max");
	}else if($('#period_combobox').combobox('getValue')==''){
		validate=false;
		message=("Period Type is Empty");
	}else if($('#year_combobox').combobox('getValue')==''){
		validate=false;
		message=("Year is Empty");
	}else if($('#week_combobox').combobox('getValue')==''){
		validate=false;
		message=("Week is Empty");
	}
	if(message!=''){
		alertBox(message);
	}
	if(validate){
		var stateId=$('#state_combobox').combobox('getValue');
		var lgaId=$('#lga_combobox').combobox('getValue');
		var minMax=$('#min_max_combobox').combobox('getValue');
		var perioadType=$('#period_combobox').combobox('getValue');
		var year=$('#year_combobox').combobox('getValue');
		var weekOrMonth=$('#week_combobox').combobox('getValue');
		$('#LgaMinMaxTable').datagrid({
			url : 'get_min_max_grid_data',
			remoteSort : false,
			columns : [ [{field:'LGA_ID',title:'LGA_ID',sortable:true,hidden:true},
			             {field:'LGA_NAME',title:'LGA NAME',sortable:true},
			             {field:'ITEM_ID',title:'ITEM_ID',sortable:true,hidden:true},
			             {field:'ITEM_NUMBER',title:'ITEM NUMBER',sortable:true},
			             {field:'ONHAND_QUANTITY',title:'STOCK BALANCE',sortable:true},
			             {field:'MIN_STOCK_BALANCE',title:'MIN STOCK QTY',sortable:true},
			             {field:'MAX_STOCK_BALANCE',title:'MAX_STOCK_QTY',sortable:true},
			             {field:'DIFFERENCE',title:'DIFFERENCE',sortable:true}
					 ] ],
			queryParams:{stateId:stateId,lgaId:lgaId,minMax:minMax,perioadType:perioadType,year:year,weekOrMonth:weekOrMonth}		 
		});
		if($('#lga_combobox').combobox('getText')=='All'){
			$('#LgaMinMaxTable').datagrid('hideColumn', 'ITEM_NUMBER');
			$('#LgaMinMaxTable').datagrid('hideColumn', 'ONHAND_QUANTITY');
			$('#LgaMinMaxTable').datagrid('hideColumn', 'MIN_STOCK_BALANCE');
			$('#LgaMinMaxTable').datagrid('hideColumn', 'MAX_STOCK_BALANCE');
			$('#LgaMinMaxTable').datagrid('hideColumn', 'DIFFERENCE');
		}else{
			$('#LgaMinMaxTable').datagrid('hideColumn', 'LGA_NAME');
			if($('#min_max_combobox').combobox('getValue')=='Min'){
				$('#LgaMinMaxTable').datagrid('hideColumn', 'MAX_STOCK_BALANCE');
			}else if($('#min_max_combobox').combobox('getValue')=='Max'){
				$('#LgaMinMaxTable').datagrid('hideColumn', 'MIN_STOCK_BALANCE');
			}
		}
	}

}
function alertBox(message){
	  $.messager.alert('Warning!',message,'warning');
}
</script>
<script type="text/javascript">
var lgaidForProduct="";
function loadStateComboboxList(){
	$('#state_combobox').combobox({
		url:"get_state_store_list?option=notAll",
		valueField : 'value',
		textField : 'label' ,
		onSelect:function(state){
			loadLgaComboboxList(state.value);
		}
	});
}
function loadLgaComboboxList(stateId){
	$('#lga_combobox').combobox({
		url : 'getlgalistBasedOnStateId?option=All&stateId='+stateId,
		valueField : 'value',
		textField : 'label',
		onSelect : function(lgaId){
		}
	});
}
$('#min_max_combobox').combobox({
	valueField : 'value',
	textField : 'label',
	data:[{label:'Minimum Stock',value:'Min'},{label:'Maximum Stock',value:'Max'}]
});
$('#period_combobox').combobox({
	valueField : 'value',
	textField : 'label',
	data:[{label:'MONTHLY',value:'MONTHLY'},{label:'WEEKLY',"selected":true,value:'WEEKLY'}],
	onSelect:function(period){
		if(period.value=='MONTHLY'){
			$('#week_label').text('Month: ');
		}else if(period.value=='WEEKLY'){
			$('#week_label').text('Week: ');
		}
		$('#year_combobox').combobox('clear');
		$('#week_combobox').combobox('clear');
	}
});
$('#year_combobox').combobox({
	url : 'get_year_list',
	valueField : 'value',
	textField : 'label',
	onSelect : function(rec) {
		var url="";
		if($('#period_combobox').combobox('getValue')=='MONTHLY'){
			url="get_week_list/month?yearParam="+ rec.value;
		}else if($('#period_combobox').combobox('getValue')=='WEEKLY'){
			url="get_week_list/week?yearParam="+ rec.value;
		}else if($('#period_combobox').combobox('getValue')==''){
			alert("Please Select Period Type")
		}
		$('#week_combobox').combobox({
			url : url,
			valueField : 'value',
			textField : 'label'
		});
	}
});
loadPaginationForTable(LgaMinMaxTable);
</script>
</html>