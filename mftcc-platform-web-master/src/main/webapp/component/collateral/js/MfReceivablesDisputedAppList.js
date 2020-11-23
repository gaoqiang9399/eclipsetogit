;
var MfReceivablesDisputedAppList = function(w,$){
	var _openDetail = function(url){
		top.updateFlag = false;
		window.parent.openBigForm(url,"争议申请详情",function(){
		},"80","92");
	};
	return {
		openDetail:_openDetail
	}
}(window,jQuery);