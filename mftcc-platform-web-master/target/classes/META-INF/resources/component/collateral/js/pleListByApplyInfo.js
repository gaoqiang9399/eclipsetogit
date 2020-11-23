;
var pleWarrantList=function(window,$){
	var _init=function(){
		LoadingAnimate.start();
	    myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/pledgeBaseInfo/getPledgeListByAppInfo",//列表数据查询的url
	    	tableId:"tableselectcollateral0001",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	    	pageSize:30,//加载默认行数(不填为系统默认行数)
	    	data:{pledgeMethod:pledgeMethod,cusNo:cusNo,appId:appId,tableName:tableName},//指定参数
	    	callback:function(){
	    		LoadingAnimate.stop();
	    	}//方法执行完回调函数（取完数据做处理的时候）
	    });
	};
	var _enterClick=function(parmArgs){
		var parm=parmArgs.split("?")[1];
		var parmArray=parm.split("&");
		var pledgeNo = parmArray[0].split("=")[1];
        var url;
		if("pledge_base_info" == tableName){
			url= webPath+"/mfBusCollateralRel/updateInput?pledgeNo="+pledgeNo+"&cusNo="+cusNo+"&appId="+appId;
			top.openBigForm(url,"押品信息",'');
		}
		if("mf_pledge_status" == tableName){
			url= webPath+"/mfPledgeStatus/input?pledgeNo="+pledgeNo+"&cusNo="+cusNo;
			top.openBigForm(url,"抵押信息",'');
		}
		
	
	};
	return{
		init:_init,
		enterClick:_enterClick,
	};
}(window,jQuery);