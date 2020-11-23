;
var MfAssetsManage_List = function(window, $) {
	var _init = function() {
		 myCustomScrollbar({
			obj : "#content", //页面内容绑定的id
			url : webPath + "/mfAssetsManage/findByPageAjax", //列表数据查询的url
			tableId : "tableAssetsManageList", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:30 //加载默认行数(不填为系统默认行数)
			//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
	    });
	};
	//跳转至新增
	var _applyInsert = function() {
		top.openBigForm(webPath+"/mfAssetsManage/input","新增", function(){
 			updateTableData();
 		});	
	};
	//跳转至详情
	var _getById = function(url) {
		top.openBigForm(webPath + url,"资产详情", function(){
 		});	
	};
	
	return {
		init : _init,
		getById:_getById,
		applyInsert:_applyInsert
	};
}(window, jQuery);
