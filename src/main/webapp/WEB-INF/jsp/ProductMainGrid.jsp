<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product Overview Page</title>
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
			$('#overViewBtn').hide();
			$('#state_combobox_div').hide();
			loadlgaList({value:'${userBean.getX_WAREHOUSE_ID()}'});
			break;
		case "SIO":
			$('#addBtn').hide();
			$('#editBtn').hide();
			$('#state_combobox_div').hide();
			loadlgaList({value:'${userBean.getX_WAREHOUSE_ID()}'});
			break;
		case "SIFP":
			$('#addBtn').hide();
			$('#editBtn').hide();
			$('#state_combobox_div').hide();
			loadlgaList({value:'${userBean.getX_WAREHOUSE_ID()}'});
			break;
		case "NTO":
			$('#addBtn').hide();
			$('#editBtn').hide();
			$('#state_checkbox_div').hide();
			loadStateStoreList();
			break;
		case "LIO":
			$('#state_checkbox_div').hide();
			$('#state_combobox_div').hide();
			$('#lga_combobox_div').hide();
			$('#addBtn').hide();
			$('#editBtn').hide();
			loadProductOverViewData('${userBean.getX_WAREHOUSE_ID()}');
			break;
		case "MOH":
			$('#state_checkbox_div').hide();
			$('#state_combobox_div').hide();
			$('#lga_combobox_div').hide();
			$('#addBtn').hide();
			$('#editBtn').hide();
			loadProductOverViewData('${userBean.getX_WAREHOUSE_ID()}');
			break;
		}
		/* document.getElementById("common_lable").innerHTML = "Product Overview";
		if(user=="NTO"){
			document.getElementById("user").innerHTML = "User: National Admin";
			document.getElementById("warehouse_name").innerHTML ="National: "+ '${userBean.getX_WAREHOUSE_NAME()}';
		}else if(user=="SIO" || user=="SCCO" || user=="SIFP"){
			document.getElementById("user").innerHTML = "User: "+user+" "+'${userBean.getX_WAREHOUSE_NAME()}' ;
			document.getElementById("warehouse_name").innerHTML ="State :"+ '${userBean.getX_WAREHOUSE_NAME()}';
		}else if(user=="LIO" || user=="MOH"){
			document.getElementById("user").innerHTML = "User: "+user+'${userBean.getX_WAREHOUSE_NAME()}' ;
			document.getElementById("warehouse_name").innerHTML ="LGA :"+ '${userBean.getX_WAREHOUSE_NAME()}';
		} */

	}
