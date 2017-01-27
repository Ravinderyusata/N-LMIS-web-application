<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Device Association Detail Page</title>
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
			$('#addDeviceAssBtn').hide();
			$('#editDeviceAssBtn').hide();
			break;
		case "SIFP":
			$('#addDeviceAssBtn').hide();
			$('#editDeviceAssBtn').hide();
			break;
		case "NTO":
			$('#button_bar').hide();
			$('#filters').hide();
			break;
		case "LIO":
			$('#button_bar').hide();
			$('#filters').hide();
			loadDeviceAssociationPageData('${userBean.getX_WAREHOUSE_ID()}');
			break;
		case "MOH":
			$('#button_bar').hide();
			$('#filters').hide();
			loadDeviceAssociationPageData('${userBean.getX_WAREHOUSE_ID()}');
			break;
		}
		
	}
</script>
</head>
<body style="margin: 0px;" onload="setRole()">

	<!-- headr of page -->
	<%-- <jsp:include page="headerforpages.jsp"></jsp:include> --%>
									<!-- status dialog -->

	<!-- button bar -->

	<div class="button_bar" id="button_bar">
		<ul>
			<li><a id="addDeviceAssBtn" class="w3-btn w3-ripple" onclick="addDeviceAssociation()" > <img alt="add"
					src="resources/images/file_add.png">Add Device Association
			</a></li>
			<li><a id="editDeviceAssBtn" class="w3-btn w3-ripple" onclick="editDeviceAssociation()"> <img alt="edit"
					src="resources/images/file_edit.png">Edit Device Association
			</a></li>
		</ul>
	</div>
	
	<!-- filters -->
		<div id="filters" style="padding-left:10px;;display: inline-flex;">
		<div id="state_checkbox_div" style="margin-top: 20px;">
		<input style="width: 15px;height: 15px;" type="checkbox" value="${userBean.getX_WAREHOUSE_ID()}" id="state_checkbox" 
		onchange="oncheckStateCheckBox()"><b>${userBean.getX_WAREHOUSE_NAME()}</b>
		</div>&nbsp;&nbsp;

		<div id="lga_combobox_div">
			<span>LGA:</span><br>
			 <select id="lga_combobox" class="easyui-combobox"
				name="lga_combobox" style="width:200px;">
			</select>
		</div> &nbsp;&nbsp;

	    <div style="padding-top: 12px;">
			 <a class="easyui-linkbutton" 
				onclick="loadDeviceAssociationPageData()">Refresh </a>
			 </div>
		</div>
		<!-- filters end here -->
	<!-- user table -->
	<table id="deviceAssociationTable" class="easyui-datagrid"
		style="width: 100%; height: 430px" title="Device Association Details"
		toolbar="#tb" rownumbers="true" pagination="true" pageSize=30 singleSelect="true"
		striped="true" remoteSort="false">


	</table>
							<!-- home and back toolbar -->
	
	
	<!-- add/edit/search form -->
	
<div id="form_dialog" class="easyui-dialog" style="width:550px;height:220px;padding: 5px;"
                closed="true" buttons="#form_buttons">
 
  <f:form method="POST" id="add_edit_form" commandName="deviceAssBean" action="savedeviceAsso" >
<table align="center">
    <tr>
        <td style="font-weight: bold;">*Product :</td>
        <td><f:select path="x_PRODUCT_ID" class="easyui-combobox" id="product_combo"  cssStyle="width:170px;">
        <f:options items="${product_list}" itemLabel="label" itemValue="value"/>
        </f:select>
        <label style="color: red"  id="empty_prod_combo"></label>
        <f:input type="text" id="action" style="display: none" path="x_ACTION" />
        <f:input type="text" id="asso_id" style="display: none" path="x_ASSOCIATION_ID" />
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
            <f:select path="x_AD_SYRINGE_ID" class="easyui-combobox" id="ad_srng_combo" >
            <f:options items="${ad_syringe_list}" itemValue="value" itemLabel="label" />
            </f:select>
              <span style="color: red" id="empty_ad_combo"></span>
        </td>
          
    </tr>
    <tr>
    <td >RECONSTITUTE SYRNG NAME :</td>
        <td>
            <f:select path="x_RECONSTITUTE_SYRNG_ID" class="easyui-combobox" id="recon_srng_combo">
            <f:options items="${recons_syringe_list}" itemValue="value" itemLabel="label" />
            </f:select>
             <span style="color: red" id="empty_recon_combo"></span>
        </td>
    </tr>
</table>
</f:form>
</div>
	 <!-- form button  -->
         <div id="form_buttons">
            <a href="javascript:void(0)" id="saveBtn" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveDeviceAssociation()" style="width:90px">Save</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#form_dialog').dialog('close')" style="width:90px">Cancel</a>
        </div>
	<!--  footer page -->

	<%-- <jsp:include page="footer-for-page.jsp"></jsp:include> --%>
