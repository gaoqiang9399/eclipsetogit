;
var MfBusPostOver_List = function(window, $) {
	var _init = function() {
		   myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url:webPath+"/mfBusPact/findOverListByPageAjax",//列表数据查询的url
				tableId : "tablebusover0001",//列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
		    });
	};
	return {
		init : _init
	};
}(window, jQuery);