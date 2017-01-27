<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LGA Stock Inconsistency Report</title>
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
			$('#lgaFilter').show();
			$('#filterBy-filter').show();
			$('#dateFilter').hide();
			$('#weekFilter').hide();
			$('#monthFilter').hide();
			$('#yearFilter').hide();
			
			break;
		case "SIO":

			break;
		case "SIFP":

			break;
		case "NTO":
			
			$('#lgaFilter').show();
			$('#filterBy-filter').show();
			$('#dateFilter').hide();
			$('#weekFilter').hide();
			$('#monthFilter').hide();
			$('#yearFilter').hide();
			break;
		case "LIO":

			break;
		case "MOH":

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
	<div class="report_title" style="text-align: center;font-size: 15px;">LGA Stock Inconsistency Report</div>
	
	<!-- button bar -->

	<div class="button_bar_for_report" id="button_bar_for_report">
		<ul>
			<div id="lgaFilter">
				<li>
					<label id="lga-label">LGA:</label>
					<input id="lga_combobox"  class="easyui-combobox" name="lga_combobox"  style="width:120px"
					data-options="valueField:'value', textField:'label', url:'getlgalist?option=All'">
				</li>
			</div>
			<div id="filterBy-filter">
				<li>
					<label id="filterby-label">Filter By:</label>
					<input id="filterby-combobox" class="easyui-combobox" name="filterby-combobox"  style="width:120px"
							data-options="
								valueField:'label',
								textField:'value',
								data:[{label:'YEAR',value:'YEAR'},{label:'MONTH',value:'MONTH'},
								{label:'WEEK',value:'WEEK'},{label:'DAY',value:'DAY'}],
								onChange: function(newValue, oldValue){
									<!-- alert('newValue'+newValue); -->
									if(newValue==='YEAR'){
										$('#dateFilter').hide();
										$('#weekFilter').hide();
										$('#monthFilter').hide();
										$('#yearFilter').show();
										var url = 'get_year_list';
            							$('#yearCombobox').combobox('reload', url);
            							$('#yearCombobox').combobox('clear');
									}else if(newValue==='MONTH'){
										$('#dateFilter').hide();
										$('#weekFilter').hide();
										$('#monthFilter').show();
										$('#yearFilter').show();
										var url = 'get_year_list';
            							$('#yearCombobox').combobox('reload', url);
            							$('#month-combobox').combobox('clear');	
            							$('#yearCombobox').combobox('clear');							
									}else if(newValue==='WEEK'){
										$('#dateFilter').hide();										
										$('#weekFilter').show();
										$('#monthFilter').hide();
										$('#yearFilter').show();
										var url = 'get_year_list';
            							$('#yearCombobox').combobox('reload', url);
            							$('#yearCombobox').combobox('clear');	
            							$('#week-combobox').combobox('clear');
									}else if(newValue==='DAY'){
										$('#dateFilter').show();
										$('#weekFilter').hide();
										$('#monthFilter').hide();
										$('#yearFilter').hide();
										$('#datePicker').combobox('clear');	
									}
								}"/>	    				
				</li>
			</div>
			<div id="dateFilter">
				<li>
					<label id="data-label">Date:</label>
					<input id="datePicker" type="text" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser">
				</li>
			</div>
			<div id="yearFilter">
				<li>
					<label id="yearFilterLabel">Year:</label>
					<input id="yearCombobox" class="easyui-combobox" name="yearCombobox"  style="width:120px"
					data-options="valueField:'value',textField:'label',
								 onChange(newValue, oldValue){
								<!--  	alert('Filter-By: '+ $('#filterby-combobox').combobox('getValue')); -->
						 			if($('#filterby-combobox').combobox('getValue')==='MONTH'){
						 			<!-- 	alert('In MONTH: '+newValue); -->
						 				var url = 'get_week_list/month?yearParam='+newValue;
          									$('#month-combobox').combobox('reload', url);
						 			}else if($('#filterby-combobox').combobox('getValue')==='WEEK'){
						 			<!-- 	alert('In WEEK: '+newValue); -->
						 				var url = 'get_week_list/week?yearParam='+newValue;
          									$('#week-combobox').combobox('reload', url);
						 			}
						  											  
						  		}">
				</li>
			</div>
			<div id="monthFilter">
				<li>
					<label id="month-label">Month:</label>
					<input id="month-combobox" class="easyui-combobox" name="month-combobox"  style="width:120px" 
						   data-options="valueField:'value',textField:'label'">
				</li>
			</div>
			<div id="weekFilter">
				<li>
					<label id="week-label">Week:</label>
					<input id="week-combobox" class="easyui-combobox" name="week-combobox"  style="width:120px" 
						   data-options="valueField:'value',textField:'label'">
				</li>
			</div>			
			<li style="list-style-type:none">
