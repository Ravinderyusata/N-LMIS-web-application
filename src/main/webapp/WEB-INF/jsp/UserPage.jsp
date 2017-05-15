<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Page</title>
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
			break;
		case "SIO":
			$('#addBtn').hide();
			break;
		case "SIFP":
			$('#addBtn').hide();
			break;
		case "NTO":
			$('#overViewBtn').hide();
			break;
		case "LIO":
			$('#addBtn').hide();
			$('#editBtn').hide();
			break;
		case "MOH":
			$('#editBtn').hide();
			$('#addBtn').hide();
			break;
		}
		/* document.getElementById("common_lable").innerHTML = "Users";
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
#first_name_label,#last_name_label,#user_name_label,#user_types_label,#password_label,
#confirm_password_label,#role_label,#assign_lga_label,#start_date_label{
font-weight: bold;
}
</style>
<style>
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

</style>
</head>
<body style="margin: 0px;" onload="setRole()">
<!-- to check seesoin is null or not -->
		
	<%-- <!-- headr of page -->
	<jsp:include page="headerforpages.jsp"></jsp:include> --%>

	<!-- button bar -->

	<div class="button_bar" id="button_bar">
		<ul>
			<li><a id="addBtn" class="w3-btn w3-ripple" onclick="AddUser()" > <img alt="add"
					src="resources/images/file_add.png">Add
			</a>
			</li>
			<li><a id="editBtn" class="w3-btn w3-ripple" onclick="editUser(this.id)"> <img alt="edit"
					src="resources/images/file_edit.png">Edit
			</a></li>
			<li><a id="overViewBtn" class="w3-btn w3-ripple" onclick="editUser(this.id)"> <img alt="overViewBtn"
				src="resources/images/file_edit.png">OverView
			</a></li>
			<li><a class="w3-btn w3-ripple" onclick="handleHistory()"> <img alt="history"
					src="resources/images/file_history.png">History
			</a></li>
			<li><a class="w3-btn w3-ripple" href="user_list_export"> <img alt="export"
					src="resources/images/Export_load_upload.png">Export
			</a></li>
			<li><a class="w3-btn w3-ripple" onclick="handleChangePassword()"> <img alt="change password"
					src="resources/images/change_password.png">Change Password
			</a></li>
			<li><a class="w3-btn w3-ripple" onclick="refreshData()"> <img alt="refresh"
					src="resources/images/refreshIcon.png" >Refresh
			</a></li>
		</ul>
	</div>

<!-- toolbar for table -->
    <div id="table_toolbar" style="padding:2px 5px;">
        User Type:   <select id="usertype_combobox" class="easyui-combobox"  style="width:100px">
        </select>
        Role Name:   <select id="rolename_combobox" class="easyui-combobox"  style="width:100px">
        </select>
        Store Name: 
        <select id="assign_lga_combobox" class="easyui-combobox"  style="width:180px">
            
        </select>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">Search</a>
    </div>
    
	<!-- user table -->
	
	<div id="user_table_div" style="margin-left: 5px;">
	<table id="userListTable" class="easyui-datagrid"
		style="width: 100%; height: 430px;padding-left: 5px;" title="Users"
		data-options="toolbar:'#table_toolbar', rownumbers:'true',  singleSelect:'true',
		striped:'true', remoteSort:'false',pagination:'true',pageSize:20">	
	</table>
	</div>
	
	<!-- add edit form -->
	
	 <div id="form_dialog" class="easyui-dialog" style="width:430px;height:470px;padding:10px 20px"
                closed="true" buttons="#form_buttons">
            <f:form id="add_edit_form" method="post" commandName="beanForUser">
            <table cellspacing="10px;">
            <tr>
            <td>
	            <div id="first_name_div">
	                    <label id="first_name_label">*First Name:</label>
	                    <f:input  id="first_name_textbox"  class="easyui-textbox" path="x_FIRST_NAME"/>
	              </div>
            </td>
             <td>
             <div class="last_name_div">
                    <label id="last_name_label">*Last Name:</label>
                    <f:input id="last_name_textbox"  class="easyui-textbox" path="x_LAST_NAME"/>
                </div>
             </td>
            </tr>
             <tr>
            <td>
	            <div class="user_name_div_div">
                    <label id="user_name_label">*User Name:</label>
                    <f:input id="user_name_textbox"  class="easyui-textbox" path="x_LOGIN_NAME"/>
                </div>
            </td>
             <td>
             
                <div class="user_types_div">
                    <label id="user_types_label">*User Types:</label>
                    <f:select  id="usertype_combobox_form"  class="easyui-combobox" cssStyle="width:120px;" path="x_USER_TYPE_ID" />
                </div>
             </td>
            </tr>
              <tr>
            <td>
	            <div class="password_div">
                    <label id="password_label">*Password:</label>
                    <f:password id="password_textbox"  class="easyui-textbox" path="x_PASSWORD"/>
                </div>
            </td>
             <td>
             
                <div class="confirm_password_div">
                    <label id="confirm_password_label">*Confirm Password:</label>
                    <input type="password" id="confirm_password_textbox"  class="easyui-textbox" width="120px" />
                </div>
             </td>
            </tr>
            <tr>
            <td>
	            <div id="email_div">
                    <label id="email_label">Email:</label>
                    <f:input id="email_textbox"  
                     class="easyui-textbox " 
                     
                     validateType="email" path="x_EMAIL"/>
                </div>
            </td>
             <td>
             
                <div id="phone_no_div">
                    <label id="phone_no_label">Telephone Number:</label>
                    <f:input id="phone_no_textbox"  
                    class="easyui-textbox " width="120px"
                    
                     path="x_TELEPHONE_NUMBER"/>
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
             
                <div id="activated">
                    <f:checkbox   id="activeted_checkbox" path="x_ACTIVATED" value="Y"/>Activated 
                </div>
             </td>
            </tr>
            <tr>
            <td>
	            <div id="role_div">
	             <label id="role_label">*Role:</label>
                    <f:select id="rolename_combobox_form"  class="easyui-combobox" cssStyle="width:150px;" path="x_USER_ROLE_ID"/>
                </div>
            </td>
             <td>
             
                <div id="assign_lga_div">
                  <label id="assign_lga_label">*Assign LGA:</label>
                    <f:select id="assign_lga_combobox_form"  class="easyui-combobox" cssStyle="width:180px;" path="x_ASSIGN_LGA_ID"/>
                </div>
             </td>
            </tr>
            <tr>
            <td>
	            <div id="start_date_div">
	             <label id="start_date_label">*Start date:</label>
                    <f:input id="start_date"  class="easyui-datebox" width="120px" path="x_START_DATE" />
                </div>
            </td>
             <td>
             
                <div id="end_date_div">
                  <label id="end_date_label">End date:</label><br>
                    <f:input id="end_date"  class="easyui-datebox" width="120px;" path="x_END_DATE"/>
                </div>
             </td>
            </tr>
            </table>
            </f:form>
        </div>
        <div id="form_buttons">
            <a href="javascript:void(0)" id="saveBtn" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">Save</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#form_dialog').dialog('close')" style="width:90px">Cancel</a>
        </div>
        
        <!-- history div -->
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
        
        <!-- change password diologe div -->
     
        <div id="change_password_dialog" class="easyui-dialog" style="width:420px;height:250px;padding:10px" closed="true">
        <table align="center">
        <tr>
	        <td><b>*Old Password:</b></td>
	        <td><input type="password"  id="old_password" class="easyui-textbox"></td>
	        </tr>
	        <tr>
	        <td><b>*New Password:</b></td>
	        <td><input type="password" id="new_password" class="easyui-textbox"></td>
	        </tr>
	      <tr>
	        <td><b>*Confirm Password:</b></td>
	        <td><input type="password" id="confirm_password" class="easyui-textbox"></td>
	      </tr>
	      
	      <tr>
	      	<td >
	      		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="changePasswordActionOk()" style="width:90px">Ok</a>
	      	</td>
	      	<td>
	      	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#change_password_dialog').dialog('close')" style="width:90px">Cancel</a>
	      	</td>
	      </tr>
        </table>
        </div>
        
	
	<%-- <jsp:include page="footer-for-page.jsp"></jsp:include> --%>
	  <!-- loder div -->
		<div style="display: none;" id="loader_div" class="loader_div">
			<div class="loader" id="loader_show">
			</div>
		</div>
</body>
<script type="text/javascript" src="resources/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript"
	src="resources/easyui/jquery.easyui.min.js"></script>
<script src="resources/js/common.js"></script>
<script src="resources/js/datagrid_agination.js" type="text/javascript"></script>
<script type="text/javascript">
var submitType;
function doSearch(){
	if($('#usertype_combobox').combobox('getValue')==''){
		alertBox("Please Select User Type:");
	}else{
		var userTypeId=$('#usertype_combobox').combobox('getValue')==''?null:$('#usertype_combobox').combobox('getValue');
		var roleId=$('#rolename_combobox').combobox('getValue')==''?null:$('#rolename_combobox').combobox('getValue');
		var warehouseId=$('#assign_lga_combobox').combobox('getValue')==''?null:$('#assign_lga_combobox').combobox('getValue');
		var searchUrl="search_user_list?userTypeId="+userTypeId+"&roleId="+roleId+"&warehouseId="+warehouseId;
		$('#userListTable').datagrid('reload',searchUrl);
	} 
}
function alertBox(message){
	  $.messager.alert('Warning!',message,'warning');
}
function AddUser(){
	submitType="add";
    $('#form_dialog').dialog('open').dialog('center').dialog('setTitle','Add User');
    $('#add_edit_form').form('clear');
    $('#add_edit_form').attr('action','save_addedit_user?action=add');
    $('#rolename_combobox_form').combobox('enable',true);
    $('#assign_lga_combobox_form').combobox('enable',true);    
    $('#password_textbox').combobox('enable',true);
    $('#confirm_password_textbox').combobox('enable',true);
    $("#status_checkbox").prop("checked", true);
    $("#status_checkbox").attr("disabled", true);
    $("#activeted_checkbox").attr("disabled", true);
    $("#activeted_checkbox").prop("checked", false);
    $('#start_date').datebox('setValue',formateDate(new Date()));
    loadFormComboboxList();
    $('#usertype_combobox_form').combobox('enable',true);
}
   function loadFormComboboxList(){
	   $('#usertype_combobox_form').combobox({
			url : 'getUserTypelist',
			valueField : 'value',
			textField : 'label',
			formatter: function(row){
				var opts = $(this).combobox('options');
				if(row.label=='Employee'){
					row.label="User";
				}
				return row[opts.textField];
			},
			onSelect : function(userType) {
				$('#assign_lga_combobox_form').combobox('enable',true);
				$('#assign_lga_combobox_form').combobox('clear');
				$('#rolename_combobox_form').combobox({
					url : 'get_rolename_list?userType='+ userType.label,
					valueField : 'value',
					textField : 'label',
					onSelect : function(roleName) {
						$('#assign_lga_combobox_form').combobox('clear');
						$('#assign_lga_combobox_form').combobox({
							url : 'get_assignlga_list_forform',
							valueField : 'value',
							textField : 'label',
							queryParams:{userTypeId:userType.value,roleId:roleName.value},
							onLoadSuccess:function(){
								if($('#assign_lga_combobox_form').combobox('getData')==''){
									alertBox("LGA Not Available To Be Assign ");
									$('#assign_lga_combobox_form').combobox('setValue','null');
									$('#assign_lga_combobox_form').combobox('setText','LGA Not Available To Be Assign');
									$('#assign_lga_combobox_form').combobox('disable',true);
								}
							}
						});
						if(roleName.label=='SCCO' || roleName.label=='SIO' || roleName.label=='SIFP'){
							$('#assign_lga_label').text("*State Store");
							$('#assign_lga_combobox_form').combobox('setValue','${userBean.getX_WAREHOUSE_ID()}');
							$('#assign_lga_combobox_form').combobox('setText','${userBean.getX_WAREHOUSE_NAME()}');
						}else{
							$('#assign_lga_label').text("*Assign LGA");						
						}						
					}
				});
			}
		});
	}
function saveUser(){
	 $('#add_edit_form').form('submit',{
        url: $('#add_edit_form').attr('action'),
        onSubmit: function(){
        	var errormessage="";
        	var validate=true;
        	if($('#status_checkbox').is(':checked')){
        		$('#status_checkbox').attr('value','A');
        	}else{
        		$('#status_checkbox').attr('value','I')
        	}if($('#activeted_checkbox').is(':checked')){
        		$('#activeted_checkbox').attr('value','Y');
        	}else{
        		$('#activeted_checkbox').attr('value','N')
        	}
          if($('#first_name_textbox').textbox('getText')==''){
        	  errormessage="First Name is Empty!";
        	  validate=false;
          }else if($('#last_name_textbox').textbox('getText')==''){
        	  errormessage="Last Name is Empty!";
        	  validate=false;
          }else if($('#user_name_textbox').textbox('getText')==''){
        	  errormessage="User Name is Empty!";
        	  validate=false;
          }else if($('#usertype_combobox_form').combobox('getValue')==''){
        	  errormessage="User Type is Empty!";
        	  validate=false;
          }else if($('#password_textbox').textbox('getText')=='' && submitType!='edit'){
        	  errormessage="Password is Empty!";
        	  validate=false;
          }else if($('#confirm_password_textbox').textbox('getText')=='' && submitType!='edit'){
        	  errormessage="Confirm Password is Empty!";
        	  validate=false;
          }else if($('#password_textbox').textbox('getText')!=$('#confirm_password_textbox').textbox('getText')){
        	  errormessage="Password Not Match!";
        	  validate=false;
          }else if($('#email_textbox').textbox('getValue')!=''){
        	  if(!isEmail($('#email_textbox').textbox('getValue'))){
                  errormessage="Enter Valid Email Adress Or Leave Blank this Field";
                  validate=false;
               }else{
            	    if($('#phone_no_textbox').textbox('getValue')!=''){
                 	  if($('#phone_no_textbox').textbox('getValue').length!='11'
                           || isNaN($('#phone_no_textbox').textbox('getValue'))
                           || $('#phone_no_textbox').textbox('getValue')[0]!='0'){
                         		  errormessage="Telephone Number Format |eg. 0XXXXXXXXXX!";
                             	  validate=false;
                        }else if($('#rolename_combobox_form').combobox('getValue')==''){
                        	  errormessage="Role Name is Empty!";
                        	  validate=false;
                          }else if($('#assign_lga_combobox_form').combobox('getValue')==''){
                        	  errormessage="Assign LGA is Empty!";
                        	  validate=false;
                          }else if($('#start_date').datebox('getValue')==''){
                        	  errormessage="Start Date is Empty!";
                        	  validate=false;
                          }
                    }
               }
           }else if($('#phone_no_textbox').textbox('getValue')!=''){
          	  if($('#phone_no_textbox').textbox('getValue').length!='11'
                  || isNaN($('#phone_no_textbox').textbox('getValue'))
                  || $('#phone_no_textbox').textbox('getValue')[0]!='0'){
                		  errormessage="Telephone Number Format |eg. 0XXXXXXXXXX!";
                    	  validate=false;
               }else if($('#rolename_combobox_form').combobox('getValue')==''){
               	  errormessage="Role Name is Empty!";
            	  validate=false;
              }else if($('#assign_lga_combobox_form').combobox('getValue')==''){
            	  errormessage="Assign LGA is Empty!";
            	  validate=false;
              }else if($('#start_date').datebox('getValue')==''){
            	  errormessage="Start Date is Empty!";
            	  validate=false;
              }
           }else if($('#rolename_combobox_form').combobox('getValue')==''){
         	  errormessage="Role Name is Empty!";
       	  validate=false;
         }else if($('#assign_lga_combobox_form').combobox('getValue')==''){
       	  errormessage="Assign LGA is Empty!";
       	  validate=false;
         }else if($('#start_date').datebox('getValue')==''){
       	  errormessage="Start Date is Empty!";
       	  validate=false;
         }else if($('#rolename_combobox_form').combobox('getValue')==''){
        	  errormessage="Role Name is Empty!";
        	  validate=false;
          }else if($('#assign_lga_combobox_form').combobox('getValue')==''){
        	  errormessage="Assign LGA is Empty!";
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
        	if (result.toString()=='success'){
                  // close the dialog
//             	  alertBox("Operation Successfull");
                  $.messager.alert('Information', 'User Added Successfully', 'info');
            	  refreshData();
            } else {
//             	  alertBox("Operaction Failed");
            	  $.messager.alert('Error', 'Operaction Failed', 'error');
            }
        	 $('#form_dialog').dialog('close');
        }
    });
}
function editUser(buttonId){
	if(buttonId=='editBtn'){
		$('#saveBtn').linkbutton('enable',true);
	}else{
		$('#saveBtn').linkbutton('disable',true);
	}
	submitType="edit";
	var row = $('#userListTable').datagrid('getSelected');
	 loadFormComboboxList();
    $('#password_textbox').textbox('disable',true);	
    $('#confirm_password_textbox').textbox('disable',true);	
    if (row){
    	if(buttonId=='editBtn'){
    		  $('#form_dialog').dialog('open').dialog('center').dialog('setTitle','Edit User');
    	}else{
    		  $('#form_dialog').dialog('open').dialog('center').dialog('setTitle','OverView Of User');
    	}
        $('#first_name_textbox').textbox('setValue',row.FIRST_NAME);
        $('#first_name_textbox').textbox('setText',row.FIRST_NAME);
        $('#last_name_textbox').textbox('setValue',row.LAST_NAME);
        $('#last_name_textbox').textbox('setText',row.LAST_NAME);
        $('#user_name_textbox').textbox('setValue',row.LOGIN_NAME);
        $('#user_name_textbox').textbox('setText',row.LOGIN_NAME);
        $('#usertype_combobox_form').combobox('setValue',row.USER_TYPE_ID);
        $('#usertype_combobox_form').combobox('setText',row.USER_TYPE_NAME);
        $('#email_textbox').textbox('setValue',row.EMAIL);
        $('#email_textbox').textbox('setText',row.EMAIL);
        $('#phone_no_textbox').textbox('setValue',row.TELEPHONE_NUMBER);
        $('#phone_no_textbox').textbox('setText',row.TELEPHONE_NUMBER);
        $('#rolename_combobox_form').combobox('setValue',row.ROLE_ID);
        $('#rolename_combobox_form').combobox('setText',row.ROLE_NAME);
        if(row.ROLE_NAME=='SCCO' || row.ROLE_NAME=='SIO' || row.ROLE_NAME=='SIFP'){
			$('#assign_lga_label').text("*State Store");
		}else{
			$('#assign_lga_label').text("*Assign LGA");
		}
        $('#assign_lga_combobox_form').combobox('setValue',row.WAREHOUSE_ID);
        $('#assign_lga_combobox_form').combobox('setText',row.WAREHOUSE_NAME);
        var dates=formateDate(row.START_DATE);
        $('#start_date').datebox('setValue',dates);
        if(!isNaN(dates=formateDate(row.END_DATE))){
        	$('#end_date').datebox('setValue',dates);
        }
        if(row.STATUS=='A'){
        	$("#status_checkbox").prop("checked", true);
        }if(row.ACTIVATED=='Y'){
        	$("#activeted_checkbox").prop("checked", true);
        }
        $('#add_edit_form').attr('action','save_addedit_user?action=edit&userId='+row.USER_ID);
    }else{
    	alertBox("Please Select Record!");
    }
    $("#status_checkbox").attr("disabled", true);
    $("#activeted_checkbox").attr("disabled", true);
    $('#password_textbox').textbox('clear');
    $('#confirm_password_textbox').textbox('clear');
    $('#usertype_combobox_form').combobox('disable',true);
    $('#rolename_combobox_form').combobox('disable',true);
    $('#assign_lga_combobox_form').combobox('disable',true);
    
}

function refreshData(){
	$('#usertype_combobox').combobox('clear');
	$('#rolename_combobox').combobox('clear');
	$('#assign_lga_combobox').combobox('clear');
	$('#userListTable').datagrid('reload','getuserlist'); 
}
function handleHistory(){
	 var row = $('#userListTable').datagrid('getSelected');
	 document.getElementById("loader_div").style.display = "block";
	 if(row==null){
		 alertBox("Please Select Record From Table")
	 }else{
		 ajaxPostRequestSync("get_history_user", {user_id: row.USER_ID},
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
					 // document.getElementById("loader_div").style.display = "none";
				  });
		
	 }
	 document.getElementById("loader_div").style.display = "none";
}
function handleChangePassword(){
	 var row = $('#userListTable').datagrid('getSelected');
	 if(row==null){
		 alertBox("Please Select Record From Table")
	 }else{
		 $('#old_password').textbox('clear');
		 $('#new_password').textbox('clear');
		 $('#confirm_password').textbox('clear');
		 $('#change_password_dialog').dialog('open').dialog('center').dialog('setTitle','Change Password'); 
	 }
}
function changePasswordActionOk(){
	var row = $('#userListTable').datagrid('getSelected');
	if($('#old_password').val()==''){
		alertBox("Old Password Is Empty!");
		$('#old_password').focus();
	}else if($('#new_password').val()==''){
		alertBox("New Password Is Empty!");
	}else if($('#confirm_password').val()==''){
		alertBox("Confirm Password Is Empty!");
	}else if($('#confirm_password').val()!=$('#new_password').val()){
		alertBox("New Password And Confirm Password Does Note Match!");
	}else{
		document.getElementById("loader_div").style.display = "block";
		$('#change_password_dialog').dialog('close');
		var newPassword=$('#new_password').val();
		var oldPassword=$('#old_password').val();
		ajaxPostRequestSync("change_user_password", {user_id:row.USER_ID,oldPassword:oldPassword,newPassword:newPassword},
				function(response) {
			 document.getElementById("loader_div").style.display = "none";
				if(response=='succsess'){
					 alertBox(" Password Updated Succesufully");
						refreshData();
			  	}else{
			  		alertBox(" Old Password Is Not Correct");
			  	}
			});
		
	}
}
</script>
<script type="text/javascript">
$('#userListTable').datagrid({
    url  : 'getuserlist',
    remoteSort: false,
    columns: [[
               {field:'USER_ID',title:'USER ID',sortable:true,hidden:true},
               {field:'COMPANY_ID',title:'COMPANY ID',sortable:true,hidden:true},
               {field:'FIRST_NAME',title:'FIRST NAME',sortable:true},
               {field:'LAST_NAME',title:'LAST NAME',sortable:true},
             	{field:'LOGIN_NAME',title:'USERNAME',sortable:true},
               {field:'PASSWORD',title:'PASSWORD',sortable:true},
               {field:'STATUS',title:'STATUS',sortable:true},
               {field:'WAREHOUSE_ID',title:'WAREHOUSE ID',sortable:true,hidden:true},
               {field:'WAREHOUSE_NAME',title:'ASSIGNED LGA',sortable:true},
               {field:'USER_TYPE_NAME',title:'USER TYPE',sortable:true},
               {field:'USER_TYPE_ID',title:'USER TYPE ID',sortable:true,hidden:true},
               {field:'ACTIVATED',title:'ACTIVATED',sortable:true,hidden:true},
               {field:'ACTIVATED_ON',title:'ACTIVATED ON',sortable:true},
               {field:'EMAIL',title:'EMAIL',sortable:true},
               {field:'TELEPHONE_NUMBER',title:'TELEPHONE',sortable:true},
               {field:'START_DATE',title:'START DATE',sortable:true},
               {field:'END_DATE',title:'END_DATE',sortable:true},
               {field:'ROLE_ID',title:'ROLE_ID',sortable:true,hidden:true},
               {field:'ROLE_NAME',title:'ROLE NAME',sortable:true},
               {field:'ROLE_DETAILS',title:'ROLE DETAILS',sortable:true,hidden:true},
               {field:'FACILITY_FLAG',title:'FACILITY FLAG',sortable:true,            	   
				formatter : function(val) {
					if (val == "0") {
						return "No";
					} else {
						return "Yes";
					}
				}
		} ] ],
		onClickRow: function(index,row){
			if('${userBean.getX_ROLE_NAME()}'=='NTO'){
				if(row.ROLE_NAME!='NTO'){
					$('#editBtn').attr('class','w3-btn w3-disabled w3-ripple');
					$('#editBtn').attr('onclick','');
				}else{
					$('#editBtn').attr('class','w3-btn w3-ripple');
					$('#editBtn').attr('onclick','editUser(this.id)');
				}
			}else if('${userBean.getX_ROLE_NAME()}'=='SIO'){
				if(row.ROLE_NAME!='SIO'){
					$('#editBtn').attr('class','w3-btn w3-disabled w3-ripple');
					$('#editBtn').attr('onclick','');
				}else{
					$('#editBtn').attr('class','w3-btn w3-ripple');
					$('#editBtn').attr('onclick','editUser(this.id)');
				}
			}else if('${userBean.getX_ROLE_NAME()}'=='SIFP'){
				if(row.ROLE_NAME!='SIFP'){
					$('#editBtn').attr('class','w3-btn w3-disabled w3-ripple');
					$('#editBtn').attr('onclick','');
				}else{
					$('#editBtn').attr('class','w3-btn w3-ripple');
					$('#editBtn').attr('onclick','editUser(this.id)');
				}
			}
		}
	});
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
$('#usertype_combobox').combobox({
	url : 'getUserTypelist',
	valueField : 'value',
	textField : 'label',
	formatter: function(row){
		var opts = $(this).combobox('options');
		if(row.label=='Employee'){
			row.label="User";
		}
		return row[opts.textField];
	},
	onSelect : function(userType) {
		$('#assign_lga_combobox').combobox('enable',true);
		$('#assign_lga_combobox').combobox('clear');
		$('#rolename_combobox').combobox({
			url : 'get_rolename_list?userType='+userType.label,
			valueField : 'value',
			textField : 'label',
			onSelect : function(roleName) {
				$('#assign_lga_combobox').combobox('clear');
				$('#assign_lga_combobox').combobox({
					url : 'get_assignlga_list',
					valueField : 'value',
					textField : 'label',
					queryParams:{userTypeId:userType.value,roleId:roleName.value}
				});
			}
		});
	}
});
</script>
 <script type="text/javascript">
loadPaginationForTable(userListTable);
</script>

</html>