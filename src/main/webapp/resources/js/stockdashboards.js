Date.prototype.getWeek = function() {
    var onejan = new Date(this.getFullYear(),0,1);
    /*var today = new Date(this.getFullYear(),this.getMonth(),this.getDate());*/
    /*var dayOfYear = ((today - onejan +1)/86400000);*/
    return Math.ceil((((this - onejan) / 86400000)) / 7);
    /*return (dayOfYear/7)*/
};
function getWeekNumber(date){
	if(date instanceof Date){
		return date.getWeek();
	}else{
		return 0;
	}	
}
function getLastWeek(){
    var today = new Date();
    var lastWeek = new Date(today.getFullYear(), today.getMonth(), today.getDate() - 7);
    return lastWeek ;
}

function filterStockDashboardGridData(url, lgaComboBoxId, yearComboBoxId, weekComboBoxId) {
	if ($('#'+lgaComboBoxId).combobox('getValue') == "") {alert("LGA is Empty");}
	else if ($('#'+yearComboBoxId).combobox('getValue') == "") {alert("Year is Empty");} 
	else if ($('#'+weekComboBoxId).combobox('getValue') == "") {alert("Week is Empty");} 
	else {
		var lga_id = $('#'+lgaComboBoxId).combobox('getValue');
		var year = $('#'+yearComboBoxId).combobox('getValue');
		var week = $('#'+weekComboBoxId).combobox('getValue');
		var url2 = url+"?year="+year+"&week="+week+"&lga_id="+lga_id;
		showTableData(url2);
	}
}
function loaddata(data) {
	var datacard = "<table style='width=80%'>";
	for (var i = 0; i < data.length; i++) {
		datacard +="<tr>";
		/* FOR TESTING: */
		/* h6-border-color:#dd5b25 */
		/* col l3 border color #fff259 */
		/* col l8 border color #de5145 */
		/* col l8 > .row border color #0f83c7 */
		/* col l1 border color #6d557d */
		/* col l1 > img border color #36626b */
		datacard += "<td style='width:20%;align:center;height: 100%;'><h6 style='line-height: 1px;margin-bottom: 0;margin-top: 0;padding-left: 3px;text-align: left;'>"+data[i].LGA_NAME+"</h6></td>";
		datacard +="<td style='width:80%;margin:0px;padding:0px;'><div style='display:inline-flex;width:100%;height:100%;'>"
		var col_to_group;
		if(!data[i].LESS_3_ANTIGENS_TOTAL_HF_PER=="0"){
			col_to_group=Math.round((data[i].LESS_3_ANTIGENS_TOTAL_HF_PER/100)*100);
			datacard += "<div style='width:"+col_to_group.toString()+"%;background-color:"
			+data[i].LESS_3_ANTIGENS_TOTAL_HF_PER_FLAG+";text-align:center;padding-top: 5px;'>"
			+data[i].LESS_3_ANTIGENS_TOTAL_HF_PER+"%</div>";
		}
		if(!data[i].GREATER_2_ANTIGENS_TOTAL_HF_PER=="0"){
			col_to_group=Math.round((data[i].GREATER_2_ANTIGENS_TOTAL_HF_PER/100)*100);
			datacard += "<div style='width:"+col_to_group.toString()+"%;background-color:"
			+data[i].GREATER_2_ANTIGENS_TOTAL_HF_PER_FLAG+";text-align:center;padding-top: 5px;'>"
			+data[i].GREATER_2_ANTIGENS_TOTAL_HF_PER+"%</div>";	
		}
		if(!data[i].SUFFICIENT_STOCK_TOTAL_HF_PER=="0"){
			col_to_group=Math.round((data[i].SUFFICIENT_STOCK_TOTAL_HF_PER/100)*100);
			datacard += "<div style='width:"+col_to_group.toString()+"%;background-color:"
			+data[i].SUFFICIENT_STOCK_TOTAL_HF_PER_FLAG+";text-align:center;padding-top: 5px;'>"
			+data[i].SUFFICIENT_STOCK_TOTAL_HF_PER+"%</div>";
		}
		datacard +="</div></td><td><img style='height:7px;width:26px;transform:rotate("+data[i].rotation+"deg)' src='resources/images/Arrow.png'></td></tr>";
	}
	$('#table_div').html(datacard);
	
}