</script>
<style type="text/css">
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
#product_name_label,#product_type_label,#product_catogory_label,#primary_uom_label,
#target_coverage_label,#start_date_label{
font-weight: bold;
}
</style>
</head>
<body style="margin: 0px;" onload="setRole()">

	
	<!-- headr of page -->
	<%-- <jsp:include page="headerforpages.jsp"></jsp:include> --%>
									<!-- status dialog -->
	<div id="stutus_dialog" ></div>
	<!-- button bar -->

	<div class="button_bar" id="button_bar">
		<ul>
		<li><a id="addBtn" class="w3-btn w3-ripple" onclick="addProduct()"> <img alt="add"
					src="resources/images/file_add.png">Add
			</a></li>
			<li><a id="editBtn" class="w3-btn w3-ripple" onclick="editProduct(this.id)"> <img alt="edit"
					src="resources/images/file_edit.png">Edit
			</a></li>
			<li><a id="overViewBtn" class="w3-btn w3-ripple" onclick="editProduct(this.id)"> <img alt="search"
					src="resources/images/file_search.png">Product Overview
			</a></li>
			<li><a class="w3-btn w3-ripple" onclick="handleHistory()"> <img alt="history"
					src="resources/images/file_history.png">History
			</a></li>
			<li><a class="w3-btn w3-ripple" href="product_list_export"> <img alt="export"
					src="resources/images/Export_load_upload.png">Export 
			</a></li>
		</ul>
	</div>
	
	<!-- filters -->
		<div id="filters" style="padding-left:10px;;display: inline-flex;">
		<div id="state_checkbox_div" style="margin-top: 20px;">
		<input style="width: 15px;height: 15px;" type="checkbox" value="${userBean.getX_WAREHOUSE_ID()}" id="state_checkbox" 
		onchange="oncheckStateCheckBox()"><b>${userBean.getX_WAREHOUSE_NAME()}</b>
		</div>
		<div id="state_combobox_div">
			<span>State Store:</span><br>
			 <select id="state_combobox" class="easyui-combobox"
				name="state_combobox" style="width:200px;">
			</select>
		</div>&nbsp;&nbsp;&nbsp;
		<div id="lga_combobox_div">
			<span>LGA:</span><br>
			 <select id="lga_combobox" class="easyui-combobox"
				name="lga_combobox" style="width:200px;">
			</select>
		</div> &nbsp;&nbsp;

	    <div style="padding-top: 12px;">
			 <a class="easyui-linkbutton" 
				onclick="loadProductOverViewData()">Refresh </a>
			 </div>
		</div>
		<!-- filters end here -->
		
	<!-- user table -->
	<table id="productOverViewTable" class="easyui-datagrid"
		style="width: 100%; height: 430px" title="Product Overview"
		data-options="toolbar:'#tb', rownumbers:'true', pagination:'true',pageSize:30, singleSelect:'true',
		striped:'true', remoteSort:'false'">
	</table>
	
	

	
	<!--  footer page -->

	<%-- <jsp:include page="footer-for-page.jsp"></jsp:include> --%>
	
	<!-- Product Add/Edit form -->
	
	 <div id="form_dialog" class="easyui-dialog" style="width:600px;height:400px;padding: 5px;"
                closed="true" buttons="#form_buttons">
            <f:form id="add_edit_form" method="post" commandName="productBean">
            <table cellspacing="10px;">
            <tr>
            <td>
	            <div id="product_name_div">
	                    <label id="product_name_label">*Product Name:</label><br>
	                    <f:input  id="product_name_textbox"  class="easyui-textbox" path="x_ITEM_NAME"/>
	                    <input type="hidden" id="item_number_field" name="x_ITEM_NUMBER">
	             </div>
            </td>
             <td colspan="2">
             <div class="description_div">
                    <label id="description_label">Description:</label><br>
                    <f:textarea cssStyle="height:60px; width:300px;" id="description_textbox" data-options="multiline:true"  class="easyui-textbox" path="x_ITEM_DESCRIPTION"/>
              </div>
             </td>
            </tr>
             <tr>
            <td>
              		<div id="product_type_div">
		             <label id="product_type_label">*Product Type:</label><br>
	                   <f:select id="product_type_combobox_form"  class="easyui-combobox" cssStyle="width:150px;" path="x_ITEM_TYPE_ID"/>
	                </div>
             </td>
             <td>
              		<div id="product_catogory_div">
		             <label id="product_catogory_label">*Product Category:</label><br>
	                   <f:select id="product_catogory_combobox_form"  class="easyui-combobox" cssStyle="width:150px;" path="x_CATEGORY_ID"/>
	                </div>
             </td>
             <td>
              		<div id="primary_uom_div">
		             <label id="primary_uom_label">*Primary UOM:</label><br>
	                   <f:select id="primary_uom_combobox_form"  class="easyui-combobox" cssStyle="width:150px;" path="x_PRIMARY_UOM"/>
	                </div>
             </td>
            </tr>
            <tr>
	             <td>
	               <div class="doses_per_schedule_div">
	                    <label id="doses_per_schedule_label" class="label-top">Doses per Schedule:</label><br>
	                    <f:input id="doses_per_schedule_textbox"  
	                    class="easyui-textbox" path="x_DOSES_PER_SCHEDULE" />
	                </div>
		            
               </td>
	              <td>
	               <div class="vaccine_persentation_div">
	                    <label id="vaccine_persentation_label" class="label-top">Vaccine Persentation:</label><br>
	                    <f:input id="vaccine_persentationl_textbox"  
	                    class="easyui-textbox" path="x_VACCINE_PRESENTATION" />
	                </div>
		            
               </td>
                 <td>
	               <div class="target_coverage_div">
	                    <label id="target_coverage_label" class="label-top">*Target Coverage:</label><br>
	                    <f:input id="target_coverage_textbox"  
	                    class="easyui-textbox" path="x_TARGET_COVERAGE" />
	                </div>
		            
               </td>
            </tr>
              <tr>
               <td>
	               <div id="status_div">
	                    <f:checkbox    id="status_checkbox" path="x_STATUS" value="A"/>Status
	              </div>
               </td>
	           <td>
	               <div class="wastage_rate_div">
	                    <label id="wastage_rate_label" class="label-top">Wastage Rate:</label><br>
	                    <f:input id="wastage_rate_textbox"  
	                    class="easyui-textbox" path="x_WASTAGE_RATE" onchange="changeData()"/>
	                </div>
		            
               </td>
	              <td>
	               <div class="wastage_factor_div">
	                    <label id="wastage_factor_label" class="label-top">Wastage Factor:</label><br>
	                    <f:input id="wastage_factor_textbox"  
	                    class="easyui-textbox" data-options="readonly:'true'" path="x_WASTAGE_FACTOR" />
	                </div>
		            
               </td>
            </tr>
           
      
            <tr>
            <td>
	            <div id="start_date_div">
	             <label id="start_date_label">*Start date:</label><br>
                    <f:input id="start_date" data-options="formatter:myformatter,parser:myparser" class="easyui-datebox" width="120px" path="x_START_DATE" />
                </div>
            </td>
             <td>
              <div id="end_date">
                  <label id="end_date_label">End date:</label><br>
                    <f:input id="end_date" data-options="formatter:myformatter,parser:myparser" class="easyui-datebox" width="120px;" path="x_END_DATE"/>
                </div>
             </td>
                <td>
	            <div id="expiration_date_div">
	             <label id="expiration_date_label">Expiration date:</label><br>
                    <f:input id="expiration_date" data-options="formatter:myformatter,parser:myparser" class="easyui-datebox" width="120px" path="x_EXPIRATION_DATE" />
                </div>
            </td>
            </tr>          
            </table>
            </f:form>
        </div>
        <!-- form button  -->
         <div id="form_buttons">
            <a href="javascript:void(0)" id="saveBtn" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveProduct()" style="width:90px">Save</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#form_dialog').dialog('close')" style="width:90px">Cancel</a>
        </div>
        
		<!-- history product div -->

        <div id="history_dialog" class="easyui-dialog" style="width:420px;height:250px;padding:10px" closed="true">
        <table align="center">
        <tr>
	        <td>Created By:</td>
	        <td><label  id="createdBylabel"></label></td>
	        </tr>
	        <tr>
	        <td>Created On:</td>
	        <td><label  id="createdOnlabel"></label></td>
	        </tr>
	      <tr>
	        <td>Updated By:</td>
	        <td><label  id="updatedBylabel"></label></td>
	      </tr>
	      <tr>
	        <td>Last updated On</td>
	        <td><label  id="updatedOnlabel"></label></td>
	      </tr>
	      <tr>
	      	<td colspan="2" align="center" style="">
	      		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:$('#history_dialog').dialog('close')" style="width:90px">Ok</a>
	      	</td>
	      	<td>
	      	</td>
	      </tr>
        </table>
        </div>
        			<!-- add/edit/Device form -->
	
