<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LGA Bincard Page</title>
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
			$('#yearFilter').hide();
			loadLgaComboboxList('${userBean.getX_WAREHOUSE_ID()}');
			break;
		case "SIO":
		$('#stateFilter').hide();
		$('#yearFilter').hide();
		loadLgaComboboxList('${userBean.getX_WAREHOUSE_ID()}');
			break;
		case "SIFP":
			$('#stateFilter').hide();
			$('#yearFilter').hide();
			loadLgaComboboxList('${userBean.getX_WAREHOUSE_ID()}');
			break;
		case "NTO":
			$('#yearFilter').hide();
			loadStateComboboxList();
			break;
		case "LIO":
			$('#stateFilter').hide();
			$('#lgaFilter').hide();
			$('#yearFilter').hide();
			lgaidForProduct='${userBean.getX_WAREHOUSE_ID()}';
			loadProductLgaBased('${userBean.getX_WAREHOUSE_ID()}');
			break;
		case "MOH":
			$('#stateFilter').hide();
			$('#lgaFilter').hide();
			$('#yearFilter').hide();
			lgaidForProduct='${userBean.getX_WAREHOUSE_ID()}';
			loadProductLgaBased('${userBean.getX_WAREHOUSE_ID()}');
			break;
		}
		
	}
	
</script>
</head>
<body style="margin: 0px;" onload="setRole()">

	<!-- headr of page -->
	<%-- <jsp:include page="headerforpages.jsp"></jsp:include> --%>
									<!-- status dialog -->
	<div id="stutus_dialog" ></div>
	<div class="report_title" style="text-align: center;font-size: 15px;">LGA BinCard Report</div>
	<!-- button bar -->

	<div class="button_bar_for_report" id="button_bar_for_report">
		<ul>
			<li>
			<div id="filterBy-filter" >
			<label id="filterby-label">Filter By:</label><br>
				<input id="filterby-combobox" class="easyui-combobox" name="filterby-combobox"  style="width:120px" >
			</div>
			</li>
			<li>
			<div id="dateFilter">
			<label id="data-label">Date:</label><br>
					<input id="datePicker" data-options="formatter:myformatter,parser:myparser" class="easyui-datebox" style="width: 120px;">
			</div>
			</li>
			<li>
			<div id="yearFilter">
			<label id="yearFilterLabel">Year:</label><br>
					<input id="yearCombobox" class="easyui-combobox" name="yearCombobox"  style="width:80px" onchange="onYearChange()">
			</div>
			</li>
			<li>
				<div id="monthFilter">
					<label id="month_label">Month:</label><br>
					<input id="month_combobox" class="easyui-combobox" name="month_combobox"  style="width:80px"  >
				</div>
			</li>
			<li>
				<div id="stateFilter" >
					<label id="state_label">State Store:</label><br>
					<input id="state_combobox"  class="easyui-combobox" name="state_combobox"  style="width:120px" >
				</div>
			</li>
			<li>
				<div id="lgaFilter" >
					<label id="lga-label">LGA:</label><br>
					<input id="lga_combobox"  class="easyui-combobox" name="lga_combobox"  style="width:200px" >
				</div>
			</li>
			<li>
				<div id="transactionFilter" >
						<label id="transaction-label">Transaction Type:</label><br>
						<input id="transaction_combobox"  class="easyui-combobox" name="transaction_combobox"  style="width:180px">
				</div>
			</li>
			<li>
				<div id="productFilter" >
						<label id="product-label">Product:</label><br>
						<input id="product_combobox"  class="easyui-combobox" name="product_combobox"  style="width:180px">
				</div>
			</li>
			<li><div style="padding-top: 15px;"><a class="easyui-linkbutton" onclick="showLgaBinCarddata()"> <b>View Report</b>
			</a></div></li>
		</ul>
	</div>
	<!-- user table -->
	<table id="LgaBinCardTable" class="easyui-datagrid"
		style="width: 100%; height: 390px" title="LGA Bin Card"
		toolbar="#tb" rownumbers="true" pagination="true" pageSize=30 singleSelect="true"
		striped="true" remoteSort="false">


	</table>
	
	</body>
