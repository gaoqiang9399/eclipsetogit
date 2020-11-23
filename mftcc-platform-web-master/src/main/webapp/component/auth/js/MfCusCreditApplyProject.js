;
var MfCusCreditApplyProject = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
		  myCustomScrollbar({
		    	obj:"#content",//页面内容绑定的id
		    	url:webPath +"/mfCusCreditApply/findByPageAjaxProject",//列表数据查询的url
		    	tableId:"tablecreditProjectQuery",//列表数据查询的table编号
		    	tableType:"thirdTableTag",//table所需解析标签的种类
		    	pageSize:30,//加载默认行数(不填为系统默认行数)
		    	topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
		    });
	};
	var _selectCredit = function(obj,url){
		//  /mfCusCreditApply/openHisData?wkfAppId=241423&cusNo=324242
		var cusNo = url.indexOf("cusNo=");
		top.toCollateralDetail = false;
		top.creditAppId = "";
		top.appId = "";
		top.openBigForm(webPath+url, '授信申请信息', function() {
			if(top.toCollateralDetail){
				window.location.href=webPath+"/mfBusCollateralRel/getCollateralInfo?cusNo="+cusNo+"&relId="+top.creditAppId+"&appId="+top.appId+"&entrance=credit";
			}
		});
	};

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		selectCredit : _selectCredit
	};
}(window, jQuery);