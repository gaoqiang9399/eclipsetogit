;
var MfCusInfoChange_List = function(window, $) {

	var _init = function() {
		myCustomScrollbar({
			obj : "#content", //页面内容绑定的id
			url : webPath + "/mfCusInfoChange/findByPageAjax?cusNo=" + cusNo, //列表数据查询的url
			tableId : "tableinfochangelist", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:30 //加载默认行数(不填为系统默认行数)
			//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
		    });
	};
	
	// 获取详情信息
	var _getById = function(url) {
		top.window.openBigForm(webPath + url + "&entryFlag=1",'客户信息调整详情',function(){
			updateTableData();
		});
	}

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		getById:_getById
	};
}(window, jQuery);