<script type="text/javascript" src="resources/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript"
	src="resources/easyui/jquery.easyui.min.js"></script>
	<script src="resources/js/common.js"></script>
	<script src="resources/js/datagrid_agination.js" type="text/javascript"></script>
<script type="text/javascript">
function showLgaBinCarddata(){
	var message="";
	var validate=true;
	if($('#filterby-combobox').combobox('getValue')==''){
		validate=false;
		message="Filter by is Empty";
	}else if($('#filterby-combobox').combobox('getValue')=='MONTH/YEAR' && $('#yearCombobox').combobox('getValue')==''){
			validate=false;
			message="Year is Empty";
		
	}else if($('#filterby-combobox').combobox('getValue')=='MONTH/YEAR' && $('#month_combobox').combobox('getValue')==''){
		validate=false;
		message=("Month is Empty");
	}else if($('#filterby-combobox').combobox('getValue')=='DAY' && $('#datePicker').combobox('getValue')==''){
		validate=false;
		message=("Date is Empty!");
	}else if($('#state_combobox').combobox('getValue')==''
			&& '${userBean.getX_ROLE_NAME()}'=='NTO'){
		validate=false;
		message=("State is Empty!");
	}else if($('#lga_combobox').combobox('getValue')==''
			&& !('${userBean.getX_ROLE_NAME()}'=='LIO'
			|| '${userBean.getX_ROLE_NAME()}'=='MOH')){
		validate=false;
		message=("LGA is Empty!");
	}else if($('#transaction_combobox').combobox('getValue')==''){
		validate=false;
		message=("Transaction is Empty!");
	}else if($('#product_combobox').combobox('getValue')==''){
		validate=false;
		message=("Product is Empty!");
	}
	if(message!=''){
		alertBox(message);
	}
	if(validate){
		var dateType=$('#filterby-combobox').combobox('getValue');
		var year=$('#yearCombobox').combobox('getValue');
		var date=$('#datePicker').combobox('getValue');
		var month=$('#month_combobox').combobox('getText');
		var lgaId=$('#lga_combobox').combobox('getValue');
		if('${userBean.getX_ROLE_NAME()}'=='MOH'
				|| '${userBean.getX_ROLE_NAME()}'=='LIO'){
			lgaId='${userBean.getX_WAREHOUSE_ID()}';
		}
		var transactionType=$('#transaction_combobox').combobox('getValue');
		var productType=$('#product_combobox').combobox('getValue');
		$('#LgaBinCardTable').datagrid({
			url : 'get_lga_bincard_grid_data',
			remoteSort : false,
			queryParams:{dateType:dateType,year:year,month:month,lgaId:lgaId,transactionType:transactionType,productType:productType,date:date},
			columns : [ [{field:'ITEM_ID',title:'ITEM_ID',sortable:true,hidden:true},
			             {field:'ITEM_NUMBER',title:'Product Name',sortable:true},
			             {field:'ONHAND_QUANTITY_BEFOR_TRX',title:'LGA Stock Balance(Before Transaction)',sortable:true,hidden:true,align:'center'}, 
			             {field:'TOTAL_TRANSACTION_QUANTITY',title:'Total Transaction Quantity',sortable:true,align:'center'},
			             {field:'TRANSACTION_QUANTITY',title:'Transaction Quantity',sortable:true,align:'center'}, 
			             {field:'TRANSACTION_TYPE_ID',title:'TRANSACTION_TYPE_ID',sortable:true,hidden:true},
			             {field:'TYPE_CODE',title:'Transaction Type',sortable:true},
			             {field:'REASON_TYPE',title:'Transaction Reason',sortable:true},
			             {field:'LGA_ID',title:'LGA_ID',sortable:true,hidden:true},
			            {field:'TRANSACTION_MONTH',title:'TRANSACTION_MONTH',sortable:true,hidden:true},
			             {field:'TRANSACTION_YEAR',title:'TRANSACTION_YEAR',sortable:true,hidden:true},
			             {field:'ONHAND_QUANTITY_AFTER_TRX',title:'Current LGA Stock Balance(after Transaction)',sortable:true,hidden:true,align:'center'}, 
						{field:'REASON',title:'Comment',sortable:true}

	 ] ]
		});
		if(dateType=='MONTH/YEAR'){
			$('#LgaBinCardTable').datagrid('hideColumn', 'ONHAND_QUANTITY_BEFOR_TRX');
			$('#LgaBinCardTable').datagrid('hideColumn', 'ONHAND_QUANTITY_AFTER_TRX');
			$('#LgaBinCardTable').datagrid('showColumn', 'TOTAL_TRANSACTION_QUANTITY');
			$('#LgaBinCardTable').datagrid('hideColumn', 'TRANSACTION_QUANTITY');
			
		}else{
			$('#LgaBinCardTable').datagrid('showColumn', 'ONHAND_QUANTITY_BEFOR_TRX');
			$('#LgaBinCardTable').datagrid('showColumn', 'ONHAND_QUANTITY_AFTER_TRX');
			$('#LgaBinCardTable').datagrid('showColumn', 'TRANSACTION_QUANTITY');
			$('#LgaBinCardTable').datagrid('hideColumn', 'TOTAL_TRANSACTION_QUANTITY');
		}
	}
}
function alertBox(message){
	  $.messager.alert('Warning!',message,'warning');
}
</script>
<script type="text/javascript">
$('#dateFilter').hide();
$('#yearCombobox').hide();
$('#monthFilter').hide();
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
		url : 'getlgalistBasedOnStateId?option=NotAll&stateId='+stateId,
		valueField : 'value',
		textField : 'label',
		onSelect : function(lgaId){
			lgaidForProduct=lgaId.value;
			$('#product_combobox').combobox('clear');
			$('#transaction_combobox').combobox('clear');
			loadProductLgaBased(lgaId.value);
		}
	});
}
function loadProductLgaBased(lgaId){
	$('#product_combobox').combobox({
		url:"getproductlist?lgaid="+lgaId+"&option=noAll",
		valueField : 'value',
		textField : 'label' 
	});
}		
		$('#transaction_combobox').combobox({
			url : 'getTransactionlist?option=All',
			valueField : 'value',
			textField : 'label',
			onSelect : function(transactionType){
				if($('#lga_combobox').combobox('getValue')==''
						&& !('${userBean.getX_ROLE_NAME()}'=='LIO')
						|| '${userBean.getX_ROLE_NAME()}'=='MOH'){
					alertBox("Please Select LGA");
					$('#transaction_combobox').combobox('clear');
				}else{
					var urlForProduct="";
					if(transactionType.label=='All'){
						urlForProduct="getproductlist?lgaid="+lgaidForProduct+"&option=noAll";
					}else{
						urlForProduct="getproductlist?lgaid="+lgaidForProduct+"&option=All";
					}
					$('#product_combobox').combobox({
						url:urlForProduct,
						valueField : 'value',
						textField : 'label' 
					});
				}
			}
		});
		
		$('#filterby-combobox').combobox({
			valueField : 'value',
			textField : 'label',
			data:[{label:'MONTH/YEAR',value:'MONTH/YEAR'},{label:'DAY',value:'DAY'}],
			onSelect:function(filterBy){
				if(filterBy.value=='MONTH/YEAR'){
					$('#yearCombobox').combobox('clear');
					$('#month_combobox').combobox('clear');
					$('#yearFilter').show();
					$('#monthFilter').show();
					$('#dateFilter').hide();
				}else if(filterBy.value=='DAY'){
					$('#datePicker').combobox('clear');
					$('#dateFilter').show();
					$('#yearFilter').hide();
					$('#monthFilter').hide();
				}
			} 
		});
		$('#yearCombobox').combobox({
			url : 'get_year_list',
			valueField : 'value',
			textField : 'label',
			onSelect : function(rec) {
				var url="";
				url="get_week_list/month?yearParam="+ rec.value;
				$('#month_combobox').combobox({
					url : url,
					valueField : 'value',
					textField : 'label'
				});
			}
		});
		loadPaginationForTable(LgaBinCardTable);
</script>
</html>