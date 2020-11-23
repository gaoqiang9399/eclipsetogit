
var TrenchBussQueryList = function(window,$){
	var _init = function(){
		_allBuss();
		//查询选项卡绑定点击事件
		$(".search-title li").click(function(){
	 		$(".search-title li").find("a").removeClass("active");
	 		$(this).find("a").addClass("active");
	 		var thisId = $(this).attr("id");
	 		$(".mysearch .mysearch-content").css("display","none");
	 		if(thisId=="menu-all"){//全部
	 			_allBuss();
	 		}else if(thisId=="menu-repay"){//还款中
	 			_repayBuss();
	 		}else if(thisId=="menu-finish"){//已完结
	 			_finishBuss();
	 		}
	 	});
	};
	//全部业务
	var _allBuss = function(){
		$("#content").html("");
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : "/mfBusApply/findTrenchBussByPageAjax",//列表数据查询的url
			tableId : "tabletrenchApplyList",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
		});
	};
	//还款中业务列表
	var _repayBuss = function(){
		$("#content").html("");
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : "/mfBusFincApp/findTrenchBussByPageAjax",//列表数据查询的url
			tableId : "tabletrenchFincList",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
			data:{fincSts:"('5','6')"}//借据状态为放款已复核、还款复核中
		});
	};
	//已完结业务列表
	var _finishBuss = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : "/mfBusFincApp/findTrenchBussByPageAjax",//列表数据查询的url
			tableId : "tabletrenchFincList",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
			data:{fincSts:"('7')"}//借据状态为已完结
		});
	};
	//跳转详情
	var _getDetail=function(obj,url){
		top.LoadingAnimate.start();		
		window.location.href=url;
	};
	return{ 
		init:_init,
		getDetail:_getDetail
	};
	 
}(window,jQuery);