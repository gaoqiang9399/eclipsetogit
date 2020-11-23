;
var MfCusGuaLoanOuterSum_List = function(window,$){
	//初始化
	var _init = function(){
		$(function(){
		    myCustomScrollbar({
			obj : "#content", //页面内容绑定的id
			url : webPath+"/mfCusGuaLoanOuterSum/findByPageAjax", //列表数据查询的url
			tableId : "tablemfgualoan0001", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:30 //加载默认行数(不填为系统默认行数)
			//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
		    });
		 });
	};
	
	 //新增
	 var _gualoanInsert = function(){
	 	top.window.openBigForm(webPath+'/mfCusGuaLoanOuterSum/input', '新增',function(){
	 		window.updateTableData();
	 	});
	 }
	return {
		init : _init,
		gualoanInsert : _gualoanInsert
	};
}(window,jQuery);