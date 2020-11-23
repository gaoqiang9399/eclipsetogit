;
var FeeCollect = function(window,$){
	//收取借据的费用
	var _doFincFeeCollect = function(){
		var url = webPath+"/mfBusAppFee/getFincBusFeeCollect?fincId="+fincId+"&appId="+appId;
		top.openBigForm(url, "收取费用", function(){
			//刷新收费历史
			pubMfRepayFeeHistoryList.init();
		});
	};
	//收取借据的费用
	var _doFincFeePlan = function(){
		var url = webPath+"/mfBusAppFee/getFincBusAppFeeByAppId?fincId="+fincId+"&appId="+appId;
		top.openBigForm(url, "生成费用计划", function(){
			//刷新收费历史
			//pubMfRepayFeeHistoryList.init();
		});
	};
	return{
		doFincFeeCollect:_doFincFeeCollect,
		doFincFeePlan:_doFincFeePlan
	}
}(window,jQuery)