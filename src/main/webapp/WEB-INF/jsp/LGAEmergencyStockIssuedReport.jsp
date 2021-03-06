<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LGA Emergency Stock Issued Report</title>

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
			$('#lgaFilter').show();
			$('#filterBy-filter').show();
			$('#dateFilter').hide();
			$('#weekFilter').hide();
			$('#monthFilter').hide();
			$('#yearFilter').hide();
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
			
			$('#lgaFilter').show();
			$('#filterBy-filter').show();
			$('#dateFilter').hide();
			$('#weekFilter').hide();
			$('#monthFilter').hide();
			$('#yearFilter').hide();
			loadStateComboboxList();
			break;
		case "LIO":

			break;
		case "MOH":

			break;
		}
	}	
</script>
</head>
<body style="margin: 0px;" onload="setRole()">

		<!-- status dialog -->
	<div id="stutus_dialog" ></div>
	<div class="report_title" style="text-align: center;font-size: 15px;">LGA Emergency Stock Issued Report</div>
	
	<!-- button bar -->
	<div class="button_bar_for_report" id="button_bar_for_report">
		<ul>
		<li>
		<div id="stateFilter" >
					<label id="state_label">State Store:</label><br>
					<input id="state_combobox"  class="easyui-combobox" name="state_combobox"  style="width:120px" >
		</div>
		</li>
		<li>
					<div id="lgaFilter">
					<label id="lga-label">LGA:</label><br>
					<input id="lga_combobox"  class="easyui-combobox" name="lga_combobox"  style="width:120px">
					</div>
		</li>
		<li>
				<div id="filterBy-filter">
					<label id="filterby-label">Filter By:</label><br>
					<input id="filterby-combobox" class="easyui-combobox" name="filterby-combobox"  style="width:120px">	
				</div>    				
		</li>
				<li>
				<div id="dateFilter">
					<label id="data-label">Date:</label><br>
					<input id="datePicker" type="text" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser">
					</div>
				</li>
				<li>
						<div id="yearFilter">
							<label id="yearFilterLabel">Year:</label><br>
							<input id="yearCombobox" class="easyui-combobox" name="yearCombobox"  style="width:120px;"
							  data-options="valueField:'value',textField:'label'">
						</div>
				</li>
			     <li>
					<div id="monthFilter">
					<label id="month-label">Month:</label><br>
					<input id="month-combobox" class="easyui-combobox" name="month-combobox"  style="width:120px;" 
						   data-options="valueField:'value',textField:'label'">
						   </div>
				</li>
		        <li>
						<div id="weekFilter">
							<label id="week-label">Week:</label><br>
							<input id="week-combobox" class="easyui-combobox" name="week-combobox"  style="width:120px;" 
								   data-options="valueField:'value',textField:'label'">
					 	</div>
				</li>
					
			<li style="list-style-type:none;margin-top: 10px;">
				<a href="#" id="viewReportLinkBtn" class="easyui-linkbutton" onclick="showLgaEmergencyReport()"><b>View Report</b></a>
			</li>
		</ul>
	</div>
	<!-- user table -->
	<table id="lGAEmergencyStockIssueTable" class="easyui-datagrid"
		style="width: 100%; height: 410px" title="LGA Emergency Report"
		 data-options="rownumbers:'true', pagination:'true', pageSize:30, singleSelect:'true', 	striped:'true', remoteSort:'false'">
	</table>
		
	
