;
var MfFeePactQueryList=function(window,$){
	//初始化在履行借据贷后检查状态列表
	var _init=function(){
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/mfBusPact/findFeePactByPageAjax",//列表数据查询的url
	    	tableId:"tablefeePactQueryList",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	    	pageSize:30//加载默认行数(不填为系统默认行数)
	    });
	};

	var _getPreRepayDetialPage = function(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.flag=false;
		var resObj = StringUtil.urlParamsToObj(url);
		top.appId = resObj.appId;
		top.window.openBigForm(url,"费用关联合同号",function(){
			
		});
	};
	
	var _selectCredit = function(obj,url){
		var cusNo = url.indexOf("cusNo=");
		top.toCollateralDetail = false;
		top.creditAppId = "";
		top.appId = "";
		top.openBigForm(webPath+url, '授信申请信息', function() {
			if(top.toCollateralDetail){
				window.location.href=webPath+"/mfBusCollateralRel/getCollateralInfo?cusNo="+cusNo+"&relId="+top.creditAppId+"&appId="+top.appId+"&entrance=credit";
			}
		});
	};
	return{
		init:_init,
		selectCredit:_selectCredit,
		getPreRepayDetialPage:_getPreRepayDetialPage,
	};
}(window,jQuery)