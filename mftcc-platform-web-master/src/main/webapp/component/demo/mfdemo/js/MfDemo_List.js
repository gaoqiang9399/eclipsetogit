;
var MfDemoList = function(window, $) {
	//初始化
	var _init = function(){
		$(function(){
		    myCustomScrollbar({
		    	obj : "#content", //页面内容绑定的id
		    	url : webPath+"/mfDemo/findByPageAjax", //列表数据查询的url
		    	tableId : "tablemfdemo0001", //列表数据查询的table编号
		    	tableType : "thirdTableTag", //table所需解析标签的种类
		    	pageSize:30 //加载默认行数(不填为系统默认行数)
		    });
		 });
		
	};
	 //演示新增
	 var _demoInsert = function(){
	 	top.window.openBigForm(webPath+'/mfDemo/input', '演示新增',function(){
	 		window.updateTableData();
	 	});
	 }
	 //查看详情
	 var _getDetailPage =function(obj,url){
	 	//跳页面
//	 	window.location.href=url;
	 	//弹层显示：openBigForm(url,title,ctrlFlag,w,h,minW,minH);
	 	top.openBigForm(url,"查看详情",function(){
	 		//回调处理
	 		window.updateTableData();
		});
	 }
	return {
		init : _init,
		getDetailPage:_getDetailPage,
		demoInsert:_demoInsert
	};
}(window, jQuery);