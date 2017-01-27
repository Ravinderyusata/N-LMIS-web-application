
function hideBeforCurrentDate(dateBoxId){
	$(dateBoxId).datebox().datebox('calendar').calendar({
        validator: function(date){
        	return date>new Date();
        }
    });
}
function hideAfterCurrentDate(dateBoxId){
	$(dateBoxId).datebox().datebox('calendar').calendar({
          validator: function(date){
        	  return date<=new Date();
          }
      });
	
}
function myformatter(date){
	//alert("in formate input data"+date)
	if(date!=null && date!=''){
	 var y = date.getFullYear();
	 var m = date.getMonth()+1;
	 var d = date.getDate();
	// alert("in my formater"+d+" "+m+" "+y)
	 return (d<10?('0'+d):d)+'-'+(m<10?('0'+m):m)+'-'+y;
	}
}
	function myparser(s){
	var months=['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
	//alert("parser input date"+s)
	if(s instanceof Date){
		return s;
	}
    var ss = (s.split('-'));
	var m;
    var d = parseInt(ss[0],10);
    if(typeof ss[1]=='string'){
    	//alert("month in string")
    	//alert("month in string"+ss[1])
    	 for (var i = 0; i < months.length; i++) {
			if(months[i]==ss[1]){
				m=i;
				break;
			}
		}
    	if(m==undefined){
    		 m =parseInt(ss[1],10);	
    		 m=m-1;
    	}
    }else{
    	//alert("month in interger")
    	 m =parseInt(ss[1],10);
    	//alert("month in intert"+m)
    	 m=m-1;
    }
    var y = parseInt(ss[2],10);
  //  alert("calculated"+d+" "+m+" "+y)
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
    	// alert(d+" "+m+" "+y)
        return new Date(y,m,d);
    } else {
        return new Date();
    }
}
	function formateDate(date){

	return date;
}
	function isEmail(email) {
  var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
  return regex.test(email);
}



//Ajax request by GET method with request header for ajax
function ajaxGetRequest(url, callFunction) {
	$.ajax({
		url: url, 
		type:'GET',
		beforeSend: function(xhr){
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhr.setRequestHeader("X-Requested-With", "ajax");
			},
		success: function(result){
			callFunction(result);
		
		},
	error: function(xhr,status,error){
		parent.window.location.href = 'logOutPage';
		}
	});
}

//Ajax request by POST method with request header for ajax
function ajaxPostRequest(url, queryString, callFunction) {
	$.ajax({
		url: url, 
		type:'POST',
		data:queryString,
		beforeSend: function(xhr){
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhr.setRequestHeader("X-Requested-With", "ajax");
			},
		success: function(result){
			callFunction(result);
		
		},
	error: function(xhr,status,error){
		parent.window.location.href = 'logOutPage';
		}
	});
}


//Ajax request by GET method with request header for ajax synchronous
function ajaxGetRequestSync(url, callFunction) {
	$.ajax({
		url: url, 
		type:'GET',
		async:false,
		beforeSend: function(xhr){
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhr.setRequestHeader("X-Requested-With", "ajax");
			},
		success: function(result){
			callFunction(result);
		
		},
	error: function(xhr,status,error){
		parent.window.location.href = 'logOutPage';
		}
	});
}
//Ajax request by Post method with request header for ajax synchronous
function ajaxPostRequestSync(url,queryString, callFunction) {
	$.ajax({
		url:url, 
		type:'POST',
		data:queryString,
		async:false,
		beforeSend: function(xhr){
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhr.setRequestHeader("X-Requested-With", "ajax");
			},
		success: function(result){
			callFunction(result);
		
		},
		error: function(xhr,status,error){
		parent.window.location.href = 'logOutPage';
		}
	});
}