<div id="device_association_form_dialog" class="easyui-dialog" style="width:550px;height:220px;padding: 5px;"
                closed="true" buttons="#form_buttons_for_add_device_form">
 
  <f:form method="POST" id="add_edit_form_for_deviceassociation" commandName="deviceAssBean" action="savedeviceAsso" >
<table align="center">
    <tr>
        <td style="font-weight: bold;">*Product :</td>
        <td><f:select path="x_PRODUCT_ID" class="easyui-combobox" id="product_combo"  cssStyle="width:170px;"/>
         <label style="color: red"  id="empty_prod_combo"></label>
        <f:input type="text" id="action" style="display: none" path="x_ACTION" />
        </td>
    </tr>
    <tr>
    <td>Safety Box :</td>
        <td>
		<f:checkbox id="safty_box_checkbox" path="x_SAFTY_BOX" value="Y" checked="checked"></f:checkbox>
		</td>
        </tr>
        <tr>
        <td style="font-weight: bold;">*AD Syringe :</td>
        <td>
            <f:select path="x_AD_SYRINGE_ID" class="easyui-combobox" id="ad_srng_combo"  cssStyle="width:170px;"/>
            <span style="color: red" id="empty_ad_combo"></span>
        </td>
          
    </tr>
    <tr>
    <td >RECONSTITUTE SYRNG NAME :</td>
        <td>
            <f:select path="x_RECONSTITUTE_SYRNG_ID" class="easyui-combobox" id="recon_srng_combo"  cssStyle="width:170px;"/>
              <span style="color: red" id="empty_recon_combo"></span>
        </td>
    </tr>
