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