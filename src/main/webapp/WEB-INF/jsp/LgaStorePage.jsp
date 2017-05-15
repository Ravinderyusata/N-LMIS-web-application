<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LGA Store Page</title>
<link rel="stylesheet" href="resources/css/buttontoolbar.css"
	type="text/css">
<link rel=" stylesheet" href="resources/css/w3css.css" type="text/css">
<link rel="stylesheet" href="resources/css/table.css" type="text/css">
<link rel="stylesheet" type="text/css"
	href="resources/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="resources/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="resources/easyui/demo/demo.css">
<script type="text/javascript">
	function setRole() {
		var user = '${userBean.getX_ROLE_NAME()}';
		switch (user) {
		case "SCCO":
			$('#overViewBtn').hide();
			break;
		case "SIO":
			$('#editBtn').hide();
			$('#addBtn').hide();
			break;
		case "SIFP":
			$('#editBtn').hide();
			$('#addBtn').hide();
			break;
		case "NTO":
			$('#overViewBtn').hide();
			break;
		case "LIO":
			$('#editBtn').hide();
			$('#addBtn').hide();
			break;
		case "MOH":
			$('#editBtn').hide();
			$('#addBtn').hide();
			break;
		}
		/* document.getElementById("common_lable").innerHTML = "LGA Stores";
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
	0% {-webkit-transform: rotate(0deg);}
	100%{-webkit-transform:rotate(360deg);}
}
@keyframes spin { 
	0% {transform: rotate(0deg);}
	100%{transform:rotate(360deg);}
}
#store_code_label, #store_name_label, #store_types_label,
	#default_ord_store_label, #country_name_label, #state_name_label,
	#start_date_label, #mtp_label {
	font-weight: bold;
}
</style>
</head>
<body style="margin: 0px;" onload="setRole()">
	<!-- headr of page -->
	<%-- <jsp:include page="headerforpages.jsp"></jsp:include> --%>
	<!-- button bar -->
	<div class="button_bar" id="button_bar">
		<ul>
			<li><a id="addBtn" class="w3-btn w3-ripple"
				onclick="addLgaStore()"> <img alt="add"
					src="resources/images/file_add.png">Add
			</a></li>
			<li><a id="editBtn" class="w3-btn w3-ripple"
				onclick="editLgaStore(this.id)"> <img alt="edit"
					src="resources/images/file_edit.png">Edit
			</a></li>
			<li><a id="overViewBtn" class="w3-btn w3-ripple"
				onclick="editLgaStore(this.id)"> <img alt="overViewBtn"
					src="resources/images/file_edit.png">OverView
			</a></li>
			<li><a class="w3-btn w3-ripple" onclick="handleHistory()"> <img
					alt="history" src="resources/images/file_history.png">History
			</a></li>
			<li><a class="w3-btn w3-ripple" href="lga_store_list_export">
					<img alt="export" src="resources/images/Export_load_upload.png">Export
			</a></li>

			<li><a class="w3-btn w3-ripple" onclick="refreshLgaStoreList()">
					<img alt="refresh" src="resources/images/refreshIcon.png">Refresh
			</a></li>
		</ul>
	</div>


	<!-- toolbar for table -->
	<div id="table_toolbar" style="padding: 2px 5px;">
		Store Type: <select id="storetype_combobox" class="easyui-combobox"
			style="width: 150px">
		</select> Store Name: <select id="storename_combobox" class="easyui-combobox"
			style="width: 200px">
		</select> <a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-search" onclick="doSearch()">Search</a>
	</div>

	<!-- user table -->
	<div id="lga_store_table_div" style="margin-left: 5px;">
		<table id="lgaStoreListTable" class="easyui-datagrid"
			style="width: 100%; height: 430px;" title="LGA Store"
			data-options="toolbar:'#table_toolbar', rownumbers:'true', pagination:'true', singleSelect:'true' ,
		striped:'true', remoteSort:'false',pageSize:20">
		</table>
	</div>
	<!-- Lga Store Add/Edit form -->

	<div id="form_dialog" class="easyui-dialog"
		style="width: 430px; height: 480px; padding: 10px 20px" closed="true"
		buttons="#form_buttons">
		<f:form id="add_edit_form" method="post" commandName="lgaStoreFormBean">
			<table cellspacing="10px;">
				<tr>
					<td>
						<div id="store_code_div">
							<label id="store_code_label">*Store Code:</label>
							<f:input id="store_code_textbox" class="easyui-textbox"
								path="x_WAREHOUSE_CODE" />
						</div>
					</td>
					<td>
						<div class="store_name_div">
							<label id="store_name_label">*Store Name:</label>
							<f:input id="store_name_textbox" class="easyui-textbox"
								path="x_WAREHOUSE_NAME" />
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="description_div">
							<label id="description_label">Description:</label>
							<f:textarea id="description_textbox" class="easyui-textbox"
								path="x_WAREHOUSE_DESCRIPTION" />
						</div>
					</td>
					<td>

						<div class="store_types_div">
							<label id="store_types_label">*Store Type:</label>
							<f:select id="store_type_combobox_form" class="easyui-combobox"
								cssStyle="width:120px;" path="" />
							<input id="store_type_combobox_form_field" type="hidden"
								name="x_WAREHOUSE_TYPE_ID">
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="address_div">
							<label id="address_label">Address:</label>
							<f:input id="address_textbox" class="easyui-textbox"
								path="x_ADDRESS1" />
						</div>
					</td>
					<td>
						<div id="default_ord_store_div">
							<label id="default_ord_store_label">*Default Ordering
								Store:</label>
							<f:select id="default_ord_store_combobox_form"
								class="easyui-combobox" cssStyle="width:150px;" path="" />
							<input id="default_ord_store_combobox_form_field" type="hidden"
								name="x_DEFAULT_ORDERING_WAREHOUSE_ID">
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div id="country_name_div">
							<label id="country_name_label">*Country Name:</label>
							<f:select id="country_name_combobox_form" class="easyui-combobox"
								cssStyle="width:150px;" path="x_COUNTRY_ID" />
						</div>
					</td>
					<td>
						<div id="state_name_div">
							<label id="state_name_label">*State Name:</label>
							<f:select id="state_name_combobox_form" class="easyui-combobox"
								cssStyle="width:150px;" path="x_STATE_ID" />
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="phone_no_div">
							<label id="phone_no_label" class="label-top">Telephone
								No:</label>
							<f:input id="phone_no_textbox" class="easyui-textbox"
								path="x_TELEPHONE_NUMBER" />
						</div>
					</td>
					<td>
						<div id="status_div">
							<f:checkbox id="status_checkbox" path="x_STATUS" value="A" />
							Status
						</div>
					</td>
				</tr>

				<tr>
					<td>
						<div id="start_date_div">
							<label id="start_date_label">*Start date:</label>
							<f:input id="start_date" class="easyui-datebox" width="120px"
								path="x_START_DATE" />
						</div>
					</td>
					<td>

						<div id="end_date_div">
							<label id="end_date_label">End date:</label>
							<f:input id="end_date" class="easyui-datebox" width="120px;"
								path="x_END_DATE" />
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="mtp_div">
							<label id="mtp_label">*Monthly Target Population:</label><br>
							<f:input id="mtp_textbox" cssStyle="width:200px;"
								class="easyui-textbox" path="x_MONTHLY_TARGET_POPULATION" />
						</div>
					</td>
				</tr>
			</table>
		</f:form>
	</div>
	<!-- form button  -->
	<div id="form_buttons">
		<a href="javascript:void(0)" id="saveBtn" class="easyui-linkbutton c6"
			iconCls="icon-ok" onclick="saveLgaStore()" style="width: 90px">Save</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#form_dialog').dialog('close')"
			style="width: 90px">Cancel</a>
	</div>

	<!-- history lga div -->

	<div id="history_dialog" class="easyui-dialog"
		style="width: 420px; height: 250px; padding: 10px" closed="true">
		<table align="center">
			<tr>
				<td>Created By:</td>
				<td><label id="createdBylabel"></label></td>
			</tr>
			<tr>
				<td>Created On:</td>
				<td><label id="createdOnlabel"></label></td>
			</tr>
			<tr>
				<td>Updated By:</td>
				<td><label id="updatedBylabel"></label></td>
			</tr>
			<tr>
				<td>Last updated On</td>
				<td><label id="updatedOnlabel"></label></td>
			</tr>
			<tr>
				<td colspan="2" align="center" style=""><a
					href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-ok"
					onclick="javascript:$('#history_dialog').dialog('close')"
					style="width: 90px">Ok</a></td>
				<td></td>
			</tr>
		</table>
	</div>

	<%-- <jsp:include page="footer-for-page.jsp"></jsp:include> --%>
	<!-- loder div -->
	<div style="display: none;" id="loader_div" class="loader_div">
		<div class="loader" id="loader_show"></div>
	</div>

</body>

<script type="text/javascript" src="resources/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript"
	src="resources/easyui/jquery.easyui.min.js"></script>
<script src="resources/js/common.js"></script>
<script src="resources/js/datagrid_agination.js" type="text/javascript"></script>

<script type="text/javascript">
	hideAfterCurrentDate('#start_date');//for disable after current date 
	hideBeforCurrentDate('#end_date');//for disable before current date 
	$('#start_date').datebox({
		formatter : myformatter,
		parser : myparser
	});
	$('#end_date').datebox({
		formatter : myformatter,
		parser : myparser
	});
	function doSearch() {
		if ($('#storetype_combobox').combobox('getValue') == '') {
			alertBox("Store Type is Empty!")
		} else {
			var storeTypeId = $('#storetype_combobox').combobox('getValue');
			var storeTypeName = $('#storetype_combobox').combobox('getText');
			var storeNameId = $('#storename_combobox').combobox('getValue');
			var searchUrl = "search_lga_store_list?storeTypeId=" + storeTypeId
					+ "&storeNameId=" + storeNameId + "&storeTypeName="
					+ storeTypeName;
			$('#lgaStoreListTable').datagrid('reload', searchUrl);
		}
	}

	function addLgaStore() {
		submitType = "add";
		var validate = true;
		$('#form_dialog').dialog('open').dialog('center').dialog('setTitle', 'Add LGA Store');
		$('#add_edit_form').form('clear');
		$("#status_checkbox").prop("checked", true);
		$('#start_date').datebox('setValue', formateDate(new Date()));
		$('#add_edit_form').attr('action', 'save_addedit_lgastore?action=add');
		loadFormComboboxListsForForm();
		$('#default_ord_store_combobox_form').combobox('enable');
	}

	function saveLgaStore() {
		$('#add_edit_form').form('submit',
				{
					url : $('#add_edit_form').attr('action'),
					onSubmit : function() {
						$('#store_type_combobox_form_field').val($('#store_type_combobox_form').combobox('getValue'));
// 						$('#default_ord_store_combobox_form_field').val($('#default_ord_store_combobox_form').combobox('getValue'));
						var errormessage = "";
						var validate = true;
						if ($('#status_checkbox').is(':checked')) {
							$('#status_checkbox').attr('value', 'A');
						} else {
							$('#status_checkbox').attr('value', 'I')
						}
						if ($('#store_code_textbox').textbox('getText') == '') {
							errormessage = "Store Code is Empty!";
							validate = false;
						} else if ($('#store_name_textbox').textbox('getText') == '') {
							errormessage = "Store Name is Empty!";
							validate = false;
						} else if ($('#store_type_combobox_form').combobox('getValue') == '') {
							errormessage = "Store Type is Empty!";
							validate = false;
						} else if ($('#default_ord_store_combobox_form').combobox('getValue') == '') {
							if ($('#store_type_combobox_form').combobox('getText') != 'National') {
								errormessage = "Default Ordering Store is Empty!";
								validate = false;
							}
						} else if ($('#country_name_combobox_form').combobox('getValue') == '') {
							errormessage = "Country Name is Empty!";
							validate = false;
						} else if ($('#state_name_combobox_form').combobox('getValue') == '') {
							errormessage = "State Name is Empty!";
							validate = false;
						} else if ($('#phone_no_textbox').textbox('getValue') != '') {
							if ($('#phone_no_textbox').textbox('getValue').length != '11' 
									|| isNaN($('#phone_no_textbox').textbox('getValue'))
									|| $('#phone_no_textbox').textbox('getValue')[0] != '0') {
								errormessage = "Telephone Number Format |eg. 0XXXXXXXXXX!";
								validate = false;
							}
						} else if ($('#start_date').datebox('getValue') == '') {
							errormessage = "Start Date is Empty!";
							validate = false;
						} else if ($('#mtp_textbox').textbox('getText') == '') {
							errormessage = "Monthly Transaction Population is Empty!";
							validate = false;
						}
						if (errormessage != '') {
							alertBox(errormessage);
						}
						return true;
					},
					success : function(result) {
						if (result.toString() == 'success') {
// 							alertBox("Operation Successfull");
							$.messager.alert('Information', 'Store Added Successfully', 'info');
							refreshLgaStoreList();
						} else {
							$.messager.alert('Error', 'Operaction Failed', 'error');
// 							alertBox("Operaction Failed");
						}
						$('#form_dialog').dialog('close');
					}
				});
	}
	function editLgaStore(buttonId) {
		if (buttonId == 'editBtn') {
			$('#saveBtn').linkbutton('enable', true);
		} else {
			$('#saveBtn').linkbutton('disable', true);
		}
		submitType = "edit";
		var row = $('#lgaStoreListTable').datagrid('getSelected');
		$('#default_ord_store_combobox_form').combobox('enable', true);
		$('#store_type_combobox_form').combobox('disable', true);
		$("#status_checkbox").prop("checked", false);
		if (row) {
			if (buttonId == 'editBtn') {
				$('#form_dialog').dialog('open').dialog('center').dialog(
						'setTitle', 'Edit LGA Store');
			} else {
				$('#form_dialog').dialog('open').dialog('center').dialog(
						'setTitle', 'OverView Of LGA Store');
			}
			$('#store_code_textbox').textbox('setValue', row.WAREHOUSE_CODE);
			$('#store_code_textbox').textbox('setText', row.WAREHOUSE_CODE);
			$('#store_name_textbox').textbox('setValue', row.WAREHOUSE_NAME);
			$('#store_name_textbox').textbox('setText', row.WAREHOUSE_NAME);
			$('#description_textbox').textbox('setValue', row.WAREHOUSE_DESCRIPTION);
			$('#description_textbox').textbox('setText', row.WAREHOUSE_DESCRIPTION);
			$('#store_type_combobox_form').combobox('setValue', row.WAREHOUSE_TYPE_ID);
			if (row.WAREHOUSE_TYPE_NAME == 'Master Warehouse') {
				$('#store_type_combobox_form').combobox('setText', 'National');
				$('#default_ord_store_combobox_form').combobox('disable');
			} else {
				$('#store_type_combobox_form').combobox('setText', row.WAREHOUSE_TYPE_NAME);
				$('#default_ord_store_combobox_form').combobox('readonly');
			}
			$('#store_type_combobox_form_field').val(row.WAREHOUSE_TYPE_ID);
			$('#store_type_combobox_form_field').text(row.WAREHOUSE_TYPE_NAME);
			$('#address_textbox').textbox('setText', row.ADDRESS1);
			$('#default_ord_store_combobox_form').combobox('setValue', row.DEFAULT_ORDERING_WAREHOUSE_ID);
			$('#default_ord_store_combobox_form').combobox('setValue', row.DEFAULT_ORDERING_WAREHOUSE_NAME);
			$('#default_ord_store_combobox_form_field').val(row.DEFAULT_ORDERING_WAREHOUSE_ID);
			$('#default_ord_store_combobox_form_field').text(row.DEFAULT_ORDERING_WAREHOUSE_NAME);
			$('#country_name_combobox_form').combobox('setValue', row.COUNTRY_ID);
			$('#country_name_combobox_form').combobox('setText', row.COUNTRY_NAME);
			$('#state_name_combobox_form').combobox('setValue', row.STATE_ID);
			$('#state_name_combobox_form').combobox('setText', row.STATE_NAME);
			$('#phone_no_textbox').textbox('setValue', row.TELEPHONE_NUMBER);
			$('#phone_no_textbox').textbox('setText', row.TELEPHONE_NUMBER);
			if (row.STATUS == 'A') {
				$("#status_checkbox").prop("checked", true);
			}
			var dates = formateDate(row.START_DATE);
			$('#start_date').datebox('setValue', dates);
			if (!isNaN(dates = formateDate(row.END_DATE))) {
				$('#end_date').datebox('setValue', dates);
			}
			$('#mtp_textbox').textbox('setValue', row.MONTHLY_TARGET_POPULATION);
			$('#mtp_textbox').textbox('setText', row.MONTHLY_TARGET_POPULATION);
			$('#add_edit_form').attr('action','save_addedit_lgastore?action=edit&warehouseId='+ row.WAREHOUSE_ID);
		} else {
			alertBox("Please Select Record!");
		}
	}
	function refreshLgaStoreList() {
		$('#storetype_combobox').combobox('clear');
		$('#storename_combobox').combobox('clear');
		$('#lgaStoreListTable').datagrid('reload', 'getlgastorelist');
	}
	function alertBox(message) {
		$.messager.alert('Warning!', message, 'warning');
	}
	function handleHistory() {
		var row = $('#lgaStoreListTable').datagrid('getSelected');
		document.getElementById("loader_div").style.display = "block";
		if (row == null) {
			alertBox("Please Select Record From Table")
		} else {
			ajaxPostRequestSync("get_LgaStore_history", {
				WAREHOUSE_ID : row.WAREHOUSE_ID
			}, function(response) {
				if (response[0].CREATED_BY == ''
						|| response[0].CREATED_BY == null) {
					$('#createdBylabel').text("<Not Available>");
				} else {
					$('#createdBylabel').text(response[0].CREATED_BY);
				}
				$('#createdOnlabel').text(response[0].CREATED_ON);
				$('#updatedBylabel').text(response[0].UPDATED_BY);
				$('#updatedOnlabel').text(response[0].LAST_UPDATED_ON);
				$('#history_dialog').dialog('open').dialog('center').dialog('setTitle', 'User Record History');
			});

			document.getElementById("loader_div").style.display = "none";
		}
	}
	function alertBox(message) {
		$.messager.alert('Warning!', message, 'warning');
	}
	function loadFormComboboxListsForForm() {
		$('#store_type_combobox_form').combobox({
			url : 'get_Store_type_list_for_form',
			valueField : 'value',
			textField : 'label',
			onSelect : function(storeType) {
				$('#default_ord_store_combobox_form').combobox('clear');
				$('#default_ord_store_combobox_form').combobox({
					url : 'storeTypeList',
					valueField : 'value',
					textField : 'label',
					queryParams : {
						storeTypeLabel : storeType.label
					}
				});
			},
			onLoadSuccess : function(storeType) {
				if ('${userBean.getX_ROLE_NAME()}' == 'SCCO' && submitType == 'add') {
					$('#default_ord_store_combobox_form').combobox('disable', true);
					$('#store_type_combobox_form').combobox('disable', true);
					$('#store_type_combobox_form').combobox('setValue',$('#store_type_combobox_form').combobox('getData')[0].value);
					$('#store_type_combobox_form_field').val($('#store_type_combobox_form').combobox('getData')[0].value);
					$('#store_type_combobox_form_field').text($('#store_type_combobox_form').combobox('getData')[0].label);
					$('#default_ord_store_combobox_form').combobox('setValue','${userBean.getX_WAREHOUSE_ID()}');
					$('#default_ord_store_combobox_form').combobox('setText','${userBean.getX_WAREHOUSE_NAME()}');
					$('#default_ord_store_combobox_form_field').text('${userBean.getX_WAREHOUSE_NAME()}');
					$('#default_ord_store_combobox_form_field').val('${userBean.getX_WAREHOUSE_ID()}');
				}
			}
		});
		$('#country_name_combobox_form').combobox({
			valueField : 'value',
			textField : 'label',
			data : [ {
				value : '220210',
				label : 'NIGERIA'
			} ],
			onSelect : function(country) {
				$('#state_name_combobox_form').combobox({
					url : 'get_state_name_for_form',
					valueField : 'value',
					textField : 'label',
					queryParams : {
						countryId : country.value
					}
				});
			},
			onLoadSuccess : function(data) {
				$('#country_name_combobox_form').combobox('select', data[0].value);
			}
		});
	}
</script>

<script type="text/javascript">
	$('#lgaStoreListTable').datagrid({
		url : 'getlgastorelist',
		remoteSort : false,
		columns : [ [ {
			field : 'WAREHOUSE_NAME',
			title : 'Store Name',
			sortable : true
		}, {
			field : 'WAREHOUSE_TYPE_NAME',
			title : 'Store Type',
			sortable : true,
			align : 'center',
			formatter : function(value, row, index) {
				if (row.WAREHOUSE_TYPE_NAME == 'Master Warehouse') {
					return 'National';
				} else {
					return value;
				}
			}
		}, {
			field : 'MONTHLY_TARGET_POPULATION',
			title : 'MTP',
			sortable : true,
			align : 'center'
		}, {
			field : 'TELEPHONE_NUMBER',
			title : 'Telephone Number',
			sortable : true,
			align : 'center'
		}, {
			field : 'DEFAULT_ORDERING_WAREHOUSE_NAME',
			title : 'Default Ordering Store',
			sortable : true
		}, {
			field : 'STATUS',
			title : 'Status',
			sortable : true,
			align : 'center'
		}, {
			field : 'START_DATE',
			title : 'Start Date',
			sortable : true,
			align : 'center'
		}, {
			field : 'END_DATE',
			title : 'End Date',
			sortable : true
		}, {
			field : 'ADDRESS1',
			title : 'ADDRESS1',
			sortable : true,
			hidden : 'true'
		}, {
			field : 'ADDRESS2',
			title : 'ADDRESS2',
			sortable : true,
			hidden : 'true'
		}, {
			field : 'ADDRESS3',
			title : 'ADDRESS3',
			sortable : true,
			hidden : 'true'
		}, {
			field : 'CITY_ID',
			title : 'CITY ID',
			sortable : true,
			align : 'center',
			hidden : 'true'
		}, {
			field : 'COMPANY_ID',
			title : 'COMPANY ID',
			sortable : true,
			align : 'center',
			hidden : 'true'
		}, {
			field : 'COUNTRY_CODE',
			title : 'COUNTRY CODE',
			sortable : true,
			hidden : 'true'
		}, {
			field : 'COUNTRY_ID',
			title : 'COUNTRY ID',
			sortable : true,
			hidden : 'true'
		}, {
			field : 'COUNTRY_NAME',
			title : 'COUNTRY NAME',
			sortable : true,
			hidden : 'true'
		}, {
			field : 'DEFAULT_ORDERING_WAREHOUSE_ID',
			title : 'DEFAULT ORDERING WAREHOUSE ID',
			sortable : true,
			hidden : 'true'
		}, {
			field : 'MONTHLY_PREGNANT_WOMEN_TP',
			title : 'MONTHLY PREGNANT WOMEN TP',
			sortable : true,
			align : 'center',
			hidden : 'true'
		}, {
			field : 'STATE_CODE',
			title : 'STATE CODE',
			sortable : true,
			hidden : 'true'
		}, {
			field : 'STATE_ID',
			title : 'STATE ID',
			sortable : true,
			align : 'center',
			hidden : 'true'
		}, {
			field : 'STATE_NAME',
			title : 'STATE NAME',
			sortable : true,
			hidden : 'true'
		}, {
			field : 'TOTAL_POPULATION',
			title : 'TOTAL POPULATION',
			sortable : true,
			align : 'center',
			hidden : 'true'
		}, {
			field : 'WAREHOUSE_CODE',
			title : 'WAREHOUSE CODE',
			sortable : true,
			hidden : 'true'
		}, {
			field : 'WAREHOUSE_DESCRIPTION',
			title : 'WAREHOUSE DESCRIPTION',
			sortable : true,
			hidden : 'true'
		}, {
			field : 'WAREHOUSE_ID',
			title : 'WAREHOUSE ID',
			sortable : true,
			align : 'center',
			hidden : 'true'
		}, {
			field : 'WAREHOUSE_TYPE_CODE',
			title : 'WAREHOUSE TYPE CODE',
			sortable : true,
			hidden : 'true'
		}, {
			field : 'WAREHOUSE_TYPE_DESC',
			title : 'WAREHOUSE TYPE DESC',
			sortable : true,
			hidden : 'true'
		}, {
			field : 'WAREHOUSE_TYPE_ID',
			title : 'WAREHOUSE TYPE ID',
			sortable : true,
			hidden : 'true'
		}, {
			field : 'YEARLY_PREGNANT_WOMEN_TP',
			title : 'YEARLY PREGNANT WOMEN TP',
			sortable : true,
			align : 'center',
			hidden : 'true'
		}, {
			field : 'YEARLY_TARGET_POPULATION',
			title : 'YEARLY TARGET POPULATION',
			sortable : true,
			align : 'center',
			hidden : 'true'
		} ] ]

	});
</script>
<script type="text/javascript">
	$('#storetype_combobox').combobox({
		url : 'get_Store_type_list',
		valueField : 'value',
		textField : 'label',
		formatter : function(row) {
			var opts = $(this).combobox('options');
			if (row.label == 'Master Warehouse') {
				row.label = "National";
			}
			return row[opts.textField];
		},
		onSelect : function(storeTypeId) {
			$('#storename_combobox').combobox('clear');
			$('#storename_combobox').combobox({
				url : 'get_storename_acco_storetype_list',
				valueField : 'value',
				textField : 'label',
				queryParams : {
					storeTypeId : storeTypeId.value
				}
			});
		}
	});
	//for pageination
	loadPaginationForTable(lgaStoreListTable);
</script>
</html>