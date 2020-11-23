;
var MfShareProfitConfig_Insert=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		$("[name='matchType']").attr("disabled","disabled");
		$("[name='calcBase']").attr("disabled","disabled");
		$("[name='calcMethod']").attr("disabled","disabled");
		$("[name='calcCoefficient']").attr("disabled","disabled");
		$("[name='calcFormula']").attr("disabled","disabled");
	};
	
	// 新增保存
	var _ajaxSave = function(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			$("[name='matchType']").removeAttr("disabled"); 
			$("[name='calcBase']").removeAttr("disabled"); 
			$("[name='calcMethod']").removeAttr("disabled"); 
			$("[name='calcCoefficient']").removeAttr("disabled"); 
			$("[name='calcFormula']").removeAttr("disabled"); 
			ajaxInsertCusForm(obj);
		}
	};
	
	// 选择分润配置后回调处理
	var _callBack = function (mfShareProfitConfig){
		$("[name='configName']").val(mfShareProfitConfig.configName);
		$("[name='matchType']").val(mfShareProfitConfig.matchType);
		$("[name='calcBase']").val(mfShareProfitConfig.calcBase);
		$("[name='calcMethod']").val(mfShareProfitConfig.calcMethod);
		$("[name='calcCoefficient']").val(mfShareProfitConfig.calcCoefficient);
		$("[name='calcFormula']").val(mfShareProfitConfig.calcFormula);
	}
	return{
		init:_init,
		ajaxSave:_ajaxSave,
		callBack:_callBack
	}
}(window,jQuery)