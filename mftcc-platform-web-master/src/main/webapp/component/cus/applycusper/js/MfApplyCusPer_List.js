;
var MfApplyCusPer_List = function(window, $) {
	var _init = function() {
		 	myCustomScrollbar({
				obj : "#content", 
				url : webPath+"/mfApplyCusPer/findByPageAjax", //列表数据查询的url
				tableId : "tableapplycusper", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30, //加载默认行数(不填为系统默认行数)
				topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
		    });
	};
    /**
	 * 客户权限新增申请页面请求
     * @private
     */
    var _toApplyInsert = function(){
        window.location.href=webPath+"/mfApplyCusPer/input";
    };
	return {
		init : _init,
        toApplyInsert: _toApplyInsert
	};
}(window, jQuery);
