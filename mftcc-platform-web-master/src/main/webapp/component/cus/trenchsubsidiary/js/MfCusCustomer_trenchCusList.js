;
var MfCusCustomer_trenchCusList=function(window,$){
	var _init=function(){
		 myCustomScrollbar({
		    	obj:"#content",//页面内容绑定的id
		    	url:webPath+"/mfCusCustomer/findCusBytrenchPageAjax",//列表数据查询的url
		    	tableId:"tabletrenchCusInfoList",//列表数据查询的table编号
		    	tableType:"thirdTableTag",//table所需解析标签的种类
		    	pageSize:30,//加载默认行数(不填为系统默认行数)
		    	topHeight : 100//顶部区域的高度，用于计算列表区域高度。
		    });
	};
	var _getDetailPage = function (obj,url){		
		top.LoadingAnimate.start();		
		window.location.href=url;			
	};
	
	var _cusInput = function(){
		window.location.href="MfCusCustomerAction_inputTrenchCus.action";
	};
	return{
		init:_init,
		getDetailPage:_getDetailPage,
		cusInput:_cusInput
	};
}(window,jQuery);