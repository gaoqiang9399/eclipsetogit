;
var MfVouAfterRiskLevelAdjust_SelectList = function(window,$){
	var _init=function(){
		$(function(){
		    myCustomScrollbar({
			obj : "#content", //页面内容绑定的id
			url : webPath + "/mfVouAfterRiskLevelAdjust/findVouAfterRiskLevelAdjustByPageAjax", //列表数据查询的url
			tableId : "tableMfVouAfterRiskLevelAdjustList", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:30 //加载默认行数(不填为系统默认行数)
			//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
		    });
		 });
	};
	// 选择委托担保合同编号
	var _choseVouAfterRiskLevelAdjust = function(url){
		var pactId = url.split("?")[1].split("=")[1];
        parent.dialog.get('vouAfterRiskLevelAdjust').close(pactId).remove();
	};
	return{
		init:_init,
		choseVouAfterRiskLevelAdjust:_choseVouAfterRiskLevelAdjust,
	};
}(window,jQuery);