function alertBox(message){
    $.messager.alert('Warning!',message,'warning');
}
function loaddataHeadTable3(data){
	var datacard="<tr>";
	for (var i = 0; i < data.length; i++) {
		datacard+="<td>"+data[i].product_name+"</td>";
	}
	datacard+="</tr>";
	document.getElementById("heading_table3").innerHTML=datacard;
}
function loaddataHeadTable5(data){
	var datacard="<tr>";
	for (var i = 0; i < data.length; i++) {
		datacard+="<td>"+data[i].product_name+"</td>";
	}
	datacard+="</tr>";
	document.getElementById("heading_table5").innerHTML=datacard;
}
function loadHeadingTable3(stateId,lgaName){
	document.getElementById("loader_div").style.display = "block";
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var ss = JSON.parse(xhttp.responseText);
			loaddataHeadTable3(ss);
		}
	};
	xhttp.open("GET", 'getheadingTable?lgaId='+stateId+'&lgaName='+lgaName, true);
	xhttp.send();
}
function loadHeadingTable5(stateId,lgaName){
	document.getElementById("loader_div").style.display = "block";
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var ss = JSON.parse(xhttp.responseText);
			loaddataHeadTable5(ss);
		}
	};
	xhttp.open("GET", 'getheadingTable?lgaId='+stateId+'&lgaName='+lgaName, true);
	xhttp.send();
}
function loaddata3(data) {
	var headingrow = document.getElementById("heading_table3").rows[0].cells;
	var datacard = "";
	for (var i = 0; i < data.length; i++) {
		datacard += "<tr>";
		datacard += "<td >" + data[i].LGA_NAME + "</td>";
		for (var rowid = 1; rowid < headingrow.length; rowid++) {
			$.each(data[i],function(lgaName, lgaValue) {
				if (typeof lgaValue == "object") {
					var color;
					var value;
					var label;
					$.each(lgaValue, function(itemName,
							itemValue) {
						if (itemName == "LEGEND_COLOR") {
							color = itemValue;
						} else {
							value = itemValue;
							label = itemName;
						}
					})
					if (label == headingrow[rowid].innerHTML) {
						datacard += "<td style='background-color:"+color+";align:center;'>"+ parseInt(value) + "</td>";
					}
				}
			})
		}
		datacard += "</tr>";
	}
	document.getElementById("table_body3").innerHTML = datacard;
}

function loaddata5(data) {
	var headingrow = document.getElementById("heading_table5").rows[0].cells;
	var datacard = "";
	for (var i = 0; i < data.length; i++) {
		datacard += "<tr>";
		datacard += "<td >" + data[i].LGA_NAME + "</td>";
		for (var rowid = 1; rowid < headingrow.length; rowid++) {
			$.each(data[i],function(lgaName, lgaValue) {
				if (typeof lgaValue == "object") {
					var color;
					var value;
					var label;
					$.each(lgaValue, function(itemName,
							itemValue) {
						if (itemName == "LEGEND_COLOR") {
							color = itemValue;
						} else {
							value = itemValue;
							label = itemName;
						}
					})
					if (label == headingrow[rowid].innerHTML) {
						datacard += "<td style='background-color:"+color+";align:center;'>"+ parseInt(value) + "</td>";
					}
				}
			})
		}
		datacard += "</tr>";
	}
	document.getElementById("table_body5").innerHTML = datacard;
}
function showTableData3(url) {
	/* document.getElementById("loader_div").style.display = "block"; */
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			document.getElementById("loader_div").style.display = "none";
			var ss = JSON.parse(xhttp.responseText);
			loaddata3(ss);
		}
	};
	xhttp.open("GET", url, true);
	xhttp.send();
}
function showTableData5(url) {
	/* document.getElementById("loader_div").style.display = "block"; */
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			document.getElementById("loader_div").style.display = "none";
			var ss = JSON.parse(xhttp.responseText);
			loaddata5(ss);
		}
	};
	xhttp.open("GET", url, true);
	xhttp.send();
}
function loaddataHeadTable4(data){
	var datacard="<tr>";
	for (var i = 0; i < data.length; i++) {
		datacard+="<td>"+data[i].product_name+"</td>";
	}
	datacard+="</tr>";
	document.getElementById("heading_table4").innerHTML=datacard;
}
function loadHeadingTable4(lgaId,lgaName){
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			var ss = JSON.parse(xhttp.responseText);
			loaddataHeadTable4(ss);
		}
	};
	xhttp.open("POST", 'getheadingTableForHfStockSummary?lgaName='+lgaName, true);
	xhttp.send();
}
function loaddata4(data) {
	var headingrow = document.getElementById("heading_table4").rows[0].cells;
	var datacard = "";
	console.log("data length: ", data.length);
	var activeButZeroDataHFList = data.pop();
	console.log("activeButZeroDataHFList: ", JSON.stringify(activeButZeroDataHFList));
	$('#activeHFCount').html("Active Facilities with Functional CCE : "+activeButZeroDataHFList.length);
	for (var i = 0; i < data.length; i++) {
		datacard += "<tr>";
		datacard += "<td>" + data[i].CUSTOMER_NAME + "</td>";
		for (var rowid = 1; rowid < headingrow.length; rowid++) {
			$.each(data[i],function(itemName, itemValue) {
				if(itemName==headingrow[rowid].innerHTML){
					var color;
					var stockBal;
					$.each(itemValue,function(label, value) {						
						if(label=='STOCK_BALANCE'){
							stockBal=value;
						}else{
							color=value;
						}
					})
					datacard += "<td style='background-color:"+color+";align:center;'>"
					+ parseInt(stockBal) + "</td>";
				}
			})
		}
		datacard += "</tr>";
	}
	document.getElementById("table_body4").innerHTML = datacard;
}
function showTableData4(url) {
	document.getElementById("loader_div").style.display = "block";
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			document.getElementById("loader_div").style.display = "none";
			var ss = JSON.parse(xhttp.responseText);
			loaddata4(ss);
		}
	};
	xhttp.open("GET", url, true);
	xhttp.send();
}

