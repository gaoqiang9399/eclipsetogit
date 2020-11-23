;
var mfCollateralClassSelect=function(window,$){
	var _init=function(){
		LoadingAnimate.start();
	    myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/mfCollateralClass/getListPageByVoutypeAjax",//列表数据查询的url
	    	tableId:"tablecollateralclass0003",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	    	pageSize:30,//加载默认行数(不填为系统默认行数)
	    	data:{vouType:vouType,appId:appId},//指定参数
	    	callback:function(){
	    		LoadingAnimate.stop();
	    	}//方法执行完回调函数（取完数据做处理的时候）
	    });
	};
	var _enterClick=function(parmArgs){
		var parm=parmArgs.split("?")[1];
		var parmArray=parm.split("&");
		var classNo = parmArray[0].split("=")[1];
		var vouType = parmArray[1].split("=")[1];
		var action = "PledgeBaseInfoAction";
		var className = parmArray[2].split("=")[1];
		var url = webPath+"/mfBusCollateralRel/insertInput?cusNo=";
		if(entrFlag=="credit"){
			url = webPath+"/mfBusCollateralRel/collateralBaseInputCredit?cusNo=";
		}
		url = url + cusNo
		+ "&appId=" + appId
		+ "&addWkfFlag=0"
		+ "&classNo=" + classNo
		+ "&vouType=" + vouType
		+ "&className=" + className
		+ "&action=" + action
		+"&entrFlag="+entrFlag;
		window.location.href=url; 
	};
	return{
		init:_init,
		enterClick:_enterClick,
	};
}(window,jQuery);