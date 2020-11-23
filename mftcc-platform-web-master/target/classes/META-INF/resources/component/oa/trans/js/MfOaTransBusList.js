;
var OaTransBusList = function(window, $) {
	var _init = function () {	
		$("#content").mCustomScrollbar({
			advanced:{
				theme:"minimal-dark",
				updateOnContentResize:true
			}
		});
	};
	var _selectBusInfo = function() {
		var appTransNo="";
		var pactTransNo="";
		var busName= "";
		if($("#content input:checkbox[name='appId']:checked").length>0){
			$("#content input:checkbox[name='appId']:checked").each(function(){
				var parms = $(this).val();
				var pObj = StringUtil.urlParamsToObj(parms);
				console.log(pObj);
				if (pObj["pactNo"]== '') {	
					appTransNo=appTransNo + pObj["appId"]+",";
					busName = busName +pObj.appName+",";
				} else if (pObj["pactNo"]!= '') {
					pactTransNo = pactTransNo + pObj["pactId"]+",";	
					busName = busName +pObj.appName+",";				
				} else {
					console.error("参数错误：");
					console.error(pObj);
				}
			});		
		}
		var busTransInfo = appTransNo+";"+pactTransNo+";"+busName;
		parent.dialog.get("transBusInfo").close(busTransInfo);
		
	};
	var _closeDialog = function(){	
		parent.dialog.get("transBusInfo").close();
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		selectBusInfo:_selectBusInfo,
		closeDialog:_closeDialog
	};
}(window, jQuery);