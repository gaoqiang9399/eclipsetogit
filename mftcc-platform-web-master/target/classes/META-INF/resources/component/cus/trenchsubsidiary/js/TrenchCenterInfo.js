;
var TrenchCenterInfo=function(window,$){
	var _init=function(){
		//_showTrenchBussingList();
		_showChildTrenchList();
	};
	
	var _showTrenchBussingList=function(){
		$.ajax({
			url : "MfBusApplyActionAjax_getTrenchBussHtmlAjax.action",
			type : 'post',
			dataType : 'json',
			data : {},
			success : function(data) {
				top.LoadingAnimate.stop();
				if(data.flag=="success"){
					$("#bussTable").html(data.tableHtml);
				}
			},
			error : function(data) {
				top.LoadingAnimate.stop();
				alert(data.msg, 0);
			}
		});	
	}
	//点击业务管理跳转业务申请列表
	var _bussBtn=function(){
		parent.$("#apply-li").click();
		/*parent.$("#apply-li").attr("class","menu-active")
		window.location.href = "MfBusApply_getTrenchApplyListPage.action?entranceNo=2";*/
	};
	//点击客户管理跳转
	var _cusBtn=function(){
		parent.$("#cus-li").click();
		/*parent.$("#cus-li").attr("class","menu-active");
		window.location.href = "MfCusCustomerAction_getCusListByTrenchPage.action?entranceNo=8";*/
	};
	
	var _getDetailPage = function (obj,url){		
		top.LoadingAnimate.start();		
		window.location.href=url;			
	};
	//记载子渠道列表数据
	var _showChildTrenchList=function(){
		$.ajax({
			url : "/mfBusTrench/getTrenchBusListHtmlStrAjax",
			type : 'post',
			dataType : 'json',
			data : {},
			success : function(data) {
				top.LoadingAnimate.stop();
				if(data.flag=="success"){
					$("#childTrenchTable").html(data.tableHtml);
				}
			},
			error : function(data) {
				top.LoadingAnimate.stop();
				alert(data.msg, 0);
			}
		});	
	};
	var _getViewCommon = function(url){
		window.location.href=url;
	};
	return{
		init:_init,
		cusBtn:_cusBtn,
		bussBtn:_bussBtn,
		getDetailPage:_getDetailPage,
		getViewCommon:_getViewCommon
	}
}(window,jQuery)