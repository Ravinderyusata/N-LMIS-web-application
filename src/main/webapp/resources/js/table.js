$(document).ready(function(){
	$(".data_table tr").click(function(){
	    $(this).addClass("higligh-row").siblings().removeClass("higligh-row");
	});
});