</table>
</f:form>
</div>
	 <!-- form button  -->
         <div id="form_buttons_for_add_device_form">
            <a href="javascript:void(0)" id="saveBtn" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveDeviceAssociation()" style="width:90px">Save</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#device_association_form_dialog').dialog('close')" style="width:90px">Cancel</a>
        </div>
         <!-- loder div -->
		<div style="display: none;" id="loader_div" class="loader_div">
			<div class="loader" id="loader_show">
			</div>
		</div>

</body>
<script type="text/javascript" src="resources/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript"
	src="resources/easyui/jquery.easyui.min.js"></script>
<script src="resources/js/common.js" type="text/javascript"></script>
<script src="resources/js/datagrid_agination.js" type="text/javascript"></script>
<script type="text/javascript">
function oncheckStateCheckBox(){
if($('#lga_combobox').combobox('getValue')!="" && $('#state_checkbox').is(':checked'))	{
	$('#lga_combobox').combobox('clear');
}
}
function loadProductOverViewData(warehouse_id){
	var message="";
	var validate=true;
	if('${userBean.getX_ROLE_NAME()}'=='NTO'){
		if( $('#state_combobox').combobox('getValue')=='' 
				&& $('#lga_combobox').combobox('getValue')==''){
			validate=false;
			message="Please Select Filter!";
		}else{
			if($('#state_combobox').combobox('getValue')!='' &&
					$('#lga_combobox').combobox('getValue')==''){
				warehouse_id= $('#state_combobox').combobox('getValue');
			}else{
				warehouse_id= $('#lga_combobox').combobox('getValue');
			}
		}
	}else if('${userBean.getX_ROLE_NAME()}'=='SCCO'
			|| '${userBean.getX_ROLE_NAME()}'=='SIO'
			|| '${userBean.getX_ROLE_NAME()}'=='SIFP'){
		if(!$('#state_checkbox').is(':checked') 
				&& $('#lga_combobox').combobox('getValue')==""){
			validate=false;
			alert("Select Filter!");		
		}else {
			if($('#state_checkbox').is(':checked')){
				warehouse_id=$('#state_checkbox').val();
				if('${userBean.getX_ROLE_NAME()}'=='SCCO'){
					$('#editBtn').show();
					$('#overViewBtn').hide();
				}
				
			}
			if($('#lga_combobox').combobox('getValue')!=''){
				warehouse_id=$('#lga_combobox').combobox('getValue');
				$('#editBtn').hide();
				$('#overViewBtn').show();
			}
		}
	}
	if(message.length!=0){
		alertBox(message);
	}
	if(validate){
		$('#productOverViewTable').datagrid({
			url : 'get_product_main_grid_data',
			remoteSort : false,
			queryParams:{warehouse_id:warehouse_id},
			columns : [ [ {field:'ITEM_ID',title:'ITEM ID',sortable:true,hidden:true},
			              {field:'ITEM_NUMBER',title:'Product Short Name'},
			              {field:'ITEM_NAME',title:'ITEM_NAME',hidden:true},
			              {field:'ITEM_DESCRIPTION',title:'Product Description',sortable:true},
			              {field:'ITEM_TYPE_CODE',title:'ITEM TYPE CODE',sortable:true,hidden:true},
			              {field:'ITEM_TYPE_NAME',title:'Product Type',hidden:true},
			              {field:'ITEM_TYPE_ID',title:'ITEM_TYPE ID',hidden:true},
			              {field:'WAREHOUSE_ID',title:'WAREHOUSE ID',sortable:true,hidden:true},
			              {field:'WAREHOUSE_CODE',title:'WAREHOUSE CODE',sortable:true,hidden:true},
			              {field:'WAREHOUSE_NAME',title:'WAREHOUSE NAME',sortable:true,hidden:true},
			              {field:'ITEM_SOURCE_TYPE',title:'ITEM SOURCE TYPE',sortable:true,hidden:true},
			              {field:'CATEGORY_ID',title:'CATEGORY_ID',sortable:true,hidden:true},
			              {field:'CATEGORY_CODE',title:'Category Code',sortable:true,hidden:true},
			              {field:'CATEGORY_NAME',title:'CATEGORY NAME',sortable:true,hidden:true},
			              {field:'CATEGORY_DESCRIPTION',title:'CATEGORY DESCRIPTION',sortable:true,hidden:true},
			              {field:'SOURCE_CODE',title:'SOURCE CODE',sortable:true,hidden:true},
			              {field:'CATEGORY_TYPE_ID',title:'CATEGORY TYPE ID',sortable:true,hidden:true},
			              {field:'CATEGORY_TYPE_CODE',title:'CATEGORY TYPE CODE',sortable:true,hidden:true},
			              {field:'CATEGORY_TYPE_NAME',title:'CATEGORY TYPE NAME',sortable:true,hidden:true},
			              {field:'DEFAULT_CATEGORY_ID',title:'DEFAULT CATEGORY ID',sortable:true,hidden:true},
			              {field:'TRANSACTION_BASE_UOM',title:'Primary UOM',sortable:true},
			              {field:'TARGET_COVERAGE',title:'Target Coverage',sortable:true},
			              {field:'VACCINE_PRESENTATION',title:'Vaccine Presentation',sortable:true},
			              {field:'YIELD_PERCENT',title:'Wastage Rate',sortable:true},
			              {field:'WASTAGE_FACTOR',title:'Wastage Factor',sortable:true},
			              {field:'DOSES_PER_SCHEDULE',title:'Doses Per Schedule',sortable:true},
			              {field:'EXPIRATION_DATE',title:'Expiration Date',sortable:true},
			 			  {field:'STATUS',title:'Status',sortable:true},
			              {field:'START_DATE',title:'Start Date',sortable:true},
			              {field:'END_DATE',title:'END DATE',sortable:true,hidden:true},
			              {field:'CREATED_BY',title:'CREATED BY',sortable:true,hidden:true},
			              {field:'CREATED_BY_NAME',title:'CREATED BY',sortable:true,hidden:true},
			              {field:'CREATED_ON',title:'CREATED ON',sortable:true,hidden:true},
			              {field:'UPDATED_BY',title:'UPDATED BY',sortable:true,hidden:true},
			              {field:'UPDATED_BY',title:'UPDATED BY_NAME',sortable:true,hidden:true},
			              {field:'LAST_UPDATED_ON',title:'LAST UPDATED ON',sortable:true,hidden:true},
			             
			              
			               ] ]
		});
	}
	
}
</script>
<script type="text/javascript">

	function addProduct(){
		submitType="add";
		var validate=true;
	    $('#form_dialog').dialog('open').dialog('center').dialog('setTitle','Add Product');
	    $('#add_edit_form').form('clear');
	    $("#status_checkbox").prop("checked", true);
	    $('#start_date').datebox('setValue',formateDate(new Date()));
	    $('#add_edit_form').attr('action','save_addedit_product?action=add');
	    if('${userBean.getX_ROLE_NAME()}'=='NTO'){
	      loadFormComboboxListsForForm('');
	    }else if('${userBean.getX_ROLE_NAME()}'=='SCCO'){	    	
	         loadFormComboboxListsForForm('');
	    }
	}
	
	function editProduct(buttonId){
		 $('#add_edit_form').form('clear');
		if(buttonId=='editBtn'){
			$('#saveBtn').linkbutton('enable',true);
		}else{
			$('#saveBtn').linkbutton('disable',true);
		}
		submitType="edit";
		var row = $('#productOverViewTable').datagrid('getSelected');
		 if (row){
			 loadFormComboboxListsForForm(row);
	    	if(buttonId=='editBtn'){
	        $('#form_dialog').dialog('open').dialog('center').dialog('setTitle','Edit Product');
	    	}else{
	    		  $('#form_dialog').dialog('open').dialog('center').dialog('setTitle','OverView Of Product');
	    	}
	    	$('#product_name_textbox').textbox('setValue',row.ITEM_NAME);
	        $('#product_name_textbox').textbox('setText',row.ITEM_NAME);
	        $('#item_number_field').val(row.ITEM_NUMBER);
	        $('#item_number_field').text(row.ITEM_NUMBER);
	        $('#description_textbox').textbox('setValue',row.ITEM_DESCRIPTION);
	        $('#description_textbox').textbox('setText',row.ITEM_DESCRIPTION);
	        $('#product_type_combobox_form').combobox('setValue',row.ITEM_TYPE_ID);
	        $('#product_type_combobox_form').combobox('setText',row.ITEM_TYPE_NAME);
	        $('#primary_uom_combobox_form').combobox('setValue',row.TRANSACTION_BASE_UOM);
	        $('#primary_uom_combobox_form').combobox('setText',row.TRANSACTION_BASE_UOM);
	        $('#doses_per_schedule_textbox').textbox('setValue',row.DOSES_PER_SCHEDULE);
	        $('#doses_per_schedule_textbox').textbox('setText',row.DOSES_PER_SCHEDULE);
	        $('#vaccine_persentationl_textbox').textbox('setValue',row.VACCINE_PRESENTATION);
	        $('#vaccine_persentationl_textbox').textbox('setText',row.VACCINE_PRESENTATION);
	        if(row.STATUS=='A'){
	        	$("#status_checkbox").prop("checked", true);
	        }else{
	        	$("#status_checkbox").prop("checked", false);
	        }
	    	$('#db_id_field').val(row.DB_ID);
	       	$('#db_id_field').text(row.DB_ID);
	        $('#target_coverage_textbox').textbox('setValue',row.TARGET_COVERAGE);
	        $('#target_coverage_textbox').textbox('setText',row.TARGET_COVERAGE);
	        $('#wastage_rate_textbox').textbox('setValue',row.YIELD_PERCENT);
	        $('#wastage_rate_textbox').textbox('setText',row.YIELD_PERCENT);
	        $('#wastage_factor_textbox').textbox('setValue',row.WASTAGE_FACTOR);
	        $('#wastage_factor_textbox').textbox('setText',row.WASTAGE_FACTOR);
	        
	     
	        var dates=formateDate(row.START_DATE);
	        $('#start_date').datebox('setValue',dates);
	        if(!isNaN(row.END_DATE)){
	        	dates=formateDate(row.END_DATE);
	        	$('#end_date').datebox('setValue',dates);
	        }
	        if(row.EXPIRATION_DATE!=null){
	        	dates=formateDate(row.EXPIRATION_DATE);
	        	$('#expiration_date').datebox('setValue',dates);
	        }else{
	        	$('#expiration_date').datebox('setValue','');
	        }
	        $('#add_edit_form').attr('action','save_addedit_product?action=edit&ITEM_ID='+row.ITEM_ID);
	    }else{
	    	alertBox("Please Select Record!");
	    }
	}
	
	function saveProduct(){
		$('#add_edit_form').form('submit',{
		        url: $('#add_edit_form').attr('action'),
		        onSubmit: function(){
		        	var errormessage="";
		        	var validate=true;
		          if($('#product_name_textbox').textbox('getText')==''){
		        	  errormessage="Product Name is Empty!";
		        	  validate=false;
		          }else if($('#product_type_combobox_form').combobox('getValue')==''){
		        	  errormessage="Product Type is Empty!";
		        	  validate=false;
		          }else if($('#product_catogory_combobox_form').combobox('getValue')==''){
		        	  errormessage="Product Category is Empty!";
		        	  validate=false;
		          }else if($('#primary_uom_combobox_form').combobox('getValue')==''){
		        	  errormessage="Primary UOM is Empty!";
		        	  validate=false;
		          }else if($('#target_coverage_textbox').textbox('getText')==''){
		        	  errormessage="Target Coverage is Empty!";
		        	  validate=false;
		          }else if($('#start_date').datebox('getValue')==''){
		        	  errormessage="Start Date is Empty!";
		        	  validate=false;
		          }
		          if(errormessage!=''){
		        	  alertBox(errormessage);
		          }
		          return validate;
		        },
		        success: function(result){
		        	if (result.toString()=='succsess'){
		        		alert("Operation Successfull");
		                  if(submitType=='add' && $('#product_type_combobox_form').combobox('getText')=='VACCINE'){
			                	  $('#form_dialog').dialog('close');
					        		deviceAssociationForm($('#product_name_textbox').textbox('getText'));
					    }
		                 $('#productOverViewTable').datagrid('reload');
		            } else {
		            	  alertBox("Operaction Failed");
		            }
		        	 $('#form_dialog').dialog('close');
		        }
		    });
	}
	
	function loadFormComboboxListsForForm(row){
		 $('#product_type_combobox_form').combobox({
				url : 'get_product_type_list',
				valueField : 'value',
				textField : 'label',
				onSelect : function(productType){
					if(productType.label=='DILUENT' 
							|| productType.label=='DEVICE'){
						$('#product_catogory_combobox_form').combobox('setValue','N/A');
						$('#product_catogory_combobox_form').combobox('setText','N/A');
						$('#product_catogory_combobox_form').combobox('readonly');
					}else{
						 $('#product_catogory_combobox_form').combobox({
								url : 'get_product_category_list',
								valueField : 'value',
								textField : 'label',
								queryParams :{productTypeId:productType.value},
								onSelect :function(category){
									$('#primary_uom_combobox_form').combobox('clear');
								}
						 });
					}
				},
				onLoadSuccess:function(){
					if(row.ITEM_TYPE_NAME=='VACCINE'){
						$('#product_catogory_combobox_form').combobox({
							url : 'get_product_category_list',
							valueField : 'value',
							textField : 'label',
							queryParams :{productTypeId:row.ITEM_TYPE_ID},
							onSelect :function(category){
								$('#primary_uom_combobox_form').combobox('clear');
							}
					 });
					}else{
						$('#product_catogory_combobox_form').combobox('readonly');	
					}
					$('#product_catogory_combobox_form').combobox('setValue',row.CATEGORY_ID);
			        $('#product_catogory_combobox_form').combobox('setText',row.CATEGORY_CODE);
				}
			});
		 $('#primary_uom_combobox_form').combobox({
				valueField : 'value',
				textField : 'label',
				data:[{'value':'unit','label':'UNIT'},
				      {'value':'doses','label':'DOSES'},
				      {'value':'vial','label':'VIAL'}]
	});
	}
	 function loadStateStoreList(){
		 $('#state_combobox').combobox({
				url : 'get_state_store_list',
				valueField : 'value',
				textField : 'label',
				onSelect : loadlgaList
			
			}); 
	 }
	 function handleHistory(){
		 var row = $('#productOverViewTable').datagrid('getSelected');
		 document.getElementById("loader_div").style.display = "block";
		 if(row==null){
			 alertBox("Please Select Record From Table")
		 }else{  
			 if(row.CREATED_BY_NAME=='' || row.CREATED_BY_NAME==null){
				  $('#createdBylabel').text('<Not Available>');
			 }else{
				 $('#createdBylabel').text(row.CREATED_BY_NAME);
			}
			  $('#createdOnlabel').text(row.CREATED_ON);
			  if(row.UPDATED_BY_NAME=='' || row.UPDATED_BY_NAME==null){
				  $('#updatedBylabel').text('<Not Available>');
			 }else{
				 $('#updatedBylabel').text(row.UPDATED_BY_NAME);
			}
			  $('#updatedOnlabel').text(row.LAST_UPDATED_ON);
			  $('#history_dialog').dialog('open').dialog('center').dialog('setTitle','User Record History');  
			
		 }
		 document.getElementById("loader_div").style.display = "none";
	 }
	 function loadlgaList(stateId){
			$('#lga_combobox').combobox({
				url : 'getlgalistBasedOnStateId',
				valueField : 'value',
				textField : 'label',
				queryParams:{stateId:stateId.value,option:'notAll'},
				onSelect :function(lga){
					$('#state_checkbox').prop('checked',false);
				}
			
			});
		}
	 function alertBox(message){
		  $.messager.alert('Warning!',message,'warning');
	}
