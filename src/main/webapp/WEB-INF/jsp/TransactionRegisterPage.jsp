<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transaction Register Page</title>
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
			$('#stateFilter').hide();
			loadLgaComboboxList('${userBean.getX_WAREHOUSE_ID()}');
			break;
		case "SIO":
			$('#stateFilter').hide();
			loadLgaComboboxList('${userBean.getX_WAREHOUSE_ID()}');
			break;
		case "SIFP":
			$('#stateFilter').hide();
			loadLgaComboboxList('${userBean.getX_WAREHOUSE_ID()}');
			break;
		case "NTO":
			loadStateComboboxList();
			break;
		case "LIO":
			$('#stateFilter').hide();
			$('#lga_filter').hide();
			loadProductBasedOnLga('${userBean.getX_WAREHOUSE_ID()}');
			break;
		case "MOH":
			$('#stateFilter').hide();
			$('#lga_filter').hide();
			loadProductBasedOnLga('${userBean.getX_WAREHOUSE_ID()}');
			break;
		}
	}
	function doSearch() {
		alert("ok")
		$('#userListTable').datagrid('load', {
			itemid : $('#itemid').val(),
			productid : $('#productid').val()
		});
	}
</script>
</head>
<body style="margin: 0px;" onload="setRole()">
										<!-- status dialog -->
	<div id="stutus_dialog" ></div>
	
<!-- filters -->
		<div id="filters" style="padding: 3px;display: inline-flex;">
		<div id="stateFilter" >
					<label id="state_label">State Store:</label><br>
					<input id="state_combobox"  class="easyui-combobox" name="state_combobox"  style="width:120px" >
				</div>
		<div id="lga_filter" >&nbsp;&nbsp;
			<span>LGA:</span><br>
				 <select id="lga_combobox" class="easyui-combobox"
					name="lga_combobox" style="width:200px;">
				</select> 
		</div>&nbsp;&nbsp;
		<div id="product_filter">
			<span>Product:</span> <br>
			<select id="product_combobox"
				class="easyui-combobox" name="product_id" style="width: 200px;">
			</select>
		</div>&nbsp;&nbsp;
		<div id="transactionFilter" >
						<label id="transaction-label">Transaction Type:</label><br>
						<input id="transaction_combobox"  class="easyui-combobox" name="transaction_combobox"  style="width:180px">
		</div>&nbsp;&nbsp;
		<div id="from_dateFilter">
			<label id="from_date_label">From Date:</label><br>
					<input id="from_datePicker" type="text" data-options="formatter:myformatter,parser:myparser"  class="easyui-datebox" style="width: 120px;" >
		</div>&nbsp;&nbsp;
		<div id="to_dateFilter">
			<label id="to_date_label">To Date:</label><br>
					<input id="to_datePicker" type="text" data-options="formatter:myformatter,parser:myparser"  class="easyui-datebox" style="width: 120px;">
		</div>&nbsp;&nbsp;
		<div id="refresh_button" style="margin-top: 12px;">
			 	<a href="#" class="easyui-linkbutton" 
				onclick="transactionRegisterData()">Refresh
				 </a>
		</div>&nbsp;&nbsp;
		<div id="export_button" style="margin-top: 12px;">
			<a href="#" class="easyui-linkbutton" onclick="handleExport()"
				>Export
			</a>
		</div>
			
		</div>
		<!-- filters end here -->
	<!-- user table -->
	<div id="transation_reg_table_div" style="margin-left: 5px">
	<table id="transactionRegisterTable" class="easyui-datagrid"
		style="width: 100%; height: 450px;" title="Transaction History"
		data-options="toolbar:'#tb', rownumbers:'true', pagination:'true',pageSize:50, singleSelect:'true',
		striped:'true', remoteSort:'false'">
	</table>
	</div>
		
</body>
<script type="text/javascript" src="resources/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript"
	src="resources/easyui/jquery.easyui.min.js"></script>
