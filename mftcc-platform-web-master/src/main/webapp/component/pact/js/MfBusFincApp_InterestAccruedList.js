;
var MfBusFincApp_InterestAccruedList=function(window,$){
	//初始化在履行借据贷后检查状态列表
	var _init=function(){
		_showInfoPage(queryMonth);
		$("#queryMonth").val(queryMonth);
	};
	var _showInfoPage=function(queryMonth){
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/mfBusFincApp/findInterestAccruedByPageAjax",//列表数据查询的url
	    	tableId:"tableinterestAccruedList",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	    	pageSize:30,//加载默认行数(不填为系统默认行数)
	    	data:{
	    		queryMonth:queryMonth
	    	}
	    });
	};
	var _queryByMonth=function(){
		queryMonth=$("#queryMonth").val();
		_showInfoPage(queryMonth);
	};
	var _getDetailPage = function (obj,url){	
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.LoadingAnimate.start();		
		window.location.href=url;			
	};
	return{
		init:_init,
		getDetailPage:_getDetailPage,
		queryByMonth:_queryByMonth
	};
}(window,jQuery)