</script>
<script type="text/javascript">

$(function() {
	$('#wastage_rate_textbox').textbox('textbox').bind('input', function() {
		if($('#wastage_rate_textbox').textbox('getText').length==0){
			$('#wastage_rate_textbox').textbox('clear');
			$('#wastage_factor_textbox').textbox('clear');
		}else{
			if(isNaN($('#wastage_rate_textbox').textbox('getText'))){
				$('#wastage_rate_textbox').textbox('clear');
				alertBox("Enter Digit Between 0-100  ");
			}else{
				wastageFactor=(1/(1-$('#wastage_rate_textbox').textbox('getText')/100))
				$('#wastage_factor_textbox').textbox('setValue',wastageFactor.toFixed(2));
				$('#wastage_factor_textbox').textbox('setText',wastageFactor.toFixed(2));
				if($('#wastage_rate_textbox').textbox('getText')=='100'){
					$('#wastage_rate_textbox').textbox('clear');
					$('#wastage_factor_textbox').textbox('clear');
					alertBox("Enter Digit Between 0-100  ")
				}
				if($('#wastage_rate_textbox').textbox('getText')>100){
					$('#wastage_rate_textbox').textbox('clear');
					$('#wastage_factor_textbox').textbox('clear');
					alertBox("Enter Digit Between 0-100  ")
				}
			}
		}
		
	});
});