</body>
<script src="resources/js/jquery-2.2.3.min.js"></script>
<script src="resources/easyui/jquery.easyui.min.js"></script>
<script src="resources/js/common.js"></script>
<script src="resources/js/datagrid_agination.js" type="text/javascript"></script>
<script type="text/javascript">
function alertBox(message){
	  $.messager.alert('Warning!',message,'warning');
}
</script>
<script type="text/javascript">
var lgaId="";
var stateId="";
function loadStateComboboxList(){
	$('#state_combobox').combobox({
		url:"get_state_store_list?option=notAll",
		valueField : 'value',
		width:180,
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
		width:180,
		textField : 'label',
		onSelect : function(lgaId){
			
		}
	});
}
function showLgaEmergencyReport(){
		var message='';
		 if($('#state_combobox').combobox('getValue')==''
				&& '${userBean.getX_ROLE_NAME()}'=='NTO'){
			validate=false;
			message=("State is Empty!");
		}else if($('#lga_combobox').combobox('getValue')==''
			&& !('${userBean.getX_ROLE_NAME()}'=='LIO'
			|| '${userBean.getX_ROLE_NAME()}'=='MOH')){
		message=("LGA is Empty!");
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
			 lgaId=$('#lga_combobox').combobox('getValue');
			 if('${userBean.getX_ROLE_NAME()}'=='MOH'
					|| '${userBean.getX_ROLE_NAME()}'=='LIO'){
				lgaId='${userBean.getX_WAREHOUSE_ID()}';
			}else if('${userBean.getX_ROLE_NAME()}'=='NTO'){
				 stateId=$('#state_combobox').combobox('getValue');
				  
			}//fot state id when national login
	  $('#lGAEmergencyStockIssueTable').datagrid({
    	url: 'get_emergency_stock_issue_report_grid_data?'
	   		+'lgaID='+lgaId
	   		+'&stateId='+stateId
	   		+'&filterBy='+$('#filterby-combobox').combobox('getValue')
	   		+'&year='+$('#yearCombobox').combobox('getValue')
	   		+'&month='+$('#month-combobox').combobox('getValue')
	   		+'&week='+$('#week-combobox').combobox('getValue')
	   		+'&dayDate='+$('#datePicker').combobox('getValue'),											    	
	   		columns : [ [	{field:'LGA_ID',title:'LGA_ID',sortable:true,hidden:true},
				             {field:'LGA_NAME',title:'LGA Name',sortable:true},
				             {field:'ITEM_ID',title:'ITEM_ID',sortable:true,hidden:true},
				             {field:'ITEM_NUMBER',title:'Product Number',sortable:true},
				             {field:'EMERGENCY_ISSUED_QUANTITY',title:'Emergency Issued Quantity',sortable:true},
				             {field:'CUSTOMER_NAME',title:'Health Facility Name',sortable:true},
				             {field:'ALLOCATION_DATE',title:'Stock Issued Date',sortable:true},
				             {field:'WEEK',title:'Week',sortable:true,hidden:true}
						 ] ]
	});
		 if($('#lga_combobox').combobox('getText')=='All'){
				$('#lGAEmergencyStockIssueTable').datagrid('hideColumn','ITEM_NUMBER');
					$('#lGAEmergencyStockIssueTable').datagrid('hideColumn','CUSTOMER_NAME');
					$('#lGAEmergencyStockIssueTable').datagrid('hideColumn','ALLOCATION_DATE');
					$('#lGAEmergencyStockIssueTable').datagrid('hideColumn','EMERGENCY_ISSUED_QUANTITY');
				}else{
					$('#lGAEmergencyStockIssueTable').datagrid('hideColumn','LGA_NAME');
				}
				
				if($('#filterby-combobox').combobox('getText')=='DAY'){
					$('#lGAEmergencyStockIssueTable').datagrid('hideColumn','ALLOCATION_DATE');
				}
	
	}
	if(message!=''){
	alertBox(message);
	}
}
$('#filterby-combobox').combobox({
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
			$('#yearCombobox').combobox('clear');
			$('#yearCombobox').combobox('reload', url);
		}else if(newValue==='MONTH'){
			$('#dateFilter').hide();
			$('#weekFilter').hide();
			$('#monthFilter').show();
			$('#yearFilter').show();
			var url = 'get_year_list';
			$('#month-combobox').combobox('clear');	
			$('#yearCombobox').combobox('reload', url);
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
	}
});

$('#yearCombobox').combobox({
	valueField:'value',
	textField:'label',
	onChange:function(newValue, oldValue){
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
											  
		}
});

loadPaginationForTable(lGAEmergencyStockIssueTable);
</script>
</html>