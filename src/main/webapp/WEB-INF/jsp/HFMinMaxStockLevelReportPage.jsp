<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>health Facility Min Max Stock Balance Report</title>
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
			break;
		case "SIO":
		break;
		case "SIFP":
			break;
		case "NTO":
			break;
		case "LIO":
			$('#dateFilter').hide();
			$('#yearFilter').hide();
			$('#monthFilter').hide();
			loadHfDrpdn('${userBean.getX_WAREHOUSE_ID()}');
			break;
		case "MOH":
			$('#dateFilter').hide();
			$('#yearFilter').hide();
			$('#monthFilter').hide();
			loadHfDrpdn('${userBean.getX_WAREHOUSE_ID()}');
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
	<div class="report_title" style="text-align: center;font-size: 15px;">Health Facility Min Max Stock Balance Report</div>
	<!-- button bar -->

	<div class="button_bar_for_report" id="button_bar_for_report">
		<ul>
			<li>
			<div id="hf_combobox_div">
			<label>Health Facility Filter:</label><br> 
			<select id="hf_combobox"
				class="easyui-combobox" name="hf_id" style="width: 200px;">
			</select>
			</div>
			</li>
			<li>
			<div id="allocation_type_filter" >
			<label id="allocation_type_label">Allocation Type:</label><br>
				<input id="allocation_type_combobox" class="easyui-combobox" name="allocation_type_combobox"  style="width:120px" >
			</div>
			</li>
			<li>
			<div id="min_max_filter" >
			<label id="min_max_label">Min./Max. Filter:</label><br>
				<input id="min_max_combobox" class="easyui-combobox" name="min_max_combobox"  style="width:120px" >
			</div>
			</li>
			<li>
			<div id="filterBy-filter">
			<label id="filterby-label">Filter By:</label></br>
				<input id="filterby-combobox" class="easyui-combobox" name="filterby-combobox"  style="width:120px" >
			</div>
			</li>
			<li>
			<div id="dateFilter">
			<label id="data-label">Date:</label></br>
					<input id="datePicker" type="text" class="easyui-datebox" style="width: 120px;">
			</div>
			</li>
			<li>
			<div id="yearFilter">
			<label id="yearFilterLabel">Year:</label></br>
					<input id="yearCombobox" class="easyui-combobox" name="yearCombobox"  style="width:80px" onchange="onYearChange()">
			</div>
			</li>
			<li>
				<div id="monthFilter">
					<label id="month_label">Month:</label></br>
					<input id="month_combobox" class="easyui-combobox" name="month_combobox"  style="width:80px"  >
				</div>
			</li>
			<li><div style="padding-top: 15px;">
			<a class="easyui-linkbutton" onclick="showHFMinMaxStockLevel()"> <b>View Report</b>
			</a></div>
			</li>
		</ul>
	</div>
	<!-- user table -->
	<table id="HFMinMaxStockLevelTable" class="easyui-datagrid"
		style="width: 100%; height: 390px" data-options="title:'HF Bin Card',
		toolbar:'#tb',rownumbers:'true',pagination:'true',pageSize:30,singleSelect:'true',
		striped:'true',remoteSort:'false'">
	</table>

	
	
	
	</body>
<script type="text/javascript" src="resources/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript"
	src="resources/easyui/jquery.easyui.min.js"></script>
	<script src="resources/js/common.js"></script>
	<script src="resources/js/datagrid_agination.js" type="text/javascript"></script>