<script src="resources/js/common.js"></script>
<script src="resources/js/datagrid_agination.js" type="text/javascript"></script>
<script type="text/javascript">
function transactionRegisterData(){
	var url="";
	if($('#lga_combobox').combobox('getValue')==""
			&&!('${userBean.getX_ROLE_NAME()}'=='MOH'
				|| '${userBean.getX_ROLE_NAME()}'=='LIO')){
		validate=false;
		alertBox("LGA is Empty");
	}else{
		var lgaId=$('#lga_combobox').combobox('getValue');
		if('${userBean.getX_ROLE_NAME()}'=='MOH'
			|| '${userBean.getX_ROLE_NAME()}'=='LIO'){
		lgaId='${userBean.getX_WAREHOUSE_ID()}';
		}
		var productId=$('#product_combobox').combobox('getValue');
		var transactionTypeId=$('#transaction_combobox').combobox('getValue');
		var fromDate=$('#from_datePicker').combobox('getValue');
		var toDate=$('#to_datePicker').combobox('getValue');
		$('#transactionRegisterTable').datagrid({
			url : 'get_transaction_register_grid_data',
			queryParams :{lgaId:lgaId,productId:productId,transactionTypeId:transactionTypeId,fromDate:fromDate,toDate:toDate},
			columns : [ [{field:'TRANSACTION_ID',title:'TRANSACTION_ID',sortable:true,hidden:true},
			             {field:'ITEM_ID',title:'ITEM_ID',sortable:true,hidden:true},
			             {field:'ITEM_NUMBER',title:'Product Name',sortable:true},
			             {field:'TRANSACTION_QUANTITY',title:'Quantity',sortable:true},
			             {field:'TRANSACTION_UOM',title:'UOM',sortable:true},
			             {field:'TRANSACTION_DATE',title:'Transaction DateTime',sortable:true},
			             {field:'REASON',title:'Comment',sortable:true},
			             {field:'TRANSACTION_TYPE_ID',title:'TRANSACTION_TYPE_ID',sortable:true,hidden:true},
			             {field:'TRANSACTION_TYPE',title:'Transaction Type',sortable:true},
			             {field:'FROM_NAME',title:'Source',sortable:true},
			             {field:'TO_NAME',title:'Destination',sortable:true},
			             {field:'TO_SOURCE_ID',title:'TO_SOURCE_ID',sortable:true,hidden:true},
			             {field:'REASON_TYPE',title:'Reason',sortable:true},
			             {field:'REASON_TYPE_ID',title:'REASON_TYPE_ID',sortable:true,hidden:true},
			             {field:'VVM_STAGE',title:'VVM Status',sortable:true}
	 ] ]
		});
		if($('#transaction_combobox').combobox('getText')=='LGA Receipt'){
			$('#transactionRegisterTable').datagrid('showColumn', 'VVM_STAGE');
			$('#transactionRegisterTable').datagrid('showColumn', 'FROM_NAME');
			$('#transactionRegisterTable').datagrid('showColumn', 'TO_NAME');
		}else if($('#transaction_combobox').combobox('getText')=='Stock Wastages'
				|| $('#transaction_combobox').combobox('getText')=='Stock Adjustments' ) {
			$('#transactionRegisterTable').datagrid('hideColumn', 'FROM_NAME');
			$('#transactionRegisterTable').datagrid('hideColumn', 'TO_NAME');
			$('#transactionRegisterTable').datagrid('hideColumn', 'VVM_STAGE');
		}
	}
}
function alertBox(message){
	  $.messager.alert('Warning!',message,'warning');
}	
</script>
<script type="text/javascript">
function loadLgaComboboxList(stateId){
	$('#lga_combobox').combobox({
		url : 'getlgalistBasedOnStateId?option=NotAll&stateId='+stateId,
		valueField : 'value',
		textField : 'label',
		onSelect : function(lgaId){
			$('#product_combobox').combobox('clear');
			$('#transaction_combobox').combobox('clear');
			loadProductBasedOnLga(lgaId.value);
		}
	});
}
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
	function loadProductBasedOnLga(lgaId){
		$('#product_combobox').combobox({
			url : 'getproductlist?lgaid=' +lgaId+'&option=All',
			valueField : 'value',
			textField : 'label'
		});	
	}
	$('#transaction_combobox').combobox({
		url : 'getTransactionlist?option=All',
		valueField : 'value',
		textField : 'label'
	});
	function handleExport(){
		if($('#transactionRegisterTable').datagrid('getRows').length>0){
			window.location.href="transition_register_export";
		}else{
			alertBox("Table is Empty")
		}
	}
	loadPaginationForTable(transactionRegisterTable);
</script>
</html>