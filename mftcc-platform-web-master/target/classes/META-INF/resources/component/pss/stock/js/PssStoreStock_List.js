;
var pssStoreStockList = function(window, $){
	var _init = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssStoreStock/getListPageAjax",//列表数据查询的url
			tableId : "tablepssstorestock0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			data : {"goodsId":goodsId},//指定参数 (不会过滤掉已经封挡的数据)
			topHeight : 100//顶部区域的高度，用于计算列表区域高度。
		});
	};
	
	var _initForWarn = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssStoreStock/getListPageForWarnAjax",//列表数据查询的url
			tableId : "tablepssstorestock0011",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100//顶部区域的高度，用于计算列表区域高度。
		});
	};
	
	return {
		init : _init,
		initForWarn : _initForWarn
	};
}(window, jQuery);