<script type="text/javascript">
function showHFMinMaxStockLevel(){
	var message="";
	var validate=true; 
	if($('#allocation_type_combobox').combobox('getValue')==''){
		validate=false;
		message="Allocation type  is Empty!";
	}else if($('#min_max_combobox').combobox('getValue')==''){
		validate=false;
		message="Min./Max Filter is Empty!";
	}else if($('#filterby-combobox').combobox('getValue')==''){
		validate=false;
		message="Filter by  is Empty!";	
	}else if (($('#yearFilter').is(':visible')) && ($('#yearCombobox').combobox('getValue')) =='' )  {
		message=('Year is Empty');
		validate=false;
	}else if (($('#monthFilter').is(':visible')) && ($('#month_combobox').combobox('getValue'))=='') {
		if($('#filterby-combobox').combobox('getValue')=='MONTH'){
			 message=('Month is Empty');
		}else{
			 message=('Weak is Empty');
		}
		 validate=false;
	}else if (($('#dateFilter').is(':visible')) && ($('#datePicker').combobox('getValue')) =='') {
	message=('Date is Empty');
	validate=false;
	} 
	if(message!=''){
		alertBox(message);
	}
	if(validate){
		var hfId=$('#hf_combobox').combobox('getValue');
		var allocationType=$('#allocation_type_combobox').combobox('getValue');
		var minMax=$('#min_max_combobox').combobox('getValue');
		var filterBy=$('#filterby-combobox').combobox('getValue');
		var year=$('#yearCombobox').combobox('getValue');
		var month=$('#month_combobox').combobox('getValue');
		var week=$('#month_combobox').combobox('getValue');
		var day=$('#datePicker').combobox('getValue');
		$('#HFMinMaxStockLevelTable').datagrid({
			url : 'get_hf_min_max_stock_level_report_data',
			remoteSort : false,
			queryParams:{hfId:hfId,allocationType:allocationType,minMax:minMax,
				filterBy:filterBy,year:year,
				month:month,week:week,day:day},
			columns : [ [{field:'CUSTOMER_ID',title:'CUSTOMER_ID',hidden:true},
			             {field:'CUSTOMER_NAME',title:'Health Facility',sortable:true},
			             {field:'ITEM_ID',title:'ITEM_ID',sortable:true,hidden:true},
			             {field:'ITEM_NUMBER',title:'Product',sortable:true},
			             {field:'STOCK_BALANCE',title:'Stock Balance',sortable:true,align:'center'}, 
			             {field:'MIN_STOCK',title:'Min Stock Qty',sortable:true,align:'center'}, 
			             {field:'MAX_STOCK',title:'Max Stock Qty',sortable:true,align:'center'}, 
			             {field:'STOCK_RECEIVED_DATE',title:'Stock Received Date',sortable:true,align:'center'}, 
			             {field:'CONSUMPTION_ID',title:'CONSUMPTION_ID',sortable:true,hidden:true},
			            {field:'WAREHOUSE_ID',title:'WAREHOUSE_ID',sortable:true,hidden:true},
			            {field:'DIFFERENCE',title:'DIFFERENCE',sortable:true} 

	 ] ]
		});
		if($('#hf_combobox').combobox('getText')=='All'){
			$('#HFMinMaxStockLevelTable').datagrid('hideColumn', 'ITEM_NUMBER');
			$('#HFMinMaxStockLevelTable').datagrid('hideColumn', 'STOCK_BALANCE');
			$('#HFMinMaxStockLevelTable').datagrid('hideColumn', 'MIN_STOCK');
			$('#HFMinMaxStockLevelTable').datagrid('hideColumn', 'MAX_STOCK');
			$('#HFMinMaxStockLevelTable').datagrid('hideColumn', 'STOCK_RECEIVED_DATE');
			$('#HFMinMaxStockLevelTable').datagrid('hideColumn', 'DIFFERENCE');
		}else{
			if(minMax==='Minimum Stock'){
				$('#HFMinMaxStockLevelTable').datagrid('showColumn', 'STOCK_BALANCE');
				$('#HFMinMaxStockLevelTable').datagrid('showColumn', 'MIN_STOCK');
				$('#HFMinMaxStockLevelTable').datagrid('showColumn', 'DIFFERENCE');
				$('#HFMinMaxStockLevelTable').datagrid('showColumn', 'STOCK_RECEIVED_DATE');
				$('#HFMinMaxStockLevelTable').datagrid('hideColumn', 'MAX_STOCK');
			}else{
				$('#HFMinMaxStockLevelTable').datagrid('showColumn', 'STOCK_BALANCE');
				$('#HFMinMaxStockLevelTable').datagrid('showColumn', 'MAX_STOCK');
				$('#HFMinMaxStockLevelTable').datagrid('showColumn', 'DIFFERENCE');
				$('#HFMinMaxStockLevelTable').datagrid('showColumn', 'STOCK_RECEIVED_DATE');
				$('#HFMinMaxStockLevelTable').datagrid('hideColumn', 'MIN_STOCK');
			}
			$('#HFMinMaxStockLevelTable').datagrid('showColumn', 'ITEM_NUMBER');
			$('#HFMinMaxStockLevelTable').datagrid('hideColumn', 'CUSTOMER_NAME');
		}
	}
}
function alertBox(message){
	  $.messager.alert('Warning!',message,'warning');
}
</script>
<script type="text/javascript">
function loadHfDrpdn(lgaId){
	$('#hf_combobox').combobox('clear');
	$('#hf_combobox').combobox({
		url : 'getHflist?lgaid=' + lgaId+'&option=All',
		valueField : 'value',
		textField : 'label',
		onLoadSuccess:function(){
			$('#hf_combobox').combobox('select','null');	
		}
	});
}		
	
	$('#min_max_combobox').combobox({
			valueField : 'value',
			textField : 'label',
			data:[{label:'Minimum Stock',value:'Minimum Stock'},{label:'Maximum Stock',value:'Maximum Stock'}],
			onSelect:function(filterBy){
				$('#filterby-combobox').combobox('clear');
				
			} 
		});
	
	$('#allocation_type_combobox').combobox({
		   valueField : 'value',
		   textField : 'label',
		   data:[{label:'Monthly',value:'Monthly'},{label:'Weekly',value:'Weekly'}],
		   onSelect:function(allcationType){
		    
		   } 
		  });
	
		  $('#yearCombobox').combobox({
		   url : 'get_year_list',
		   valueField : 'value',
		   textField : 'label',
		   onSelect : function(rec) {
			   var url="";
			   if($('#filterby-combobox').combobox('getText')=='MONTH'){
				   url="get_week_list/month?yearParam="+ rec.value;
			   }else if($('#filterby-combobox').combobox('getText')=='WEEK'){
				   url="get_week_list/week?yearParam="+ rec.value;
			   }
		    $('#month_combobox').combobox({
		     url : url,
		     valueField : 'value',
		     textField : 'label'
		    });
		   }
		  });
	$('#filterby-combobox').combobox({
		   valueField : 'value',
		   textField : 'label',
		   data:[{label:'YEAR',value:'YEAR'},{label:'MONTH',value:'MONTH'},
		         {label:'WEEK',value:'WEEK'}, {label:'DAY',value:'DAY'}],
		   onSelect:function(filterBy){
		    if(filterBy.label=='YEAR'){
		     $('#yearCombobox').combobox('clear');
		     $('#monthFilter').hide();
		     $('#yearFilter').show();
		     $('#dateFilter').hide();
		    }else if(filterBy.label=='MONTH'){
		    	  $('#month_label').text('Month:');
			     $('#datePicker').hide();
			     $('#yearCombobox').combobox('clear');
			     $('#month_combobox').combobox('clear');
			      $('#yearFilter').show();
			     $('#monthFilter').show();
			}else if(filterBy.label=='WEEK'){
				  $('#month_label').text('Week:');
				  $('#datePicker').hide();
				  $('#yearCombobox').combobox('clear');
				     $('#month_combobox').combobox('clear');
				  $('#yearFilter').show();
				  $('#monthFilter').show();
			    }else if(filterBy.label=='DAY'){
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
			   if($('#filterby-combobox').combobox('getText')=='MONTH'){
				   url="get_week_list/month?yearParam="+ rec.value;
			   }else if($('#filterby-combobox').combobox('getText')=='WEEK'){
				   url="get_week_list/week?yearParam="+ rec.value;
			   }
		    $('#month_combobox').combobox({
		     url : url,
		     valueField : 'value',
		     textField : 'label'
		    });
		   }
		  });
		  
		  loadPaginationForTable(HFMinMaxStockLevelTable);
</script>
</html>