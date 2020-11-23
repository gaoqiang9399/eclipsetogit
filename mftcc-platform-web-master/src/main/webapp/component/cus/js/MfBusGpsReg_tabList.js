;
var MfBusGpsReg_tabList = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content"
		});
	};
	//新增gps信息
	var _addGpsFormInfo = function(){
		top.flag = false;
		top.gpsListInfo = "";
		top.openBigForm(webPath + "/mfBusGpsReg/inputForList?appId="+appId, "GPS登记", addGpsFormInfoCall);
	};
	
	//gps登记保存后回调
	var addGpsFormInfoCall = function(){
		if(top.flag){
			$("#gpsInfoListDiv").html("").html(top.gpsListInfo);
			location.reload();
		}
	};
	
	return {
		init : _init,
		addGpsFormInfo : _addGpsFormInfo
	};
}(window, jQuery);