</body>
<script type="text/javascript" src="resources/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="resources/easyui/jquery.easyui.min.js"></script>
<script src="resources/js/common.js" type="text/javascript"></script>
<script src="resources/js/datagrid_agination.js" type="text/javascript"></script>
<script type="text/javascript">
	function addDeviceAssociation() {
		 $('#add_edit_form').form('clear');
		 $('#product_combo').combobox('readonly',false);
		$("#action").val("Insert");
		 $('#safty_box_checkbox').prop('checked',true);
		  $('#form_dialog').dialog('open').dialog('center').dialog('setTitle','Add Device Association');
	}
	function editDeviceAssociation() {
		$("#action").val("Update");
		document.getElementById("empty_prod_combo").innerHTML="";
		document.getElementById("empty_ad_combo").innerHTML="";
		document.getElementById("empty_prod_combo").innerHTML="";
	    var row = $('#deviceAssociationTable').datagrid('getSelected');
	       if(row==null){
	    	alert("Please Select row from Table!") 
	       }else{
	    	   $('#form_dialog').dialog('open').dialog('center').dialog('setTitle','Edit Device Association');
	    	   $('#product_combo').combobox('setValue', row.ITEM_ID);
	    	   $('#product_combo').combobox('setText', row.ITEM_NUMBER);
	    	   $('#product_combo').combobox('readonly');
	    	   $('#ad_srng_combo').combobox('setValue', row.AD_SYRINGE_ID);
	    	   $('#ad_srng_combo').combobox('setText', row.AD_SYRINGE_NAME);
	    	   $('#recon_srng_combo').combobox('setValue',row.RECONSTITUTE_SYRNG_ID);
	    	   $('#recon_srng_combo').combobox('setText',row.RECONSTITUTE_SYRNG_NAME);
	    	   $('#asso_id').val(row.ASSOCIATION_ID);

	       }
	    
	}
	
	function saveDeviceAssociation(){
		$('#add_edit_form').form('submit',{
		        url: $('#add_edit_form').attr('action'),
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
		        	}else if($('#recon_srng_combo').combobox('getValue')=='' && !$("#action").val()=='Update'){
		        		$('#empty_prod_combo').text("");
		        		$('#empty_ad_combo').text("");
		        		$('#empty_recon_combo').text("Feild  is Empty!");
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
		                  $('#deviceAssociationTable').datagrid('reload');
		            } else {
		            	  alertBox("Operaction Failed");
		            }
		        	 $('#form_dialog').dialog('close');			
		        }
		    });
	}
	 function alertBox(message){
		  $.messager.alert('Warning!',message,'warning');
	}
	 function oncheckStateCheckBox(){
		 if($('#lga_combobox').combobox('getValue')!="" && $('#state_checkbox').is(':checked'))	{
		 	$('#lga_combobox').combobox('clear');
		 }
		 }
	function loadDeviceAssociationPageData(warehouse_id){
		if(!$('#state_checkbox').is(':checked') 
				&& $('#lga_combobox').combobox('getValue')==""
				&& (!'${userBean.getX_ROLE_NAME()}'=='LIO' 
						|| !'${userBean.getX_ROLE_NAME()}'=='MOH') ){//excep lio and moh
			validate=false;
			alert("Select Filter!");		
		}else {
			if($('#state_checkbox').is(':checked')){
				warehouse_id=$('#state_checkbox').val();
			}
			if($('#lga_combobox').combobox('getValue')!=''){
				warehouse_id=$('#lga_combobox').combobox('getValue');
			}
			if($('#state_checkbox').is(':checked')){
				if('${userBean.getX_ROLE_NAME()}'=='SCCO'){//only when scco is logged
					$('#addDeviceAssBtn').show();
					$('#editDeviceAssBtn').show();	
				}
				
			}else{
				$('#addDeviceAssBtn').hide();
				$('#editDeviceAssBtn').hide();
			}
			$('#deviceAssociationTable').datagrid({
				url : 'getdevice_association_detail',
				remoteSort : false,
				queryParams:{warehouseId:warehouse_id},
				columns : [ [ {
					field : 'ASSOCIATION_ID',
					title : 'ASSOCIATION ID',
					sortable : true,
					hidden : 'true'
				}, {
					field : 'ITEM_ID',
					title : 'ITEM ID',
					sortable : true,
					hidden : 'true'
				}, {
					field : 'ITEM_NUMBER',
					title : 'ITEM NUMBER',
					sortable : true
				}, {
					field : 'AD_SYRINGE_ID',
					title : 'AD_SYRINGE ID',
					sortable : true,
					hidden : 'true'
				}, {
					field : 'AD_SYRINGE_NAME',
					title : 'AD_SYRINGE NAME',
					sortable : true,
					hidden : 'true'
				}, {
					field : 'RECONSTITUTE_SYRNG_ID',
					title : 'RECONSTITUTE SYRNG_ID',
					sortable : true,
					hidden : 'true'
				}, {
					field : 'RECONSTITUTE_SYRNG_NAME ',
					title : 'RECONSTITUTE SYRNG NAME ',
					sortable : true,
					hidden : 'true'
				}, {
					field : 'ASSOCIATED_DEVICES',
					title : 'ASSOCIATED DEVICES',
					sortable : true
				}, {
					field : 'NO_OF_ASSOCIATE_DEVICE',
					title : 'NO OF ASSOCIATE_DEVICE ',
					sortable : true
				} ] ]
			});
		}
		
	}
</script>
<script type="text/javascript">
$('#lga_combobox').combobox({
	url : 'getlgalistBasedOnStateId',
	valueField : 'value',
	textField : 'label',
	queryParams:{stateId:'${userBean.getX_WAREHOUSE_ID()}',option:'notAll'},
	onSelect :function(lga){
		$('#state_checkbox').prop('checked',false);
	}

});
loadPaginationForTable(deviceAssociationTable);
</script>
</html>