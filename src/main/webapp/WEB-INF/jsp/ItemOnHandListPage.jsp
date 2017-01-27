<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product Stock Balance Page</title>
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
			$('#lga_combobox_div').hide();
			loadProductBasedOnLga('${userBean.getX_WAREHOUSE_ID()}');
			break;
		case "MOH":
			$('#stateFilter').hide();
			$('#lga_combobox_div').hide();
			loadProductBasedOnLga('${userBean.getX_WAREHOUSE_ID()}');
			break;
		}
		/* document.getElementById("common_lable").innerHTML = "LGA Stock Balance";
		if(user=="NTO"){
			document.getElementById("user").innerHTML = "User: National Admin";
			document.getElementById("warehouse_name").innerHTML ="National: "+ '${userBean.getX_WAREHOUSE_NAME()}';
		}else if(user=="SIO" || user=="SCCO" || user=="SIFP"){
			document.getElementById("user").innerHTML = "User: "+user+'${userBean.getX_WAREHOUSE_NAME()}' ;
			document.getElementById("warehouse_name").innerHTML ="State :"+ '${userBean.getX_WAREHOUSE_NAME()}';
		}else if(user=="LIO" || user=="MOH"){
			document.getElementById("user").innerHTML = "User: "+user+'${userBean.getX_WAREHOUSE_NAME()}' ;
			document.getElementById("warehouse_name").innerHTML ="LGA :"+ '${userBean.getX_WAREHOUSE_NAME()}';
		} */

	}
</script>
<style type="text/css">
</style>
</head>
<body style="margin: 0px;" onload="setRole()">
	<!-- headr of page -->
<%-- 	<jsp:include page="headerforpages.jsp"></jsp:include> --%>
	<!-- status dialog -->
	<div id="stutus_dialog"></div>
	
	<!-- user table -->
	<div style="margin-left: 5px;">
	<table id="itemOnHandTable" class="easyui-datagrid"
		style="width: 100%; height: 470px;"
		data-options="title:'Product Stock Balance',toolbar:'#filters', rownumbers:'true',
		 pagination:'true', singleSelect:'true',pageSize:30,
		striped:'true', remoteSort:'false'">
	</table>
	</div>
	<!-- filters -->
	<div id="filters" style="padding: 3px;display: inline-flex">
		<div id="stateFilter" >
					<label id="state_label">State Store:</label>
					<input id="state_combobox"  class="easyui-combobox" name="state_combobox"  style="width:120px" >
		</div>&nbsp;&nbsp;&nbsp;
		
		<div id="lga_combobox_div">
		<span>LGA:</span> 
		<select id="lga_combobox" class="easyui-combobox"
			name="lga_id" style="width: 200px;">
		</select>
		</div>&nbsp;&nbsp;&nbsp;&nbsp;
		<div id="product_combobox_div">
		 <span>Product:</span> 
		<select id="product_combobox"
			class="easyui-combobox" name="product_id" style="width: 200px;">
		</select>
		</div>&nbsp;&nbsp;&nbsp;&nbsp;
		
		<div>
		<a href="#" class="easyui-linkbutton" 
			onclick="filterGridData()">Search</a>
		
	</div>
	
</div>

	
</body>
<script type="text/javascript" src="resources/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript"
	src="resources/easyui/jquery.easyui.min.js"></script>
	<script src="resources/js/common.js" type="text/javascript"></script>
	<script src="resources/js/datagrid_agination.js" type="text/javascript"></script>
<script type="text/javascript">
function filterGridData(lga_id,product_id){
	var warehouse_id="";
	if($('#lga_combobox').combobox('getValue')==""
			&& !('${userBean.getX_ROLE_NAME()}'=='MOH'
			|| '${userBean.getX_ROLE_NAME()}'=='LIO')){
		alert("LGA is Empty");
	}else{
		warehouse_id=$('#lga_combobox').combobox('getValue');
		if('${userBean.getX_ROLE_NAME()}'=='MOH'
			|| '${userBean.getX_ROLE_NAME()}'=='LIO'){
			warehouse_id='${userBean.getX_WAREHOUSE_ID()}';
	}
		var product_id=$('#product_combobox').combobox('getValue');
		var url="get_item_onhand_grid_data?warehouse_id="+warehouse_id+"&product_id="+product_id;
		showTableData(url)
	}
	function showTableData(url){
		$('#itemOnHandTable').datagrid({
			url : url,
			remoteSort : false,
			columns : [ [ {
				field : 'ITEM_ID',
				title : 'ITEM_ID',
				sortable : true,
				hidden : true
			}, {
				field : 'ITEM_NUMBER',
				title : 'Product Name',
				sortable : true
			}, {
				field : 'ITEM_SAFETY_STOCK',
				title : 'ITEM_SAFETY_STOCK',
				sortable : true,
				hidden : true
			}, {
				field : 'ONHAND_QUANTITY',
				title : 'Product Stock Balance',
				sortable : true
			}, {
				field : 'TRANSACTION_UOM',
				title : 'UOM',
				sortable : true
			}, {
				field : 'ITEMS_BELOW_SAFETY_STOCK',
				title : 'ITEMS_BELOW_SAFETY_STOCK',
				sortable : true,
				hidden : true
			} ] ]
		});
	}
	
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
	function loadProductBasedOnLga(lgaId){
		$('#product_combobox').combobox({
			url : 'getproductlist?lgaid=' +lgaId+'&option=All',
			valueField : 'value',
			textField : 'label'
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
	loadPaginationForTable(itemOnHandTable);
</script>
</html>