function deviceAssociationForm(productName){
	$.messager.confirm('Confirm', 'Do You Want To DeviceAssociation For '+productName+'?', function(r){
		if (r){
			$("#action").val("Insert");
			loadListDeviceAssociationForm(productName);
			  $('#device_association_form_dialog').dialog('open').dialog('center').dialog('setTitle','Add Device Association');
		}
	});
}
function saveDeviceAssociation(productName){
	$('#add_edit_form_for_deviceassociation').form('submit',{
	        url: $('#add_edit_form_for_deviceassociation').attr('action'),
	        onSubmit: function(){
	        	var errormessage="";
	        	var validate=true;
	        	if($('#product_combo').combobox('getValue')==''){
	        		$('#empty_prod_combo').text("Product is Empty!");
	        		validate=false;
	        	}else if($('#ad_srng_combo').combobox('getValue')==''){
	        		$('#empty_prod_combo').text("");
	        		$('#empty_ad_combo').text("Field is Empty!");
	        		validate=false;
	        	}
	          if(errormessage!=''){
	        	  alertBox(errormessage);
	          }
	          return validate;
	        },
	        success: function(result){
	        	if (result.toString()=='succsess'){
	                  alertBox("Operation Successfull");
	                  $('#device_association_form_dialog').dialog('close');
	            } else {
	            	  alertBox("Operaction Failed");
	            }			
	        }
	    });
}
function loadListDeviceAssociationForm(productName){
	$('#product_combo').combobox({
		url : 'device_asso_product_list',
		valueField : 'value',
		textField : 'label',
		onLoadSuccess :function(){
			var jsondata=$('#product_combo').combobox('getData');
			for (var i = 0; i < jsondata.length; i++) {
				if(productName===jsondata[i].label){
					$('#product_combo').combobox('select',(jsondata[i].value));
					break;
				}
			}
		}
	});
	$('#ad_srng_combo').combobox({
		url : 'ad_syringe_list',
		valueField : 'value',
		textField : 'label'
	});
	$('#recon_srng_combo').combobox({
		url : 'reconstitute_syringe_list',
		valueField : 'value',
		textField : 'label'
	});
}
loadPaginationForTable(productOverViewTable);
</script>
</html>