function loaddataNTO(data) {
	var datacard = "";
	var stateName="";
	var stateMatchName="";
	var datacard = "<table style='width=100%'>";
	for (var i = 0; i < data.length; i++) {
		datacard +="<tr>";
		stateName=data[i].STATE_NAME;
		if(stateMatchName!=stateName){
			datacard+="<td colspan='3' style='height: 100%;padding: 0px;margin: 0px;'><div class='row' style='line-height:19px; height: 100%;" +
					" background-color:#eeeeee;text-align:center;" +
					"margin-bottom:0px'>"+stateName+"</div></td></tr>";
			stateMatchName=stateName;
		}
		datacard += "<tr><td style='width:20%;'><h6 style='margin-top:4px;margin-bottom:3px;margin-left: 6px;line-height:0px;'>"+data[i].LGA_NAME+"</h6></td>";
		datacard += "<td style='width:70%;height: 100%;padding: 0px;margin: 0px;'>" +
				"<div style='display:inline-flex;width:100%;height:100%'>";
		var percentage;
		if(!data[i].REORDER_STOCK_COUNT_Y_PER=="0"){
			percentage=Math.round((data[i].REORDER_STOCK_COUNT_Y_PER));
			datacard += "<div style='width:"+percentage.toString()+"%;background-color:"
			+data[i].REORDER_STOCK_COUNT_Y_FLAG+";padding-top: 5px;text-align: center'>"
			+data[i].REORDER_STOCK_COUNT_Y_PER+"%</div>";
		}
		if(!data[i].INSUFFICIENT_STOCK_COUNT_R_PER=="0"){
			percentage=Math.round((data[i].INSUFFICIENT_STOCK_COUNT_R_PER));
			datacard += "<div style='width:"+percentage.toString()+"%;background-color:"
			+data[i].INSUFFICIENT_STOCK_COUNT_FLAG+";padding-top: 5px;text-align: center'>"
			+data[i].INSUFFICIENT_STOCK_COUNT_R_PER+"%</div>";	
		}
		if(!data[i].SUFFICIENT_STOCK_COUNT_G_PER=="0"){
			percentage=Math.round((data[i].SUFFICIENT_STOCK_COUNT_G_PER));
			datacard += "<div style='width:"+percentage.toString()+"%;background-color:"
			+data[i].SUFFICIENT_STOCK_COUNT_G_FLAG+";padding-top: 5px;text-align: center'>"
			+data[i].SUFFICIENT_STOCK_COUNT_G_PER+"%</div>";
		}
		datacard +="</div></td><td style='width:1%;'><img style='height:7px;width:26px;" +
				"transform:rotate("+data[i].rotation+"deg)' src='resources/images/Arrow.png'></td></tr>";
	}
	document.getElementById("table_divNTO").innerHTML = datacard;
}
function showTableDataNTO(url) {
	document.getElementById("loader_div").style.display = "block";
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {
			document.getElementById("loader_div").style.display = "none";
		var ss = JSON.parse(xhttp.responseText);
			loaddataNTO(ss);
		}
	};
	xhttp.open("POST", url, true);
	xhttp.send();
}
function filterGridDataNTO() {
	document.getElementById("exportLinkBtnNTO").href="get_state_stock_status_dashboard_export";
	if ($('#state_comboboxNTO').combobox('getValue') === "") {
		alert("State is Empty");
	}
	else if ($('#year_comboboxNTO').combobox('getValue') === "") {
		alert("Year is Empty");
	} else if ($('#week_comboboxNTO').combobox('getValue') === "") {
		alert("Week is Empty");
	} else {
		var lga_id = $('#state_comboboxNTO').combobox('getValue');
		var year = $('#year_comboboxNTO').combobox('getValue');
		var week = $('#week_comboboxNTO').combobox('getValue');
		var url = "get_state_stock_status_dashboard_data?year="+year+"&week="+week+"&lga_id="+lga_id;
		showTableDataNTO(url);
	}
}
function loadLgaAggStockDataNTO(url){
	document.getElementById("loader_div").style.display = "block";
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState === 4 && xhttp.status === 200) {
			document.getElementById("loader_div").style.display = "none";
		var ss = JSON.parse(xhttp.responseText);
			showLgaAggStockDataNTO(ss);
		}
	};
	xhttp.open("POST", url, true);
	xhttp.send();	
}
function showLgaAggStockDataNTO(data){
	var datacard = "<table style='width=100%'>";
	for (var i = 0; i < data.length; i++) {
		datacard +="<tr>";
		datacard += "<tr><td style='width:20%;'><h6 style='margin-top:4px;margin-bottom:3px;margin-left: 6px;line-height:0px;'>"+data[i].STATE_NAME+"</h6></td>";
		datacard += "<td style='width:70%;height: 100%;padding: 0px;margin: 0px;'>" +
				"<div style='display:inline-flex;width:100%;height:100%'>";
		var percentage;
		if(!data[i].LESS_3_ANTIGENS_TOTAL_HF_PER=="0"){
			percentage=Math.round((data[i].LESS_3_ANTIGENS_TOTAL_HF_PER));
			datacard += "<div style='width:"+percentage.toString()+"%;background-color:"
			+data[i].LESS_3_ANTIGENS_TOTAL_HF_PER_FLAG+";padding-top: 5px;text-align:center'>"
			+data[i].LESS_3_ANTIGENS_TOTAL_HF_PER+"%</div>";
		}
		if(!data[i].GREATER_2_ANTIGENS_TOTAL_HF_PER=="0"){
			percentage=Math.round((data[i].GREATER_2_ANTIGENS_TOTAL_HF_PER));
			datacard += "<div style='width:"+percentage.toString()+"%;background-color:"
			+data[i].GREATER_2_ANTIGENS_TOTAL_HF_PER_FLAG+";padding-top: 5px;text-align:center'>"
			+data[i].GREATER_2_ANTIGENS_TOTAL_HF_PER+"%</div>";	
		}
		if(!data[i].SUFFICIENT_STOCK_TOTAL_HF_PER=="0"){
			percentage=Math.round((data[i].SUFFICIENT_STOCK_TOTAL_HF_PER));
			datacard += "<div style='width:"+percentage.toString()+"%;background-color:"
			+data[i].SUFFICIENT_STOCK_TOTAL_HF_PER_FLAG+";padding-top: 5px;text-align:center'>"
			+data[i].SUFFICIENT_STOCK_TOTAL_HF_PER+"%</div>";
		}
		datacard +="</div></td><td style='width:1%;'><img style='height:7px;width:26px;" +
		"transform:rotate("+data[i].rotation+"deg)' src='resources/images/Arrow.png'></td></tr>";
	}
	document.getElementById("table_divNTO").innerHTML = datacard;
}
function lgaStockAggregatedDataNTO(){
	document.getElementById("exportLinkBtnNTO").href="get_lga_agg_stock_dashboard_export";
//	if ($('#state_comboboxNTO').combobox('getValue') == "") {
//		alert("State is Empty");
//	}else 
	if ($('#year_comboboxNTO').combobox('getValue') === "") {
		alert("Year is Empty");
	} else if ($('#week_comboboxNTO').combobox('getValue') === "") {
		alert("Week is Empty");
	} else {
		var lga_id = $('#state_comboboxNTO').combobox('getValue');
		var year = $('#year_comboboxNTO').combobox('getValue');
		var week = $('#week_comboboxNTO').combobox('getValue');
		var url = "get_lga_agg_stock_dashboard_data?year=" + year + "&week="+ week+"&lga_id="+lga_id;
		loadLgaAggStockDataNTO(url);
	}
}