;
var MfRiskRegistration_List = function(window, $) {

	var _init = function() {
		myCustomScrollbar({
			obj : "#content", // 页面内容绑定的id
			url : webPath + "/mfRiskLevelManage/findRiskRgtByPageAjax", // 列表数据查询的url
			tableId : "tableriskregistrationlist", // 列表数据查询的table编号
			tableType : "thirdTableTag", // table所需解析标签的种类
			pageSize : 30
		// 加载默认行数(不填为系统默认行数)
		});
	};


	// 获取详情信息
	var _getById = function(url) {
		top.window.openBigForm(webPath + url, '风险登记详情', function() {
		});
	}
	var _getDetailPage = function(obj, url) {
		top.window.openBigForm(webPath + url, '项目详情', function() {
		});
	}

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		getById : _getById,
		getDetailPage : _getDetailPage
	};
}(window, jQuery);
