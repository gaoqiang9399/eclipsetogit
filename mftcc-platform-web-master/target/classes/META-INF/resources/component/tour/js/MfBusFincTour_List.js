;
var MfBusFincTour_List = function(window, $) {
	var _init = function() {
		   myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/mfbusFincTour/findByPageAjax", //列表数据查询的url
				tableId : "tablefincmfbusbase", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
		    });
	};
	
	var _getDetail = function(obj,url){
		top.openBigForm(webPath+url,"巡视跟进登记", function(){
		updateTableData();
		}); 
	 }
	//跳转至新增
	
	var _applyInsert = function(obj,url) {
		top.openBigForm(webPath+url,"新增巡视记录", function(){
			updateTableData();
 		});	
	};
	return {
		init : _init,
		applyInsert:_applyInsert,
		getDetail:_getDetail
	};
}(window, jQuery);
