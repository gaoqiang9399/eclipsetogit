;
var MfRiskLevelManage_List = function(window, $) {

	var _init = function() {
		var tableId = "tablecusrisklevelmanagelist";
		if (comeFrom == "2") {
			tableId = "tablerisklevelmanagelist";
		}
		myCustomScrollbar({
			obj : "#content", // 页面内容绑定的id
			url : webPath + "/mfRiskLevelManage/findByPageAjax?comeFrom="
					+ comeFrom, // 列表数据查询的url
			tableId : tableId, // 列表数据查询的table编号
			tableType : "thirdTableTag", // table所需解析标签的种类
			pageSize : 30
		// 加载默认行数(不填为系统默认行数)
		});
	};

	// 风险处置，包括降级，升级，解除，处置
	var _adjustRiskLevel = function(url) {
		top.window.openBigForm(webPath + url, '风险处置', function() {
			updateTableData();
		});
	}

	// 获取详情信息
	var _getById = function(url) {
		top.window.openBigForm(webPath + url, '风险管理详情', function() {
		});
	}
	var _getDetailPage = function(obj, url) {
		top.window.openBigForm(webPath + url, '项目详情', function() {
		});
	}
	var _applyInsert = function() {
		var url = webPath + "/mfRiskLevelManage/input";
		top.window.openBigForm(url, '新增风险处置', function() {
			updateTableData();
		});
	}

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		adjustRiskLevel : _adjustRiskLevel,
		getById : _getById,
		getDetailPage : _getDetailPage,
		applyInsert : _applyInsert
	};
}(window, jQuery);
