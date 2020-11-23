;
var MfCusWarehouseOrg_List = function(window, $) {
	var _init = function() {
		   myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/mfCusWarehouseOrg/findByPageAjax", //列表数据查询的url
				tableId : tableId, //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
		    });
	};
	//跳转至新增
	var _applyInsert = function() {
		top.openBigForm(webPath+"/mfCusWarehouseOrg/input","新增仓储机构", function(){
 			updateTableData();
 		});	
	};
	//跳转至详情
	var _getById = function(url) {
		window.location.href = url;
	};
	return {
		init : _init,
		getById:_getById,
		applyInsert:_applyInsert,
	};
}(window, jQuery);
