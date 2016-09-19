
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
	var months=[
			'Jan',
			'Feb',
			'Mar',
			'Apr',
			'May',
			'Jun',
			'Jul',
			'Aug',
			'Sep',
			'Oct',
			'Nov',
			'Dec'
			];
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
    	alert("month in interger")
    	 m =parseInt(ss[1],10);
    	alert("month in intert"+m)
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

//for pagination enable
(function($){
	function pagerFilter(data){
		if ($.isArray(data)){	// is array
			data = {
				total: data.length,
				rows: data
			}
		}
		var target = this;
		var dg = $(target);
		var state = dg.data('datagrid');
		var opts = dg.datagrid('options');
		if (!state.allRows){
			state.allRows = (data.rows);
		}
		
		var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
		var end = start + parseInt(opts.pageSize);
		data.rows = state.allRows.slice(start, end);
		return data;
	}

	var loadDataMethod = $.fn.datagrid.methods.loadData;
	$.extend($.fn.datagrid.methods, {
		clientPaging: function(jq){
			return jq.each(function(){
				var dg = $(this);
                var state = dg.data('datagrid');
                var opts = state.options;
                opts.loadFilter = pagerFilter;
                var onBeforeLoad = opts.onBeforeLoad;
                opts.onBeforeLoad = function(param){
                    state.allRows = null;
                    return onBeforeLoad.call(this, param);
                }
                var pager = dg.datagrid('getPager');
				pager.pagination({
					onSelectPage:function(pageNum, pageSize){
						opts.pageNumber = pageNum;
						opts.pageSize = pageSize;
						pager.pagination('refresh',{
							pageNumber:pageNum,
							pageSize:pageSize
						});
						dg.datagrid('loadData',state.allRows);
					},
					onRefresh:function(){
						dg.datagrid('reload');
					}
				});
               
			});
		},
        loadData: function(jq, data){
        	jq.each(function(){
                $(this).data('datagrid').allRows = null;
            });
            return loadDataMethod.call($.fn.datagrid.methods, jq, data);
        },
      
        getAllRows: function(jq){
        	return $(this).datagrid('getRows');
        }
	})
})(jQuery);

function loadPaginationForTable(tableId){
	$(function(){
		$(tableId).datagrid('clientPaging');
	});
}	