$(function(){
	var hideFutureDate=$('.easyui-datebox').attr("name");
	if(hideFutureDate!='not_hide_future_date'){
		 $('.easyui-datebox').datebox().datebox('calendar').calendar({
		        validator: function(date){
		            var now = new Date();
		            var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());               
		            return date<=now;
		        }
		    });
	}
});
function myformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return (d<10?('0'+d):d)+'-'+(m<10?('0'+m):m)+'-'+y;
}
function myparser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(d,m,y);
    } else {
        return new Date();
    }
}
function isEmail(email) {
  var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
  return regex.test(email);
}