<!-- 				alert('  lgaID='+$('#lga_combobox').combobox('getValue')
									+ ', filterBy='+$('#filterby-combobox').combobox('getValue')
									+ ', year='+$('#yearCombobox').combobox('getValue')
									+ ', month='+$('#month-combobox').combobox('getValue')
									+ ', week='+$('#week-combobox').combobox('getValue')
									+ ', dayDate='+$('#datePicker').combobox('getValue')); -->
				<a href="#" id="viewReportLinkBtn" class="easyui-linkbutton"
					data-options="onClick:function(){
								var message='';
								if ($('#lga_combobox').combobox('getValue') == '') {
		   							message=('LGA is Empty');
		  						}else if ($('#filterby-combobox').combobox('getValue') == '') {
		  						 message=('Filter By is Empty');
		  						} else if (($('#yearFilter').is(':visible')) && ($('#yearCombobox').combobox('getValue')) =='' )  {
		  						 message=('Year is Empty');
		  						}else if (($('#dateFilter').is(':visible')) && ($('#datePicker').combobox('getValue')) =='') {
		   							message=('Date is Empty');
		  						} else if (($('#monthFilter').is(':visible')) && ($('#month-combobox').combobox('getValue'))=='') {
		   						message=('Month is Empty');
		  						} else if (($('#weekFilter').is(':visible')) && ($('#week-combobox').combobox('getValue'))=='') {
		  						  message=('Weak is Empty');
		  						}else{
						    $('#LgaStockInconsistencyTable').datagrid({
						    	url: 'get_lga_Inconsistency_report_grid_data?'
							   		+'lgaID='+$('#lga_combobox').combobox('getValue')
							   		+'&filterBy='+$('#filterby-combobox').combobox('getValue')
							   		+'&year='+$('#yearCombobox').combobox('getValue')
							   		+'&month='+$('#month-combobox').combobox('getValue')
							   		+'&week='+$('#week-combobox').combobox('getValue')
							   		+'&dayDate='+$('#datePicker').combobox('getValue'),											    	
								remoteSort : false,
								columns : [ [{field:'LGA_ID',title:'LGA ID',sortable:true,hidden:true},
							             {field:'ITEM_NUMBER',title:'PRODUCT',sortable:true},
							             {field:'WAREHOUSE_CODE',title:'LGA',sortable:true},
							             {field:'STOCK_BALANCE',title:'STOCK BALANCE',sortable:true},
							             {field:'PHYSICAL_STOCK_COUNT',title:'PHYSICAL STOCK COUNT',sortable:true},
							             {field:'PHYSICAL_COUNT_DATE',title:'PHYSICAL COUNT DATE',sortable:true},
							             {field:'DIFFERENCE',title:'DIFFERENCE',sortable:true},
							             {field:'REASON',title:'REASON',sortable:true}							         
									 ] ]
							});
							if($('#lga_combobox').combobox('getText')=='All'){
							$('#LgaStockInconsistencyTable').datagrid('hideColumn', 'ITEM_NUMBER');
							$('#LgaStockInconsistencyTable').datagrid('hideColumn', 'STOCK_BALANCE');
							$('#LgaStockInconsistencyTable').datagrid('hideColumn', 'PHYSICAL_STOCK_COUNT');
							$('#LgaStockInconsistencyTable').datagrid('hideColumn', 'PHYSICAL_COUNT_DATE');
							$('#LgaStockInconsistencyTable').datagrid('hideColumn', 'DIFFERENCE');
							$('#LgaStockInconsistencyTable').datagrid('hideColumn', 'REASON');
							}
							else{
							$('#LgaStockInconsistencyTable').datagrid('hideColumn','WAREHOUSE_CODE');
							}
							if($('#filterby-combobox').combobox('getText')=='DAY'){
								$('#LgaStockInconsistencyTable').datagrid('hideColumn','PHYSICAL_COUNT_DATE');
							}
							
							}
							if(message!=''){
							alertBox(message);
							}
							}"><b>View Report</b></a>
			</li>
		</ul>
	</div>
	<!-- user table -->
	<table id="LgaStockInconsistencyTable" class="easyui-datagrid"
		style="width: 100%; height: 410px" title="LGA Stock Inconsistenccy"
		toolbar="#tb" rownumbers="true" pagination="true" pageSize=30 singleSelect="true"
		striped="true" remoteSort="false">


	</table>

</body>
<script type="text/javascript" src="resources/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript"
	src="resources/easyui/jquery.easyui.min.js"></script>
<script src="resources/js/common.js"></script>
<script src="resources/js/datagrid_agination.js" type="text/javascript"></script>
<script>
$("#filterby-combobox").attr("selectedIndex", -1);
function alertBox(message){
    $.messager.alert('Warning!',message,'warning');
}
loadPaginationForTable(LgaStockInconsistencyTable);
</script>
</html>