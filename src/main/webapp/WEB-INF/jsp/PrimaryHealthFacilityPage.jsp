<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Primary Health Facility Page</title>
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
			$('#state_combobox_div').hide();
			$('#addBtn').hide();
			$('#editBtn').hide();
			loadlgaList({value:'${userBean.getX_WAREHOUSE_ID()}'});
			break;
		case "NTO":
			loadStateList();
			$('#overViewBtn').hide();
			break;
		case "LIO":
			loadHfDrpdn({value:'${userBean.getX_WAREHOUSE_ID()}'});
			$('#state_combobox_div').hide();
			$('#lga_combobox_div').hide();
			$('#addBtn').hide();
			$('#editBtn').hide();
			var url="getprimary_hf_list_grid?warehouse_id="+${userBean.getX_WAREHOUSE_ID()}+"&hf_id="+null+'&state_id='+null;
			loadHfData(url)
			break;
		case "MOH":
			loadHfDrpdn({value:'${userBean.getX_WAREHOUSE_ID()}'});
			$('#state_combobox_div').hide();
			$('#lga_combobox_div').hide();
			$('#addBtn').hide();
			$('#editBtn').hide();
			var url="getprimary_hf_list_grid?warehouse_id="+${userBean.getX_WAREHOUSE_ID()}+"&hf_id="+null+'&state_id='+null;
			loadHfData(url)
			break;
		}
	/* 	document.getElementById("common_lable").innerHTML = "Primary Health Facilities";
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
#hf_number_label,#hf_name_label,#state_store_label,#default_ord_store_label,#country_name_label,
#ward_label,#start_date_label{
font-weight: bold;
}
</style>
</head>
<body style="margin: 0px;" onload="setRole()">
	<!-- headr of page -->
	<%-- <jsp:include page="headerforpages.jsp"></jsp:include> --%>
		
	
		<!-- button bar -->
	<div class="button_bar" id="button_bar">
		<ul style="list-style-type:none">
			<li><a id="addBtn" class="w3-btn w3-ripple" onclick="addHfForm()"> <img alt="add"
					src="resources/images/file_add.png">Add
			</a></li>
			<li><a id="editBtn" class="w3-btn w3-ripple" onclick="editHfForm(this.id)"> <img alt="edit"
					src="resources/images/file_edit.png">Edit
			</a></li>
			<li><a id="overViewBtn" class="w3-btn w3-ripple" onclick="editHfForm(this.id)">Health Facility OverView
			</a></li>
			<li><a class="w3-btn w3-ripple" onclick="handleHistory()"> <img alt="history"
					src="resources/images/file_history.png">History
			</a></li>
			<li><a class="w3-btn w3-ripple" href="HF_list_export"> <img alt="export"
					src="resources/images/Export_load_upload.png">Export
			</a></li>
		</ul>
	</div>
<!-- filters -->
		<div id="filters" style="padding: 3px;display: inline-flex;">
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
			<div id="hf_combobox_div">
			<span>Health Facility Filter:</span><br> 
			<select id="hf_combobox"
				class="easyui-combobox" name="hf_id" style="width: 200px;">
			</select>
			</div>&nbsp;&nbsp;
			 <div style="padding-top: 12px;">
			 <a class="easyui-linkbutton" 
				onclick="HfData()">Refresh </a>
			 </div>
		</div>
		<!-- filters end here -->
	<!-- user table -->
	<div id="hf_table_div" style="margin-left: 5px;">
	<table id="HFListTable" class="easyui-datagrid"
		style="width: 100%; height:430px;" title="Primary Health Facility"
		data-options="toolbar:'#tb', rownumbers:'true', pagination:'true', singleSelect:'true',
		striped:'true', remoteSort:'false'">	
	</table>
	</div>
	
	
	<!-- footer of page -->
	<%-- <jsp:include page="footer-for-page.jsp"></jsp:include> --%>
	 <!-- loder div -->
		<div style="display: none;" id="loader_div" class="loader_div">
			<div class="loader" id="loader_show">
			</div>
		</div>
		
		 <!-- HF Add/Edit form -->
	
	 <div id="form_dialog" class="easyui-dialog" style="width:450px;height:490px;padding: 5px;"
                closed="true" buttons="#form_buttons">
            <f:form id="add_edit_form" method="post" commandName="hfBean">
            <table cellspacing="10px;">
            <tr>
            <td>
	            <div id="hf_number_div">
	                    <label id="hf_number_label">*Health Facility Number:</label><br>
	                    <f:input  id="hf_number_textbox"  class="easyui-textbox" path="x_HF_NUMBER"/>
	              </div>
            </td>
             <td>
             <div class="hf_name_div">
                    <label id="hf_name_label">*Health Facility Name:</label><br>
                    <f:input id="hf_name_textbox"  class="easyui-textbox" path="x_HF_NAME"/>
                </div>
             </td>
            </tr>
             <tr>
            <td>
	            <div class="description_div">
                    <label id="description_label">Description:</label><br>
                    <f:textarea id="description_textbox" data-options="multiline:true" style="height:60px" class="easyui-textbox" path="x_HF_DESCRIPTION"/>
                </div>
            </td>
             <td>
              		<div id="country_name_div">
		             <label id="country_name_label">*Country Name:</label><br>
	                   <f:select id="country_name_combobox_form"  class="easyui-combobox" cssStyle="width:150px;" path="x_COUNTRY_ID"/>
	                </div>
             </td>
            </tr>
            <tr>
	             <td>
	             <div class="phone_no_div">
	                    <label id="phone_no_label" class="label-top">Telephone No:</label><br>
	                    <f:input id="phone_no_textbox"  
	                    class="easyui-textbox" path="x_TELEPHONE_NUMBER" />
	              </div>
                
               </td>
	              <td>
	               <div class="email_div">
	                    <label id="email_label" class="label-top">E-Mail:</label><br>
	                    <f:input id="email_textbox"  
	                    class="easyui-textbox" path="x_EMAIL_ADDRESS" />
	                </div>
		            
               </td>
            </tr>
              <tr>
	            <td>
		            <div id="state_store_div">
		             <label id="state_store_label">*State Store:</label><br>
	                    <f:select id="state_store_combobox_form"  class="easyui-combobox" cssStyle="width:150px;" path=""/>
	                </div>
	            </td>
	             <td>
		            <div id="default_ord_store_div">
		             <label id="default_ord_store_label">*Default Ordering Store:</label><br>
	                    <f:select id="default_ord_store_combobox_form"  class="easyui-combobox" cssStyle="width:150px;" path=""/>
	                     <input id="default_ord_store_combobox_form_field" type="hidden" name="x_DEFAULT_STORE_ID">
	                </div>
               </td>
            </tr>
            <tr>
           		 <td>
           		   <div class="target_population_div">
	                    <label id="target_population_label">Target Population:</label><br>
	                    <f:input id="target_population_textbox" cssStyle="width:200px;" class="easyui-textbox" path="x_TARGET_POPULATION"/>
	            </div>
		          
	            </td>
              <td>
	             <div class="ward_div">
                    <label id="ward_label">*Ward:</label><br>
                    <f:select  id="ward_combobox_form"  class="easyui-combobox" cssStyle="width:180px;" path="" />
                     <input id="ward_combobox_form_field" type="hidden" name="X_WARD_ID">
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
             
                <div id="end_date_div">
                  <label id="end_date_label">End date:</label><br>
                    <f:input id="end_date" data-options="formatter:myformatter,parser:myparser" class="easyui-datebox" width="120px;" path="x_END_DATE"/>
                </div>
             </td>
            </tr>
            <tr>
            <td >
           
             <div id="fridge__div">
                    <f:checkbox    id="fridge_checkbox" path="x_VACCINE_FLAG" value="Y"/>Fridge Available
            </div>
            </td>
            <td>
             <div id="active_div">
                    <f:checkbox    id="active_checkbox" path="x_STATUS" value="A"/>Active
                    <input type="hidden" id="db_id_field" name="x_DB_ID">
                </div>
                </td>
            </tr>
            </table>
            </f:form>
        </div>
        <!-- form button  -->
         <div id="form_buttons">
            <a href="javascript:void(0)" id="saveBtn" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveHF()" style="width:90px">Save</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#form_dialog').dialog('close')" style="width:90px">Cancel</a>
        </div>
        
		<!-- history lga div -->

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
</body>
<script type="text/javascript" src="resources/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="resources/easyui/jquery.easyui.min.js"></script>
<script src="resources/js/common.js" type="text/javascript"></script>
<script src="resources/js/datagrid_agination.js" type="text/javascript"></script>
<script type="text/javascript">
function alertBox(message){
	  $.messager.alert('Warning!',message,'warning');
}
function HfData(){
	var url="";
	var validate=true;
	if('${userBean.getX_ROLE_NAME()}'=='NTO'){
		if($('#state_combobox').combobox('getValue')==""){
			validate=false;
			alertBox("State is Empty");
		}else if($('#lga_combobox').combobox('getValue')==""){
			validate=false;
			alertBox("LGA is Empty");
		}else if($('#hf_combobox').combobox('getValue')==''){
			validate=false;
			alertBox("Health Facility is Empty");
		}
	}else if('${userBean.getX_ROLE_NAME()}'=='SCCO' 
			|| '${userBean.getX_ROLE_NAME()}'=='SIO'
			|| '${userBean.getX_ROLE_NAME()}'=='SIFP'){
		if($('#lga_combobox').combobox('getValue')==""){
			validate=false;
			alertBox("LGA is Empty");
		}else if($('#hf_combobox').combobox('getValue')==''){
			validate=false;
			alertBox("Health Facility is Empty");
		}
	}else if('${userBean.getX_ROLE_NAME()}'=='LIO'
			|| '${userBean.getX_ROLE_NAME()}'=='MOH'){
		if($('#hf_combobox').combobox('getValue')==''){
			validate=false;
			alertBox("Health Facility is Empty");
		}
	}
	
	if(validate){
		var state_id=$('#state_combobox').combobox('getValue');
		var warehouse_id=$('#lga_combobox').combobox('getValue');
		if(warehouse_id=='' || warehouse_id==null){
			warehouse_id='${userBean.getX_WAREHOUSE_ID()}';
		}
		var hf_id=$('#hf_combobox').combobox('getValue');
		var url="getprimary_hf_list_grid?warehouse_id="+warehouse_id+"&hf_id="+hf_id+'&state_id='+state_id;
		loadHfData(url)
	}
}

function handleHistory(){
	 var row = $('#HFListTable').datagrid('getSelected');
	 document.getElementById("loader_div").style.display = "block";
	 if(row==null){
		 alertBox("Please Select Record From Table")
	 }else{
		 ajaxPostRequestSync("get_HF_history", {DB_ID: row.DB_ID,DEFAULT_STORE_ID: row.DEFAULT_STORE_ID},
				 function(response) {
			  if(response[0].CREATED_BY=='' || response[0].CREATED_BY==null){
				  $('#createdBylabel').text("<Not Available>");
			  }else{
				  $('#createdBylabel').text(response[0].CREATED_BY); 
			  }
			  $('#createdOnlabel').text(response[0].CREATED_ON);
			  $('#updatedBylabel').text(response[0].UPDATED_BY);
			  $('#updatedOnlabel').text(response[0].LAST_UPDATED_ON);
			  $('#history_dialog').dialog('open').dialog('center').dialog('setTitle','User Record History');  
		  });
		 document.getElementById("loader_div").style.display = "none";
		
	 }
}
function addHfForm(){
	submitType="add";
	var validate=true;
    $('#form_dialog').dialog('open').dialog('center').dialog('setTitle','Add Health Facility');
    $('#add_edit_form').form('clear');
    $("#fridge_checkbox").prop("checked", true);
    $("#active_checkbox").prop("checked", true);
    $('#start_date').datebox('setValue',formateDate(new Date()));
    $('#add_edit_form').attr('action','save_addedit_hf?action=add');
    $('#country_name_combobox_form').combobox('select',$('#country_name_combobox_form').combobox('getData')[0].value);
    if('${userBean.getX_ROLE_NAME()}'=='NTO'){
    	loadStateStoreList();
    	loadFormComboboxListsForForm($('#state_store_combobox_form').combobox('getValue'));
    }else if('${userBean.getX_ROLE_NAME()}'=='SCCO'){
    	 $('#state_store_combobox_form').combobox('setValue','${userBean.getX_WAREHOUSE_ID()}');
         $('#state_store_combobox_form').combobox('setText','${userBean.getX_WAREHOUSE_NAME()}');
         $('#state_store_combobox_form').combobox('disable',true);
         loadFormComboboxListsForForm('${userBean.getX_WAREHOUSE_ID()}');
    }
    $('#default_ord_store_combobox_form').combobox('enable',true);
    $('#ward_combobox_form').combobox('enable',true);
}
 
 function loadStateStoreList(){
	 $('#state_store_combobox_form').combobox({
			url : 'get_state_store_list',
			valueField : 'value',
			textField : 'label',
			onSelect : function(stateStore) {
				loadFormComboboxListsForForm(stateStore.value);
			}
		
		}); 
 }
 function loadFormComboboxListsForForm(stateId){
	 $('#default_ord_store_combobox_form').combobox({
			url : 'getlgalistBasedOnStateId',
			valueField : 'value',
			textField : 'label',
			queryParams:{stateId:stateId,option:'notAll'},
			onSelect : function(lga) {
				$('#default_ord_store_combobox_form_field').val(lga.value);
		       	$('#default_ord_store_combobox_form_field').text(lga.label);
				$('#ward_combobox_form').combobox('clear');
				loadWardBadesdOnLga(lga.value);
			}

		});
 }

function saveHF(){
	 $('#add_edit_form').form('submit',{
	        url: $('#add_edit_form').attr('action'),
	        onSubmit: function(){
	        	var errormessage="";
	        	var validate=true;
	          if($('#hf_number_textbox').textbox('getText')==''){
	        	  errormessage="Health Facility Number is Empty!";
	        	  validate=false;
	          }else if($('#hf_name_textbox').textbox('getValue')==''){
	        	  errormessage="Health Facility Name is Empty!";
	        	  validate=false;
	          }else if($('#country_name_combobox_form').combobox('getValue')==''){
	        	  errormessage="Country Name is Empty!";
	        	  validate=false;
	          }else if($('#phone_no_textbox').textbox('getValue')!=''){
	          	  if($('#phone_no_textbox').textbox('getValue').length!='11'
	                  || isNaN($('#phone_no_textbox').textbox('getValue'))
	                  || $('#phone_no_textbox').textbox('getValue')[0]!='0'){
	                		  errormessage="Telephone Number Format |eg. 0XXXXXXXXXX!";
	                    	  validate=false;
	               }else if($('#email_textbox').textbox('getValue')!=''){
		             	  if(!isEmail($('#email_textbox').textbox('getValue'))){
		                      errormessage="Enter Valid Email Adress Or Leave Blank this Field";
		                      validate=false;
		                  }else if($('#state_store_combobox_form').combobox('getValue')==''){
		    	        	  errormessage="State Store is Empty!";
		    	        	  validate=false;
		    	          }else if($('#default_ord_store_combobox_form').combobox('getValue')==''){
		    	        	  errormessage="Default Ordering Store is Empty!";
		    	        	  validate=false;
		    	          }else if($('#ward_combobox_form').combobox('getValue')==''){
		    	        	  errormessage="Ward is Empty!";
		    	        	  validate=false;
		    	          }else if($('#start_date').datebox('getValue')==''){
		    	        	  errormessage="Start Date is Empty!";
		    	        	  validate=false;
		    	          }
	              	 }else if($('#state_store_combobox_form').combobox('getValue')==''){
	    	        	  errormessage="State Store is Empty!";
	    	        	  validate=false;
	    	          }else if($('#default_ord_store_combobox_form').combobox('getValue')==''){
	    	        	  errormessage="Default Ordering Store is Empty!";
	    	        	  validate=false;
	    	          }else if($('#ward_combobox_form').combobox('getValue')==''){
	    	        	  errormessage="Ward is Empty!";
	    	        	  validate=false;
	    	          }else if($('#start_date').datebox('getValue')==''){
	    	        	  errormessage="Start Date is Empty!";
	    	        	  validate=false;
	    	          }
	           }else if($('#email_textbox').textbox('getValue')!=''){
		             	  if(!isEmail($('#email_textbox').textbox('getValue'))){
		                      errormessage="Enter Valid Email Adress Or Leave Blank this Field";
		                      validate=false;
		                  }else if($('#state_store_combobox_form').combobox('getValue')==''){
		    	        	  errormessage="State Store is Empty!";
		    	        	  validate=false;
		    	          }else if($('#default_ord_store_combobox_form').combobox('getValue')==''){
		    	        	  errormessage="Default Ordering Store is Empty!";
		    	        	  validate=false;
		    	          }else if($('#ward_combobox_form').combobox('getValue')==''){
		    	        	  errormessage="Ward is Empty!";
		    	        	  validate=false;
		    	          }else if($('#start_date').datebox('getValue')==''){
		    	        	  errormessage="Start Date is Empty!";
		    	        	  validate=false;
		    	          }
	          }else if($('#state_store_combobox_form').combobox('getValue')==''){
  	        	  errormessage="State Store is Empty!";
	        	  validate=false;
	          }else if($('#default_ord_store_combobox_form').combobox('getValue')==''){
	        	  errormessage="Default Ordering Store is Empty!";
	        	  validate=false;
	          }else if($('#ward_combobox_form').combobox('getValue')==''){
	        	  errormessage="Ward is Empty!";
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
	                  alertBox("Operation Successfull");
	                  if(submitType=='edit'){
	                	  HfData();//for reload data 
	                  }
	            } else {
	            	  alertBox("Operaction Failed");
	            }
	        	 $('#form_dialog').dialog('close');
	        }
	    });
}
function editHfForm(buttonId){
	if(buttonId=='editBtn'){
		$('#saveBtn').linkbutton('enable',true);
	}else{
		$('#saveBtn').linkbutton('disable',true);
	}
	submitType="edit";
	var row = $('#HFListTable').datagrid('getSelected');
	 $('#default_ord_store_combobox_form').combobox('enable',true);
	 if (row){
    	if(buttonId=='editBtn'){
        $('#form_dialog').dialog('open').dialog('center').dialog('setTitle','Edit Health Facility');
    	}else{
    		  $('#form_dialog').dialog('open').dialog('center').dialog('setTitle','OverView Of Health Facility');
    	}
    	$('#hf_number_textbox').textbox('setValue',row.CUSTOMER_NUMBER);
        $('#hf_number_textbox').textbox('setText',row.CUSTOMER_NUMBER);
        $('#hf_name_textbox').textbox('setValue',row.CUSTOMER_NAME);
        $('#hf_name_textbox').textbox('setText',row.CUSTOMER_NAME);
        $('#description_textbox').textbox('setValue',row.CUSTOMER_DESCRIPTION);
        $('#description_textbox').textbox('setText',row.CUSTOMER_DESCRIPTION);
        $('#country_name_combobox_form').combobox('setValue',row.COUNTRY_ID);
        $('#country_name_combobox_form').combobox('setText',row.COUNTRY_NAME);
        $('#phone_no_textbox').textbox('setValue',row.DAY_PHONE_NUMBER);
        $('#phone_no_textbox').textbox('setText',row.DAY_PHONE_NUMBER);
        $('#email_textbox').textbox('setValue',row.EMAIL_ADDRESS);
        $('#email_textbox').textbox('setText',row.EMAIL_ADDRESS);
    	$('#db_id_field').val(row.DB_ID);
       	$('#db_id_field').text(row.DB_ID);
        
        if('${userBean.getX_ROLE_NAME()}'=='NTO'){
        	 $('#state_store_combobox_form').combobox('setValue',$('#state_combobox').combobox('getValue'));
             $('#state_store_combobox_form').combobox('setText',$('#state_combobox').combobox('getText'));
             $('#state_store_combobox_form').combobox('disable',true);
        }else if('${userBean.getX_ROLE_NAME()}'=='SCCO'
        		|| '${userBean.getX_ROLE_NAME()}'=='SIO'
        		|| '${userBean.getX_ROLE_NAME()}'=='SIFP'){
        	 $('#state_store_combobox_form').combobox('setValue','${userBean.getX_WAREHOUSE_ID()}');
             $('#state_store_combobox_form').combobox('setText','${userBean.getX_WAREHOUSE_NAME()}');
             $('#state_store_combobox_form').combobox('disable',true);
        }else if('${userBean.getX_ROLE_NAME()}'=='LIO'
    		|| '${userBean.getX_ROLE_NAME()}'=='MOH'){
        getStateStoreBasedOnLgaId(row.DEFAULT_STORE_ID);
        }
        $('#default_ord_store_combobox_form').combobox('setValue',row.DEFAULT_STORE_ID);
       	$('#default_ord_store_combobox_form').combobox('setText',row.DEFAULT_STORE);
       	if(row.DEFAULT_STORE_ID!=''){
       		loadWardBadesdOnLga(row.DEFAULT_STORE_ID);
       	}
       	$('#default_ord_store_combobox_form_field').val(row.DEFAULT_STORE_ID);
       	$('#default_ord_store_combobox_form_field').text(row.DEFAULT_STORE);
       	$('#target_population_textbox').textbox('setValue',row.TARGET_POPULATION);
        $('#target_population_textbox').textbox('setText',row.TARGET_POPULATION);
       	$('#ward_combobox_form').combobox('setValue',row.CUSTOMER_TYPE_ID);
        $('#ward_combobox_form').combobox('setText',row.customer_type_code);
        $('#ward_combobox_form_field').val(row.CUSTOMER_TYPE_ID);
        $('#ward_combobox_form_field').text(row.customer_type_code);
         if(row.STATUS=='A'){
        	$("#active_checkbox").prop("checked", true);
        }else{
        	$("#active_checkbox").prop("checked", false);
        }
        if(row.VACCINE_FLAG=='Y'){
         	$("#fridge_checkbox").prop("checked", true);
         }else{
        	 $("#fridge_checkbox").prop("checked", false);
         }
        var dates=formateDate(row.START_DATE);
        $('#start_date').datebox('setValue',dates);
        if(!isNaN(dates=formateDate(row.END_DATE))){
        	$('#end_date').datebox('setValue',dates);
        }
        $('#default_ord_store_combobox_form').combobox('disable',true);
        $('#add_edit_form').attr('action','save_addedit_hf?action=edit&CUSTOMER_ID='+row.CUSTOMER_ID);
    }else{
    	alertBox("Please Select Record!");
    }    
}


	function getStateStoreBasedOnLgaId(lgaId) {
		ajaxPostRequest("get_state_store_id_basedon_lga_id", {
			LGA_ID : lgaId
		}, function(response) {
			$('#state_store_combobox_form').combobox('setValue',
					response[0].value);
			$('#state_store_combobox_form').combobox('setText',
					response[0].label);
			$('#state_store_combobox_form').combobox('disable', true);
		});
	}

	function loadHfData(url) {
		$('#HFListTable').datagrid({
			url : url,
			remoteSort : false,
			columns : [ [ {
				field : 'COMPANY_ID',
				title : 'COMPANY ID',
				hidden : 'true'
			}, {
				field : 'CUSTOMER_ID',
				title : 'CUSTOMER ID',
				hidden : 'true'
			}, {
				field : 'COUNTRY_ID',
				title : 'COUNTRY ID',
				hidden : 'true'
			}, {
				field : 'COUNTRY_NAME',
				title : 'COUNTRY_NAME',
				hidden : 'true'
			}, {
				field : 'CUSTOMER_TYPE_ID',
				title : 'CUSTOMER TYPE ID',
				hidden : 'true'
			}, {
				field : 'CUSTOMER_NAME',
				title : 'Health Facility Name',
				sortable : true
			}, {
				field : 'CUSTOMER_NUMBER',
				title : 'CUSTOMER_NUMBER',
				hidden : 'true'
			}, {
				field : 'CUSTOMER_DESCRIPTION',
				title : 'CUSTOMER_DESCRIPTION',
				hidden : 'true'
			}, {
				field : 'customer_type_code',
				title : 'Ward',
				sortable : true
			}, {
				field : 'TARGET_POPULATION',
				title : 'MTP',
				sortable : true
			}, {
				field : 'EDIT_DATE',
				title : 'MTP Edit Date',
				sortable : true
			}, {
				field : 'MONTHLY_PREGNANT_WOMEN_TP',
				title : 'Pregnant Women MTP(TT)',
				sortable : true
			}, {
				field : 'DEFAULT_STORE',
				title : 'Default Ordering Store',
				sortable : true
			}, {
				field : 'DEFAULT_STORE_ID',
				title : 'DEFAULT STORE ID',
				sortable : true,
				hidden : 'true'
			}, {
				field : 'DB_ID',
				title : 'DB_ID',
				sortable : true,
				hidden : 'true'
			}, {
				field : 'STATE_ID',
				title : 'STATE_ID',
				sortable : true,
				hidden : 'true'
			}, {
				field : 'STATE_NAME',
				title : 'STATE_NAME',
				sortable : true,
				hidden : 'true'
			}, {
				field : 'DAY_PHONE_NUMBER',
				title : 'Telephone',
				sortable : true
			}, {
				field : 'EMAIL_ADDRESS',
				title : 'EMAIL_ADDRESS',
				sortable : true,
				hidden : 'true'
			}, {
				field : 'VACCINE_FLAG',
				title : 'Fridge Available',
				sortable : true
			}, {
				field : 'STATUS',
				title : 'Active',
				sortable : true
			}, {
				field : 'START_DATE',
				title : 'Facility Start Date',
				sortable : true
			}

			] ]
		});
	}
</script>
<script type="text/javascript">
hideAfterCurrentDate('#start_date');//for disable after current date 
hideBeforCurrentDate('#end_date');//for disable before current date 
$('#start_date').datebox({
	formatter:myformatter,
	parser:myparser
});
$('#end_date').datebox({
	formatter:myformatter,
	parser:myparser
});
function loadStateList(){
	$('#state_combobox').combobox({
		url : 'get_state_store_list',
		valueField : 'value',
		textField : 'label',
		onSelect :loadlgaList
	});
}
function loadlgaList(stateId){
	$('#lga_combobox').combobox({
		url : 'getlgalistBasedOnStateId',
		valueField : 'value',
		textField : 'label',
		queryParams:{stateId:stateId.value,option:'notAll'},
		onSelect : loadHfDrpdn
	
	});
}
$('#country_name_combobox_form').combobox({
	url : 'getCountrylist',
	valueField : 'value',
	textField : 'label'
});
function loadWardBadesdOnLga(lgaId){
	$('#ward_combobox_form').combobox({
		url : 'get_ward_list_basedon_lga?LGA_ID=' + lgaId+'&option=noAll',
		valueField : 'value',
		textField : 'label',
		onSelect : function(ward) {
			 $('#ward_combobox_form_field').val(ward.value);
		     $('#ward_combobox_form_field').text(ward.label);
		}
	});
}
function loadHfDrpdn(lga){
	$('#hf_combobox').combobox('clear');
	$('#hf_combobox').combobox({
		url : 'getHflist?lgaid=' + lga.value+'&option=All',
		valueField : 'value',
		textField : 'label',
		onLoadSuccess:function(){
			$('#hf_combobox').combobox('select','null');	
		}
	});
}
loadPaginationForTable(HFListTable);
</script>
</html>